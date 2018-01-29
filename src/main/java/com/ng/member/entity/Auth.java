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
public class Auth {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限名称
     */
    @Column(name = "auth_name")
    private String authName;

    /**
     * 权限编码
     */
    @Column(name = "auth_code")
    private String authCode;

    /**
     * 删除标记 N/Y
     */
    @JSONField(serialize = false)
    @Column(name = "delete_flag")
    private String deleteFlag;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}