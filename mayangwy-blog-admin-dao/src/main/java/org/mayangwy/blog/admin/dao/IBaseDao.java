package org.mayangwy.blog.admin.dao;

import java.util.List;

public interface IBaseDao<T> {

    int insert(T t);

    int insert(List<T> list);

}