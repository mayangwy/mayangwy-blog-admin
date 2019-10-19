package org.mayangwy.blog.admin.common.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建druid数据源类
 */
public class DruidDataSourceBuilder {

    private static volatile DataSource dataSource = null;

    private static final Map<String, String> dataSourceMap = new HashMap<>();

    static {
        dataSourceMap.put("driverClassName", "com.mysql.jdbc.Driver");
        dataSourceMap.put("url", "jdbc:mysql://localhost:3306/blog-admin?useUnicode=true&characterEncoding=utf-8");
        dataSourceMap.put("username", "root");
        dataSourceMap.put("password", "112233");

        dataSourceMap.put("initialSize", "1");
        dataSourceMap.put("minIdle", "5");
        dataSourceMap.put("maxActive", "20");

        dataSourceMap.put("maxWait", "60000");

        dataSourceMap.put("timeBetweenEvictionRunsMillis", "60000");

        dataSourceMap.put("minEvictableIdleTimeMillis", "300000");

        dataSourceMap.put("validationQuery", "SELECT 'x' FROM DUAL");
        dataSourceMap.put("testWhileIdle", "true");
        dataSourceMap.put("testOnBorrow", "false");
        dataSourceMap.put("testOnReturn", "false");
    }

    private DruidDataSourceBuilder() throws Exception {
        throw new Exception("this class[DruidDataSourceBuilder] is not create instance !!!");
    }

    public static void createDataSource(Map<String, String> dataSourceMap) {
        try {
            dataSource = DruidDataSourceFactory.createDataSource(dataSourceMap);
        } catch (Exception e) {
            System.exit(1);
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }

}