package com.ng.member.util;

import com.ng.member.entity.JwtUser;
import com.ng.member.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author niuguang
 * @date 18-1-16
 */
public class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(UserInfo user, List<String> roles) {
        return JwtUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .userInfo(user)
                .nonLocked(true)
                .enabled(true)
                .authorities(mapToGrantedAuthorities(roles))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
