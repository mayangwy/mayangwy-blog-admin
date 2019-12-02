package org.mayangwy.blog.admin.dao;

import java.util.List;

public interface IBaseDao<T, ID> {

    ID insert(T t);

}