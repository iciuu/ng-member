package com.ng.member.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author niuguang
 * @date 18-1-16
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}

