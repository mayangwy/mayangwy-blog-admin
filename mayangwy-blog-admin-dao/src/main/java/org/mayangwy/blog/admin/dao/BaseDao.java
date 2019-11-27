package org.mayangwy.blog.admin.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.mayangwy.blog.admin.common.jdbc.QueryRunnerBuilder;

import java.util.List;

public class BaseDao<T> implements IBaseDao<T> {

    protected QueryRunner queryRunner = QueryRunnerBuilder.getQueryRunner();


    @Override
    public int insert(T o) {
        return 0;
    }

    @Override
    public int insert(List<T> list) {
        return 0;
    }
}