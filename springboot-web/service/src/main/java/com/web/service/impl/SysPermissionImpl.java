package com.web.service.impl;

import com.web.dao.mapper.SysPermissionMapper;
import com.web.dao.model.entity.SysPermission;
import com.web.dao.model.entity.SysRole;
import com.web.service.ISysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.service.impl
 * @date 2021/2/3/003 15:18
 */
@Slf4j
@Service
public class SysPermissionImpl implements ISysPermissionService {

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> findPermissionByRoles(SysRole sysRole) {
        return sysPermissionMapper.findPermissionByRoles(sysRole);
    }
}
