package com.web.service.impl;

import com.web.dao.mapper.SysRolesMapper;
import com.web.dao.model.entity.SysRole;
import com.web.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.service.impl
 * @date 2021/2/3/003 15:14
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements ISysRoleService {


    @Autowired
    SysRolesMapper sysRolesMapper;

    @Override
    public List<SysRole> findRoleByUserAccount(String account) {
       return sysRolesMapper.findRoleByUserAccount(account);
    }
}
