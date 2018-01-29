package com.ng.member.entity;

import java.util.Date;
import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author mybatis generator
 * @date 2018/01/18
 */
@Table(name = "user_group")
@Data
public class UserGroup {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 组名称
     */
    @Column(name = "group_name")
    private String groupName;

    /**
     * 组管理员ID
     */
    @Column(name = "admin_user_id")
    private Long adminUserId;

    /**
     * 组介绍
     */
    @Column(name = "group_desc")
    private String groupDesc;

    /**
     * 组标识
     */
    @Column(name = "group_code")
    private String groupCode;

    /**
     * 删除标记 N/Y
     */
    @JSONField(serialize = false)
    @Column(name = "delete_flag")
    private String deleteFlag;

    /**
     * 创建时间
     */
    @JSONField(serialize = false)
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @JSONField(serialize = false)
    @Column(name = "update_time")
    private Date updateTime;
}