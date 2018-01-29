package com.ng.member.entity;

import java.util.Date;
import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author mybatis generator
 * @date 2018/01/17
 */
@Data
public class Role {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名
     */
    @Column(name = "role_name")
    private String roleName;

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
}