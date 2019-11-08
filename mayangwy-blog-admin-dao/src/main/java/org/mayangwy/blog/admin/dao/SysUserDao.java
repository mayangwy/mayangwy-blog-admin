package org.mayangwy.blog.admin.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.mayangwy.blog.admin.common.jdbc.QueryRunnerBuilder;

public class SysUserDao {

    private QueryRunner queryRunner;

    public SysUserDao() {
        this.queryRunner = QueryRunnerBuilder.getQueryRunner();
    }

}