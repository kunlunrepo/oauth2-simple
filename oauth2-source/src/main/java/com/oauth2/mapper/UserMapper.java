package com.oauth2.mapper;

import com.oauth2.domain.UserPojo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /**
     * 根据用户名查询用户信息
     */
    UserPojo queryByUserName(@Param("userName") String userName);

}
