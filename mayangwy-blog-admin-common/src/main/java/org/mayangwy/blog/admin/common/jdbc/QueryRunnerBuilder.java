package org.mayangwy.blog.admin.common.jdbc;

import org.apache.commons.dbutils.QueryRunner;

public class QueryRunnerBuilder {

    private static QueryRunner queryRunner = new QueryRunner(DruidDataSourceBuilder.getDataSource());

    public static QueryRunner getQueryRunner(){
        return queryRunner;
    }

}