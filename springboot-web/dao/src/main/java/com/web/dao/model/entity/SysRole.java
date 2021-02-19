package com.web.dao.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang-rongyao
 * @version V1.0
 * @Package com.example.boot_shiro.entity
 * @date 2020/12/22/022
 */
@Data
@Entity
@Table(name = "role_t")
public class SysRole implements Serializable {

    private static final long serialVersionUID = -1093536782396181761L;
    @Id
    @GeneratedValue
    private Integer id;
    private String role;

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission_t", joinColumns = { @JoinColumn(name = "rid") }, inverseJoinColumns = {
            @JoinColumn(name = "pid") })
    private List<SysPermission> permissions;

    @ManyToMany
    @JoinTable(name = "user_role_t", joinColumns = { @JoinColumn(name = "rid") }, inverseJoinColumns = {
            @JoinColumn(name = "uid") })
    private List<User> users;*/

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}