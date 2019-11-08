package org.mayangwy.blog.admin.common.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * 创建druid数据源类
 */
@Slf4j
public class DruidDataSourceBuilder {

    private static String key_prefix = "jdbc.config.url.";
    private static DataSource dataSource = null;

    private static final Map<String, String> dataSourceMap = Maps.newHashMap();

    static {
        dataSourceMap.put("driverClassName", "com.mysql.jdbc.Driver");
        dataSourceMap.put("url", "jdbc:mysql://localhost:3306/blog-admin?useUnicode=true&characterEncoding=utf-8");
        dataSourceMap.put("username", "root");
        dataSourceMap.put("password", "112233");

        dataSourceMap.put("initialSize", "0");
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

    private static void createDataSource() {
        if(dataSource == null){
            synchronized(DruidDataSourceBuilder.class){
                if(dataSource == null){
                    try {
                        Properties properties = new Properties();
                        InputStream inputStream = DruidDataSourceBuilder.class.getClassLoader().getResourceAsStream("application.properties");
                        properties.load(inputStream);
                        inputStream.close();

                        properties.forEach((k,v) -> {
                            String keyStr = (String) k;
                            if(k.toString().startsWith(key_prefix)){
                                dataSourceMap.put(StringUtils.removeStart(keyStr, key_prefix), (String) v);
                            }
                        });

                        dataSource = DruidDataSourceFactory.createDataSource(dataSourceMap);//这一步是否可以导致不用加volatile
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static DataSource getDataSource(){
        if(dataSource == null){
            createDataSource();
        }
        return dataSource;
    }

}