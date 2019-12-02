package org.mayangwy.blog.admin.dao;

import cn.hutool.core.lang.Singleton;

public class BaseDao<T, ID> implements IBaseDao<T, ID> {

    private DaoTemplate daoTemplate = Singleton.get(DaoTemplate.class);

    @Override
    public ID insert(T t) {
       return (ID) daoTemplate.insert(t);
    }

}