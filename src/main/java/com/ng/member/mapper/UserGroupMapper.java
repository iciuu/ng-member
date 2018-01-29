package com.ng.member.mapper;

import com.ng.member.entity.UserGroup;
import com.ng.member.util.MyMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author mybatis generator
 * @date 2018/01/18
 */
public interface UserGroupMapper extends MyMapper<UserGroup> {

    Integer delUserGroup(@Param("id") Long id,@Param("adminUserId") Long adminUserId);
}