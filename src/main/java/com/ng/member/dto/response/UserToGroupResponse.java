package com.ng.member.dto.response;

import com.ng.member.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author niuguang
 * @date 18-1-18
 */
@Data
@Builder
public class UserToGroupResponse {

    /**
     * 组ID
     */
    private Long groupId;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 组成员列表
     */
    private List<User> groupUserList;

}
