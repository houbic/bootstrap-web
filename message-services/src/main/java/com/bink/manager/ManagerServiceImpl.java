package com.bink.manager;

import com.bink.base.ManagerService;
import com.bink.entity.Menu;
import com.bink.entity.RoleType;
import com.bink.entity.User;
import com.bink.mapper.base.MenuMapper;
import com.bink.mapper.base.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by chenbinghao on 16/8/27.
 */
@Component("managerService")
public class ManagerServiceImpl implements ManagerService{

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public MenuMapper menuMapper;

    @Override
    public User getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }

    @Override
    public List<Menu> getMenuByRoleType(int roleType) {
        System.out.println("begin to get menu.........." + roleType);
        return menuMapper.getMenuByRoleType(roleType);
    }

}
