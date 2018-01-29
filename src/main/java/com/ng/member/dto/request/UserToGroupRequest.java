package com.ng.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niuguang
 * @date 18-1-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToGroupRequest {

    /**
     * 组id
     */
    private Long groupId;

    /**
     * 操作的用户id
     */
    private Long userId;

    /**
     * 操作的用户名
     */
    private String userName;



}
