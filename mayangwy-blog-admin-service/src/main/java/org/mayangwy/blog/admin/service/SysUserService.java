package org.mayangwy.blog.admin.service;

import org.mayangwy.blog.admin.dao.SysUserDao;

public class SysUserService {

    private SysUserDao sysUserDao;

    public SysUserService() {
        sysUserDao = new SysUserDao();
    }

}