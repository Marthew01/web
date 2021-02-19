package com.web.service;

import com.web.dao.model.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.service
 * @date 2021/2/3/003 15:12
 */
public interface ISysRoleService {

    /**
     * 根据User查询Role
     * @param account
     * @return java.util.List<com.wang.model.RoleDto>
     * @author dolyw.com
     * @date 2018/8/31 11:30
     */
    List<SysRole> findRoleByUserAccount(@Param("account") String account);

}
