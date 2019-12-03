package org.mayangwy.blog.admin.common.util;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PoParser {

    public static final Map<String, String> sqlCache = new ConcurrentHashMap<>();

    private static final String insertSqlTemplate = "INSERT INTO {} ({}) VALUES ({})";

    private static final String entityPackagePre = "entity.package";

    static {
        Optional<Object> first = Props.getProp("application.properties").keySet().stream().filter(entityPackagePre::equals).findFirst();
        first.ifPresent(item -> {
            Set<Class<?>> resources = ClassUtil.scanPackage(item.toString());
            resources.forEach(element -> {
                //获取表名
                String tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, ClassUtils.getSimpleName(element.getClass()));
                //获取数据库字段名
                Field[] fields = ReflectUtil.getFields(element.getClass());
                String[] colnums = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    colnums[i] = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, fields[i].getName());
                }
                String fieldNameJoinStr = Joiner.on(", ").join(colnums);

                String placeholderStr = StrUtil.repeat("?, ", colnums.length - 1) + "?";

                String insertSql = StrUtil.format(insertSqlTemplate, tableName, fieldNameJoinStr, placeholderStr);

                sqlCache.put("INSERT.SQL." + tableName, insertSql);
            });
        });
    }

}