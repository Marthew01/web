package com.web.dao.jpa;

import com.web.dao.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.fapiao.layui.dao
 * @date 2021/1/6/006 16:33
 */

public interface UserRepository extends JpaRepository <User, Long> {

    /**
     * 查询user
     * @param id
     * @return
     */
    public User findById(long id);

    /**
     * 查询用户
     * @param username
     * @return
     */

    public User findByUsername(String username);


    /**
     * 查询用户
     * @param account
     * @return
     */

    public User findByAccount(String account);
    /**
     * 保存
     * @param user
     * @return
     */
    @Override
    public User save(User user);
}
