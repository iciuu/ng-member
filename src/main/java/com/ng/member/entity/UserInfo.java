package com.ng.member.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author niuguang
 * @date 18-1-17
 */
@Data
public class UserInfo extends User {

    private UserDetail userDetail;

    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
