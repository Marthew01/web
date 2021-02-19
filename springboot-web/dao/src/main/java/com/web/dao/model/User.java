package com.web.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zhang-rongyao
 * @version V1.0
 * @Package com.fapiao.layui.model
 * @date 2020/12/22/022
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_t")
public class User implements Serializable {
    private static final long serialVersionUID = -7927203001962811150L;
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
    private String salt;
    private long experience;//积分
    private String sex;
    private String city;
    private String classify;//职业
    private long wealth;//财富
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;//


    @JsonIgnore //在实体类中一对多的引用属性上加上注解@JsonIgnore,表示查询的时候不查询这个属性//)在实体类中多对一的引用属性上加上注解@JsonIgnoreProperties,表示查询的时候不查询这个引用属性中的哪些属性(需要手动定义不需要查询哪些属性)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_t", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns = {
            @JoinColumn(name = "rid") })
    private List<SysRole> roles;

    public String getCredentialsSalt() {
        return username + salt + salt;
    }

    public User(long id, String username, String password, long experience, String sex, String city, String classify, long wealth, Date createDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.experience = experience;
        this.sex = sex;
        this.city = city;
        this.classify = classify;
        this.wealth = wealth;
        this.createDate = createDate;
    }
}