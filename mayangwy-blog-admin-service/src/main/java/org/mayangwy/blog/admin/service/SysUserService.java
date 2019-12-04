package org.mayangwy.blog.admin.service;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ObjectUtil;
import org.mayangwy.blog.admin.common.enums.IsDelEnum;
import org.mayangwy.blog.admin.common.jdbc.DruidDataSourceBuilder;
import org.mayangwy.blog.admin.dao.SysUserDao;
import org.mayangwy.blog.admin.dao.po.SysUser;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class SysUserService {

    private SysUserDao sysUserDao = Singleton.get(SysUserDao.class);

    private DataSource dataSource = DruidDataSourceBuilder.getDataSource();

    public void save(SysUser sysUser){
        save(null, sysUser);
    }

    private void save(Connection connection, SysUser sysUser){
        if(ObjectUtil.isNotNull(sysUser.getId())){
            sysUserDao.update(connection, sysUser);
        } else {
            Date now = new Date();
            sysUser.setCreateUserId(1L);
            sysUser.setCreateTime(now);
            sysUser.setUpdateUserId(1L);
            sysUser.setUpdateTime(now);
            sysUser.setIsDel(IsDelEnum.NO.getCode());
            sysUserDao.insert(connection, sysUser);
        }
    }

    public void delete(Long id){
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setIsDel(IsDelEnum.YES.getCode());
        sysUserDao.deleteById(null, sysUser);
    }

    public List<SysUser> find(SysUser sysUser){
        return sysUserDao.find(null, sysUser);
    }

}