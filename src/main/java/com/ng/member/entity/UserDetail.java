package com.ng.member.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 用户详情
 * @author niuguang
 * @date 18-1-17
 */
@Data
public class UserDetail {

    private Long id;

    private String userName;

    @JSONField(serialize = false)
    private String password;

    private Long roleId;

    private String roleName;

    private List<Auth> authList;
}
