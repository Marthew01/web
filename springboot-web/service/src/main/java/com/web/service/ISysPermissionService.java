package com.web.service;

import com.web.dao.model.entity.SysPermission;
import com.web.dao.model.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.service
 * @date 2021/2/3/003 15:12
 */
public interface ISysPermissionService {

    /**
     * 根据Role查询SysPermission
     * @param sysRole
     * @return java.util.List<com.web.model.SysPermissionDto>
     * @author dolyw.com
     * @date 2018/8/31 11:30
     */
    List<SysPermission> findPermissionByRoles(SysRole sysRole);

}
