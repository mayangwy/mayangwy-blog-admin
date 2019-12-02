package org.mayangwy.blog.admin.common.jdbc;

import cn.hutool.setting.dialect.Props;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 创建druid数据源类
 */
@Slf4j
public class DruidDataSourceBuilder {

    private static String key_prefix = "jdbc.config.";
    private static volatile DruidDataSource druidDataSource = null;

    private static final Map<String, String> dataSourceMap = Maps.newHashMap();

    static {
        dataSourceMap.put("driverClassName", "com.mysql.jdbc.Driver");
        dataSourceMap.put("url", "jdbc:mysql://localhost:3306/blog-admin?useUnicode=true&characterEncoding=utf-8");
        dataSourceMap.put("username", "root");
        dataSourceMap.put("password", "112233");

        dataSourceMap.put("initialSize", "5");
        dataSourceMap.put("minIdle", "5");
        dataSourceMap.put("maxActive", "20");
        dataSourceMap.put("inited", "true");

        dataSourceMap.put("maxWait", "60000");

        dataSourceMap.put("timeBetweenEvictionRunsMillis", "60000");

        dataSourceMap.put("minEvictableIdleTimeMillis", "300000");

        dataSourceMap.put("validationQuery", "SELECT 'x' FROM DUAL");
        dataSourceMap.put("testWhileIdle", "true");
        dataSourceMap.put("testOnBorrow", "false");
        dataSourceMap.put("testOnReturn", "false");
    }

    private DruidDataSourceBuilder() throws Exception {
        throw new Exception("this class[org.mayangwy.blog.admin.common.jdbc.DruidDataSourceBuilder] is not create instance !!!");
    }

    private static void createDataSource() {
        if (druidDataSource == null) {
            synchronized (DruidDataSourceBuilder.class) {
                if (druidDataSource == null) {
                    try {
                        /*Properties properties = new Properties();
                        InputStream inputStream = DruidDataSourceBuilder.class.getClassLoader().getResourceAsStream("application.properties");
                        properties.load(inputStream);
                        inputStream.close();*/

                        Props.getProp("application.properties").forEach((k, v) -> {
                            String keyStr = (String) k;
                            String valueStr = (String) v;
                            if (StringUtils.startsWith(keyStr, key_prefix)) {
                                dataSourceMap.put(StringUtils.removeStart(keyStr, key_prefix), valueStr);
                            }
                        });

                        druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(dataSourceMap);//这一步是否可以导致不用加volatile
                        druidDataSource.init();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static DataSource getDataSource() {
        if (druidDataSource == null) {
            createDataSource();
        }
        return druidDataSource;
    }

}