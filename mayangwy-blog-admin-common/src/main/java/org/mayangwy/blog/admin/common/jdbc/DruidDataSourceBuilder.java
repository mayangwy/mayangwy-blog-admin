package org.mayangwy.blog.admin.common.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 创建druid数据源类
 */
public class DruidDataSourceBuilder {

    private DruidDataSourceBuilder() throws Exception {
        throw new Exception("this class[DruidDataSourceBuilder] is not create instance !!!");
    }

    private static DataSource dataSource = null;

    private static boolean isCreate = false;

    public static synchronized void createDataSource(Map<String, String> dataSourceMap) throws Exception {
        if(!isCreate){
            dataSource = DruidDataSourceFactory.createDataSource(dataSourceMap);
            isCreate = true;
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }

}