package com.web.dao.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhang-rongyao
 * @version V1.0
 * @Package com.fapiao.layui.model
 * @date 2020/12/22/022
 */
@Data
@Entity
@Table(name = "permission_t")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 6991235513096205138L;

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    /*@ManyToMany
    @JoinTable(name = "role_permission_t", joinColumns = { @JoinColumn(name = "pid") }, inverseJoinColumns = {
            @JoinColumn(name = "rid") })
    private List<SysRole> roles;*/
    @Override
    public String toString() {
        return "SysPermission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
