package org.mayangwy.blog.admin.dao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.ClassUtils;
import org.mayangwy.blog.admin.common.jdbc.DruidDataSourceBuilder;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DaoTemplate {

    private static final Map<String, String> sqlCache = new ConcurrentHashMap<>();

    private static final String insertSqlTemplate = "INSERT INTO {} ({}) VALUES ({})";

    private QueryRunner queryRunner = Singleton.get(QueryRunner.class, DruidDataSourceBuilder.getDataSource());

    private static final ThreadLocal<Connection> connections = new ThreadLocal<>();

    public Serializable insert(Object obj){
        String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, ClassUtils.getSimpleName(obj.getClass()));
        String insertSqlKey = "INSERT.SQL." + tableName;
        String insertSqlValue = sqlCache.get(insertSqlKey);
        if(insertSqlValue != null){
            Object[] fieldsValues = ReflectUtil.getFieldsValue(obj);
            Object[] objectParams = Arrays.stream(fieldsValues).map(item -> {
                if (item instanceof Date) {
                    return DateUtil.formatDateTime((Date) item);
                }
                return item;
            }).toArray();
            try {
                long start = System.currentTimeMillis();
                Serializable insertResult = queryRunner.insert(insertSqlValue, new ScalarHandler<Serializable>(), objectParams);
                System.out.println(System.currentTimeMillis() - start);
                if(insertResult instanceof BigInteger){
                    return ((BigInteger) insertResult).longValue();
                } else {
                    return insertResult;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            Map<String, Object> beanMap = BeanUtil.beanToMap(obj);
            //获取数据库字段名
            List<String> fieldNameList = beanMap.keySet().stream().map(i -> CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, i.toString())).collect(Collectors.toList());
            String fieldNameJoinStr = Joiner.on(", ").join(fieldNameList);

            String placeholderStr = StrUtil.repeat("?, ", fieldNameList.size() - 1) + "?";

            String insertSql = StrUtil.format(insertSqlTemplate, tableName, fieldNameJoinStr, placeholderStr);

            sqlCache.put("INSERT.SQL." + tableName, insertSql);

            try {
                long start = System.currentTimeMillis();
                Serializable insertResult = queryRunner.insert(insertSql, new ScalarHandler<Serializable>(), beanMap.values().toArray());
                System.out.println(System.currentTimeMillis() - start);
                if(insertResult instanceof BigInteger){
                    return ((BigInteger) insertResult).longValue();
                } else {
                    return insertResult;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}