package com.ng.member.entity;

import java.util.Date;
import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author mybatis generator
 * @date 2018/01/17
 */
@Table(name = "user_to_group")
@Data
public class UserToGroup {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 组ID
     */
    @Column(name = "group_id")
    private Long groupId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 组角色 0:管理员 1:成员
     */
    @Column(name = "group_role")
    private String groupRole;

    /**
     * 删除标记 N/Y
     */
    @JSONField(serialize = false)
    @Column(name = "delete_flag")
    private String deleteFlag;

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


    @Transient
    private String userName;
}