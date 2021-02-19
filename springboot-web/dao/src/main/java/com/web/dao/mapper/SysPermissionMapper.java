package com.web.dao.mapper;

import com.web.dao.model.SysRoleDto;
import com.web.dao.model.SysPermissionDto;
import com.web.dao.model.entity.SysPermission;
import com.web.dao.model.entity.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.dao.mapper
 * @date 2021/1/6/006 16:33
 */
@Repository
public interface SysPermissionMapper {

    /**
     * 根据Role查询SysPermission
     * @param sysRole
     * @return java.util.List<com.web.model.SysPermissionDto>
     * @author dolyw.com
     * @date 2018/8/31 11:30
     */
    List<SysPermission> findPermissionByRoles(SysRole sysRole);
}
