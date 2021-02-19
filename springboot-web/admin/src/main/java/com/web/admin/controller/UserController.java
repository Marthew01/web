package com.web.admin.controller;

import com.web.dao.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 *
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.fapiao.layui.controller
 * @date 2021/1/7/007 16:32
 */
@Slf4j
@RestController
//@RequestMapping(value = "/user")
public class UserController {


    /*@Autowired
    UserService userService;*/

    @RequiresPermissions(value = {"Retrieve", "admin"})
    @RequestMapping("/getUser/{str}")
    @Cacheable(value = "user-key", key = "'EvrAlarm-'+#str")
    public List<User> getUser(@PathVariable String str) {
        User user = new User();
        user.setUsername("ccc");
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(user);

        users.add(user);
        log.info("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return users;
    }

    @RequiresPermissions(value = {"Create"})
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @RequestMapping("/addUser")
    public User addUser() {
        log.info("添加用户");
        User user = new User();
        user.setUsername("ccc");
        return user;
    }


}
