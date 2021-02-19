package com.web.dao.mapper;

import com.web.dao.model.UserDto;
import com.web.dao.model.entity.SysRole;
import com.web.dao.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.fapiao.layui.dao
 * @date 2021/1/6/006 16:33
 */
@Repository
public interface UserMapper {

    public UserDto findOne(User user);

    public User findById(Long id);

    public User findByUsername(String username);

    public List<User> findAll();

}
