package com.bink.mapper.base;

import com.bink.entity.User;

/**
 * Created by chenbinghao on 16/8/31.
 */
public interface UserMapper {

    User getUserByName(String userName);

}
