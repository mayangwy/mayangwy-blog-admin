package org.mayangwy.blog.admin.common.util;

import cn.hutool.core.date.DateUtil;

import java.math.BigInteger;
import java.util.Date;

/**
 *
 * 项目通用工具类
 *
 */
public class Utils {

    public static void convertSqlInputValue(Object[] objects){
        if(objects == null){
            return;
        }

        for(int i = 0; i < objects.length; i++){
            if(objects[i] instanceof Date){
                objects[i] = DateUtil.formatDateTime((Date) objects[i]);
                continue;
            }
        }
    }

    public static void convertSqlOutValue(Object[] objects){
        if(objects == null){
            return;
        }

        for(int i = 0; i < objects.length; i++){
            if(objects[i] instanceof BigInteger){
                objects[i] = ((BigInteger) objects[i]).longValue();
                continue;
            }
        }
    }

    public static <T> T convertSqlOutValue(Object object){
        Object[] objects = new Object[]{object};
        convertSqlOutValue(objects);
        return (T)objects[0];
    }

}