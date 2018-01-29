package com.ng.member.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author niuguang
 * @date 18-1-17
 */
@Data
@Builder
public class GenericResponse<T> {
    private int code;
    private String msg;
    private T data;
}
