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
public class UserGroupRequest {

    /**
     * 组id
     */
    private Long id;

    /**
     * 组名称
     */
    private String groupName;


    /**
     * 组简介
     */
    private String groupDesc;



}
