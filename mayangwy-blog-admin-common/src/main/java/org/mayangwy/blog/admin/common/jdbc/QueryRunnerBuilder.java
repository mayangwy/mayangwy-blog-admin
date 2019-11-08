package org.mayangwy.blog.admin.common.jdbc;

import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;

public class QueryRunnerBuilder {

    private static QueryRunner queryRunner = new QueryRunner(DruidDataSourceBuilder.getDataSource());

    public static QueryRunner getQueryRunner(){
        return queryRunner;
    }

}