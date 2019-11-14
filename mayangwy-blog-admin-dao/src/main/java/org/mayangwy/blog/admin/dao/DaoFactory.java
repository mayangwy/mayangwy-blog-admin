package org.mayangwy.blog.admin.dao;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class DaoFactory {

    private static final ListMultimap<Class, BaseDao> classesDaos = ArrayListMultimap.create();

    private static final ListMultimap<String, BaseDao> namesDaos = ArrayListMultimap.create();

}