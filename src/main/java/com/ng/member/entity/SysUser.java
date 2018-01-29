package com.ng.member.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * @author mybatis generator
 * @date 2018/01/17
 */
@Table(name = "sys_user")
@Data
public class SysUser {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 删除标记 N/Y
     */
    @Column(name = "delete_flag")
    private String deleteFlag;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}