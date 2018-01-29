package com.ng.member.mapper;

import com.ng.member.entity.User;
import com.ng.member.entity.UserToGroup;
import com.ng.member.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mybatis generator
 * @date 2018/01/17
 */
public interface UserToGroupMapper extends MyMapper<UserToGroup> {

    Integer delUserToGroup(@Param("groupId") Long groupId);

    Integer delUserToGroupUser(@Param("groupId") Long groupId,@Param("userId") Long userId);

    List<User> queryUserToGroupByUserName(@Param("groupId") Long groupId, @Param("userName") String userName);

    List<Long> queryUserIdByGroupList(@Param("groupId") List<Long> groupId);
}