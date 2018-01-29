package com.ng.member.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author niuguang
 * @date 18-1-17
 */
public class UnifiedLoginAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public UnifiedLoginAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}