package com.web.admin;

import com.web.dao.mapper.SysPermissionMapper;
import com.web.dao.mapper.SysRolesMapper;
import com.web.dao.mapper.UserMapper;
import com.web.dao.model.SysRoleDto;
import com.web.dao.model.UserDto;
import com.web.dao.model.entity.SysPermission;
import com.web.dao.model.entity.SysRole;
import com.web.dao.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
@EnableAutoConfiguration
@PropertySource(value={"classpath:aaa.properties"})
class AdminApplicationTests {

    @Value("${redis.key}")
    private String redisKey;

    @Value("${jedis.key}")
    private String jedisKey;

    @Autowired
    UserMapper userMapper;
    @Autowired
    private SysRolesMapper sysRolesMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Test
    void test() {

        //一个用户多重角色
        List<SysRole> list = sysRolesMapper.findRoleByUserAccount("li2");
        log.info("[{}]",list);
        for(SysRole sysRoleDto:list){
            List<SysPermission> permissionByRoles = sysPermissionMapper.findPermissionByRoles(sysRoleDto);
            log.info("[{}]",permissionByRoles);
        }

    }

    @Test
    void test1() {



    }

}
