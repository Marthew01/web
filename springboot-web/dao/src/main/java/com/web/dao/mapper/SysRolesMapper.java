package com.web.dao.mapper;

import com.web.dao.model.SysRoleDto;
import com.web.dao.model.UserDto;
import com.web.dao.model.entity.SysRole;
import com.web.dao.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.fapiao.layui.dao
 * @date 2021/1/6/006 16:33
 */
@Repository
public interface SysRolesMapper {

    /**
     * 根据User查询Role
     * @param account
     * @return java.util.List<com.web.dao.mapper>
     * @author dolyw.com
     * @date 2018/8/31 11:30
     */
    List<SysRole> findRoleByUserAccount(@Param("account") String account);

}
