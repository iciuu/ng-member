package com.ng.member.mapper;

import com.ng.member.entity.User;
import com.ng.member.entity.UserDetail;
import com.ng.member.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mybatis generator
 * @date 2018/01/17
 */
public interface UserMapper extends MyMapper<User> {

    UserDetail selectUserInfo(@Param("userName") String userName);

    List<User> selectUserList(@Param("userIdList") List<Long> userIdList);
}