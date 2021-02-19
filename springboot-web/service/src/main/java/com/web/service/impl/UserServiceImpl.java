package com.web.service.impl;

import com.web.common.util.PageSort;
import com.web.dao.jpa.UserRepository;
import com.web.dao.mapper.UserMapper;
import com.web.dao.model.common.RestPage;
import com.web.dao.model.entity.User;
import com.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang-rongyao
 * @version V 1.0
 * @Package com.web.service.impl
 * @date 2021/1/6/006 16:33
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;
    //mybatis

    @Autowired
    UserMapper userMapper;

    @Override
    public RestPage<User> getPageList(User user) {
        log.info("====@Cacheable(user)======getPageList");
        // 创建分页对象
        PageRequest page= PageSort.pageRequest(Sort.Direction.ASC);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                //模糊查询: where username like 'aaa'
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id","experience","wealth");
        log.info("=====开始数据库查询");
        Page<User> userPage = userRepository.findAll(Example.of(user, matcher), page);

        return new RestPage<User>(userPage);
    }


//    @Cacheable(value = "user",key = "#result.id")
    @Override
    public User findUserById(long id) {
        log.info("===@Cacheable(user,result.id)=========findUserById");
        return userRepository.findById(id);
    }

    @Cacheable(cacheNames = "userName",key = "#result.id")
    @Override
    public User findUserByName(String userName) {
        log.info("============findUserByName");
        return userRepository.findByUsername(userName);
    }

    @Override
    public User findUserByAccount(String account) {
        log.info("============findUserByName");
        return userRepository.findByAccount(account);
    }

    @Override
    public List<User> getUserList() {
        log.info("============getUserList");
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void edit(User user) {

    }

    @Override
    public void delete(long id) {

    }


    /**
     * @CacheEvict : 缓存清除
     * key : 指定要清除的数据
     * allEntries = true : 指定要清除所有的数据
     * beforeInvocation = false : 缓存的清除是否再方法之前执行
     *          默认代表缓存清除操作是在方法执行后执行 ； 如果出现异常缓存就不会清除
     * beforeInvocation = true : 代表清除缓存操作是在方法运行之前执行，无论方法是否出现异常，缓存都清除
     *
     * @param id
     */

    /**
     * 将方法的运行结果进行缓存，以后再要相同的数据，直接从缓存中取，不用调用方法
     *
     * CacheManager管理多个Cache组件的，对缓存的真正CRUD操作再Cache组件中，每一个缓存组件有自己唯一一个名字
     * 几个属性：
     *      cacheNames/value：指定缓存组件的名字；
     *      key:缓存数据使用的key，可以用它来指定。默认是使用方法的参数的值  1-方法的返回值
     *              编写SpEL;   #id；参数id的值   #a0  #p0  #root.args[0]
     *      keyGenerator:key的生成器；可以自己指定key的生成器的组件id
     *              key/keyGenerator:二选一使用
     *      cacheManager:指定缓存管理器；或者cacheResolver指定获取解析器  它俩也是二选一使用
     *      condition:指定符合条件的情况下才缓存
     *                          ，condition = "#id>0"
     *      unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存；可以获取到结果进行判断
     *                  unless = "#result == null"
     *      sync:是否使用异步模式
     *
     *
     * 原理：
     *      1、自动配置类 CacheAutoConfiguration
     *      2、缓存的配置
     *
     *
     * @param id
     * @return
     */

    /**
     * @cachePut : 既调用方法，又更新缓存数据；
     * 修改了数据库的某个数据，同时更新缓存；
     * 运行时机：
     *  1、先调用目标方法
     *  2、将目标方法的结果缓存起来
     * @param employee
     * @return
     *
     *
     * 测试步骤：
     *  1、查询1号员工：查询到的结果放在缓存中；
     *          key:1   value: lastName:张三
     *  2、以后查询还是之前的结果
     *  3、更新1号员工：【lastName:zhangsan;gender:0】
     *          将方法的返回值也放进缓存了；
     *          key:传入的employee对象   值：返回的employee对象；
     *  4、查询1号员工？
     *          应该是更新后的员工；
     *                  key = "#employee.id":使用传入的参数的员工id；
     *                  key = "#result.id":使用返回的id
     *                      @Cacheable的key是不能用#result
     *          为什么是没更新之前的？【1号员工没有再缓存中更新】
     */
}
