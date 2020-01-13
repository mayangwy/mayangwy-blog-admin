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
    private static DruidDataSource druidDataSource = null;//项目启动前完成初始化，不用加volatile

    private static final Map<String, String> dataSourceMap = Maps.newHashMap();

    static {
        dataSourceMap.put("driverClassName", "com.mysql.jdbc.Driver");
        dataSourceMap.put("url", "jdbc:mysql://localhost:3306/blog-admin?useUnicode=true&characterEncoding=utf-8");
        dataSourceMap.put("username", "root");
        dataSourceMap.put("password", "112233");

        dataSourceMap.put("initialSize", "5");
        dataSourceMap.put("minIdle", "5");
        dataSourceMap.put("maxActive", "20");

        dataSourceMap.put("maxWait", "60000");

        dataSourceMap.put("timeBetweenEvictionRunsMillis", "60000");

        dataSourceMap.put("minEvictableIdleTimeMillis", "300000");

        dataSourceMap.put("validationQuery", "SELECT 1");
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
                        Props.getProp("application.properties").forEach((k, v) -> {
                            String keyStr = (String) k;
                            String valueStr = (String) v;
                            if (StringUtils.startsWith(keyStr, key_prefix)) {
                                dataSourceMap.put(StringUtils.removeStart(keyStr, key_prefix), valueStr);
                            }
                        });

                        druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(dataSourceMap);
                        druidDataSource.setFilters("stat");
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