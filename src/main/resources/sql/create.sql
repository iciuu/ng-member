CREATE DATABASE ngmember;


DROP TABLE IF EXISTS user;
CREATE TABLE user
(
  id                        BIGINT UNSIGNED AUTO_INCREMENT
  COMMENT '主键ID'
    PRIMARY KEY,
  user_name                 VARCHAR(50)  NOT NULL
  COMMENT '用户名',
  password                  VARCHAR(200) NOT NULL
  COMMENT '密码',
  delete_flag               CHAR(2)      DEFAULT 'N' NULL
  COMMENT '删除标记 N/Y',
  last_login_time           DATETIME     NULL
  COMMENT '最后登录时间',
  create_time               DATETIME     NULL
  COMMENT '创建时间',
  update_time               DATETIME     NULL
  COMMENT '更新时间'
)
  COMMENT '用户表'
  ENGINE = InnoDB;

DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user
(
  id                        BIGINT UNSIGNED AUTO_INCREMENT
  COMMENT '主键ID'
    PRIMARY KEY,
  user_name                 VARCHAR(50)  NOT NULL
  COMMENT '用户名',
  password                  VARCHAR(200) NOT NULL
  COMMENT '密码',
  delete_flag               CHAR(2)      DEFAULT 'N' NULL
  COMMENT '删除标记 N/Y',
  last_login_time           DATETIME     NULL
  COMMENT '最后登录时间',
  create_time               DATETIME     NULL
  COMMENT '创建时间',
  update_time               DATETIME     NULL
  COMMENT '更新时间'
)
  COMMENT '管理员表'
  ENGINE = InnoDB;


DROP TABLE IF EXISTS role;
CREATE TABLE role
(
  id                        BIGINT UNSIGNED AUTO_INCREMENT
  COMMENT '主键ID'
    PRIMARY KEY,
  role_name                 VARCHAR(50)  NOT NULL
  COMMENT '角色名',
  delete_flag               CHAR(2)      DEFAULT 'N' NULL
  COMMENT '删除标记 N/Y',
  create_time               DATETIME     NULL
  COMMENT '创建时间',
  update_time               DATETIME     NULL
  COMMENT '更新时间'
)
  COMMENT '角色表'
  ENGINE = InnoDB;

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role
(
  id                        BIGINT UNSIGNED AUTO_INCREMENT
  COMMENT '主键ID'
    PRIMARY KEY,
  user_id                   BIGINT UNSIGNED NOT NULL
  COMMENT '用户ID',
  role_id                   BIGINT UNSIGNED NOT NULL
  COMMENT '角色ID',
  delete_flag               CHAR(2)      DEFAULT 'N' NULL
  COMMENT '删除标记 N/Y',
  create_time               DATETIME     NULL
  COMMENT '创建时间',
  update_time               DATETIME     NULL
  COMMENT '更新时间'
)
  COMMENT '用户角色关联表'
  ENGINE = InnoDB;

DROP TABLE IF EXISTS auth;
CREATE TABLE auth
(
  id                        BIGINT UNSIGNED AUTO_INCREMENT
  COMMENT '主键ID'
    PRIMARY KEY,
  auth_name                 VARCHAR(50) NOT NULL
  COMMENT '权限名称',
  auth_code                 VARCHAR(50) NOT NULL
  COMMENT '权限编码',
  delete_flag               CHAR(2)     DEFAULT 'N' NULL
  COMMENT '删除标记 N/Y',
  create_time               DATETIME     NULL
  COMMENT '创建时间',
  update_time               DATETIME     NULL
  COMMENT '更新时间'
)
  COMMENT '用户角色关联表'
  ENGINE = InnoDB;

DROP TABLE IF EXISTS role_auth;
CREATE TABLE role_auth
(
  id                        BIGINT UNSIGNED AUTO_INCREMENT
  COMMENT '主键ID'
    PRIMARY KEY,
  role_id                   BIGINT UNSIGNED NOT NULL
  COMMENT '角色ID',
  auth_id                   BIGINT UNSIGNED NOT NULL
  COMMENT '权限ID',
  delete_flag               CHAR(2)     DEFAULT 'N' NULL
  COMMENT '删除标记 N/Y',
  create_time               DATETIME     NULL
  COMMENT '创建时间',
  update_time               DATETIME     NULL
  COMMENT '更新时间'
)
  COMMENT '角色权限关联表'
  ENGINE = InnoDB;

DROP TABLE IF EXISTS user_group;
CREATE TABLE user_group
(
  id                        BIGINT UNSIGNED AUTO_INCREMENT
  COMMENT '主键ID'
    PRIMARY KEY,
  group_name                VARCHAR(50) NOT NULL
  COMMENT '组名称',
  admin_user_id             BIGINT UNSIGNED NOT NULL
  COMMENT '组管理员ID',
  group_desc                VARCHAR(500)  NULL
  COMMENT '组介绍',
  group_code                VARCHAR(200)    NULL
  COMMENT '组标识',
  delete_flag               CHAR(2)     DEFAULT 'N' NULL
  COMMENT '删除标记 N/Y',
  create_time               DATETIME     NULL
  COMMENT '创建时间',
  update_time               DATETIME     NULL
  COMMENT '更新时间'
)
  COMMENT '用户组表'
  ENGINE = InnoDB;

DROP TABLE IF EXISTS user_to_group;
CREATE TABLE user_to_group
(
  id                        BIGINT UNSIGNED AUTO_INCREMENT
  COMMENT '主键ID'
    PRIMARY KEY,
  group_id                  BIGINT UNSIGNED NOT NULL
  COMMENT '组ID',
  user_id                   BIGINT UNSIGNED NOT NULL
  COMMENT '用户ID',
  group_role                CHAR(2)     DEFAULT '1' NULL
  COMMENT '0:管理员/1:成员',
  delete_flag               CHAR(2)     DEFAULT 'N' NULL
  COMMENT '删除标记 N/Y',
  create_time               DATETIME     NULL
  COMMENT '创建时间',
  update_time               DATETIME     NULL
  COMMENT '更新时间'
)
  COMMENT '用户与组关联表'
  ENGINE = InnoDB;