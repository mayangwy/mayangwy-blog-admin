package org.mayangwy.blog.admin.dao.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUser implements Serializable {

    private Long id;

    private String userName;

    private String password;

    private String nickName;

    private String email;

    private String mobile;

    private String idCardNo;

    private Long departmentId;

    private Long createUserId;

    private Date createTime;

    private Long updateUserId;

    private Date updateTime;

    private Integer isDel;

}