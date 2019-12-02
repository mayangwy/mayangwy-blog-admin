package org.mayangwy.blog.admin.dao;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ReflectUtil;
import com.google.common.base.CaseFormat;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.ClassUtils;
import org.mayangwy.blog.admin.common.jdbc.DruidDataSourceBuilder;
import org.mayangwy.blog.admin.common.util.PoParser;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

public class DaoTemplate {

    private QueryRunner queryRunner = Singleton.get(QueryRunner.class, DruidDataSourceBuilder.getDataSource());

    {
        try {
            Class.forName("org.mayangwy.blog.admin.common.util.PoParser");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Serializable insert(Object obj){
        String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, ClassUtils.getSimpleName(obj.getClass()));
        String insertSqlKey = "INSERT.SQL." + tableName;
        String insertSqlValue = PoParser.sqlCache.get(insertSqlKey);

        Object[] fieldsValues = ReflectUtil.getFieldsValue(obj);
        Object[] objectParams = Arrays.stream(fieldsValues).map(item -> {
            if (item instanceof Date) {
                return DateUtil.formatDateTime((Date) item);
            }
            return item;
        }).toArray();

        try {
            long start = System.currentTimeMillis();
            Serializable insertResult = queryRunner.insert(insertSqlValue, new ScalarHandler<>(), objectParams);
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