package com.ng.member.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author niuguang
 * @date 18-1-17
 */
@Data
@Builder
public class LoginResponse {

    private String token;

    private Long userId;

    private String userName;

}
