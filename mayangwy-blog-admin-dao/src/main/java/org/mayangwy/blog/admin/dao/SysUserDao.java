package org.mayangwy.blog.admin.dao;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.mayangwy.blog.admin.common.jdbc.DruidDataSourceBuilder;
import org.mayangwy.blog.admin.common.util.Utils;
import org.mayangwy.blog.admin.dao.po.SysUser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SysUserDao {

    private QueryRunner queryRunner = Singleton.get(QueryRunner.class, DruidDataSourceBuilder.getDataSource());

    public Long insert(Connection connection, SysUser sysUser){
        String sql = "INSERT INTO SYS_USER (USER_NAME, PASSWORD, NICK_NAME, EMAIL, MOBILE, ID_CARD_NO, DEPARTMENT_ID, CREATE_USER_ID, CREATE_TIME, UPDATE_USER_ID, UPDATE_TIME, IS_DEL) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] params = ReflectUtil.getFieldsValue(sysUser);
        Utils.convertSqlInputValue(params);

        try {
            Object insertResult;
            if(connection == null){
                insertResult = queryRunner.insert(sql, new ScalarHandler<>(), params);
            } else {
                insertResult = queryRunner.insert(connection, sql, new ScalarHandler<>(), params);
            }

            Long finalResult = Utils.convertSqlOutValue(insertResult);
            return finalResult;
        } catch (SQLException e) {
            log.error("系统用户新增失败", e);
            throw new RuntimeException(e);
        }
    }

    public Integer deleteById(Connection connection, SysUser sysUser){
        String sql = "UPDATE SYS_USER SET IS_DEL = ? WHERE ID = ?";
        Object[] params = new Object[]{sysUser.getIsDel(), sysUser.getId()};

        try {
            Integer result;
            if(connection == null){
                result = queryRunner.update(sql, params);
            } else {
                result = queryRunner.update(connection, sql, params);
            }

            return result;
        } catch (SQLException e) {
            log.error("系统用户删除失败", e);
            throw new RuntimeException(e);
        }
    }

    public Integer update(Connection connection, SysUser sysUser){
        String sqlTemplate = "UPDATE SYS_USER SET {} WHERE ID = ?";

        List<String> updateList = new ArrayList<>();
        List<Object> paramList = new ArrayList<>();
        if(StrUtil.isNotBlank(sysUser.getNickName())){
            updateList.add("NICK_NAME = ?");
            paramList.add(sysUser.getNickName());
        }

        if(StrUtil.isNotBlank(sysUser.getEmail())){
            updateList.add("EMAIL = ?");
            paramList.add(sysUser.getEmail());
        }

        if(StrUtil.isNotBlank(sysUser.getMobile())){
            updateList.add("MOBILE = ?");
            paramList.add(sysUser.getMobile());
        }

        if(updateList.isEmpty()){
            return 0;
        }

        String updateStr = CollUtil.join(updateList, ", ");
        String sql = StrUtil.format(sqlTemplate, updateStr);
        paramList.add(sysUser.getId());
        Object[] params = paramList.toArray();

        try {
            Integer result;
            if(connection == null){
                result = queryRunner.update(sql, params);
            } else {
                result = queryRunner.update(connection, sql, params);
            }

            return result;
        } catch (SQLException e) {
            log.error("系统用户更新失败", e);
            throw new RuntimeException(e);
        }
    }

    public List<SysUser> find(Connection connection, SysUser sysUser){
        String sqlTemplate = "SELECT ID, USER_NAME AS userName, PASSWORD, NICK_NAME AS nickName, EMAIL, MOBILE, " +
                "ID_CARD_NO AS idCardNo, DEPARTMENT_ID AS departmentId, CREATE_USER_ID AS createUserId, " +
                "CREATE_TIME AS createTime, UPDATE_USER_ID AS updateUserId, UPDATE_TIME AS updateTime, IS_DEL AS isDel FROM SYS_USER WHERE {}";

        List<String> whereList = new ArrayList<>();
        List<Object> paramList = new ArrayList<>();
        if(ObjectUtil.isNotNull(sysUser.getId())){
            whereList.add("ID = ?");
            paramList.add(sysUser.getId());
        }

        if(StrUtil.isNotBlank(sysUser.getUserName())){
            whereList.add("USER_NAME LIKE ?");
            paramList.add("%" + sysUser.getUserName() + "%");
        }

        String whereStr = CollUtil.join(whereList, " AND ");
        String sql = StrUtil.format(sqlTemplate, whereStr);
        Object[] params = paramList.toArray();

        try {
            List<SysUser> resultList;
            if(connection == null){
                resultList = queryRunner.query(sql, new BeanListHandler<>(SysUser.class), params);
            } else {
                resultList = queryRunner.query(connection, sql, new BeanListHandler<>(SysUser.class), params);
            }

            return resultList;
        } catch (SQLException e) {
            log.error("系统用户查询失败", e);
            throw new RuntimeException(e);
        }
    }

}