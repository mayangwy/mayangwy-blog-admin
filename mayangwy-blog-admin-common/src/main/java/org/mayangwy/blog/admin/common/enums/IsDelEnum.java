package org.mayangwy.blog.admin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsDelEnum {

    YES(1, "已删除"), NO(0, "正常");

    private Integer code;
    private String name;

}