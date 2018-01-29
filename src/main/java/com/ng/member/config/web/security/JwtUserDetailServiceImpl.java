package com.ng.member.config.web.security;

import com.ng.member.dto.request.LoginRequest;
import com.ng.member.entity.UserDetail;
import com.ng.member.entity.UserInfo;
import com.ng.member.mapper.UserMapper;
import com.ng.member.util.JwtUserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static java.util.Collections.emptyList;

/**
 * @author niuguang
 * @date 18-1-17
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtUserDetailServiceImpl implements UserDetailsService {

    private static final String USER_REDIS_KEY = "USER_";

    @Autowired
    private UserMapper userMapper;

   /* @NonNull
    private RedisTemplate<String, Object> redisTemplate;*/

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LoginRequest loginRequest = new LoginRequest();
        UserInfo userInfo = null;
        /*String redisKey = USER_REDIS_KEY + userName;
        userInfo = (UserInfo) redisTemplate.opsForValue().get(redisKey);
        if (userInfo != null) {
            return JwtUserFactory.create(userInfo, new ArrayList<>());
        }*/
        UserDetail user = userMapper.selectUserInfo(userName);
        if (user == null) {
            throw new UsernameNotFoundException("未找到该用户");
        }
        userInfo =  new UserInfo(user.getUserName(), user.getPassword(), emptyList());
        userInfo.setUserDetail(user);

        //redisTemplate.opsForValue().set(redisKey, user, 24, TimeUnit.HOURS);

        return JwtUserFactory.create(userInfo,new ArrayList<>());
    }
}
