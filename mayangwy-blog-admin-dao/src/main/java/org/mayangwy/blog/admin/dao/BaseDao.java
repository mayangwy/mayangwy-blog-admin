package org.mayangwy.blog.admin.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.mayangwy.blog.admin.common.jdbc.QueryRunnerBuilder;

public class BaseDao {

    protected QueryRunner queryRunner;

    {
        queryRunner = QueryRunnerBuilder.getQueryRunner();
    }

}