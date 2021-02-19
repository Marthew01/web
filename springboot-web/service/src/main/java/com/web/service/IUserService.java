package com.web.service;

import com.web.dao.model.common.RestPage;
import com.web.dao.model.entity.User;

import java.util.List;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.fapiao.layui.service
 * @date 2021/1/6/006 16:32
 */

public interface IUserService {

    /**
     * id 查询用户
     * @param id
     * @return
     */
    public User findUserById(long id);

    /**
     * 有问题 用户名查询用户
     * @param name
     * @return
     */
    public User findUserByName(String name);

    /**
     * 账号查询用户
     * @param account
     * @return
     */
    public User findUserByAccount(String account);

    /**
     * 查询用户列表
     * @return
     */
    public List<User> getUserList();

    /**
     * 保存
     * @param user
     */
    public void save(User user);

    /**
     * 编辑
     * @param user
     */
    public void edit(User user);

    /**
     * 删除
     * @param id
     */
    public void delete(long id);

    /**
     * 分页查询
     * @param user
     * @return
     */
    public RestPage getPageList(User user);
}
