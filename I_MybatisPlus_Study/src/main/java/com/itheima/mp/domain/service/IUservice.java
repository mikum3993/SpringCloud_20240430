package com.itheima.mp.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.po.User;

import java.util.List;

/**
 * @program: SpringCloud_Study
 * @description:
 * @author: ${}
 * @create: 2024/5/1 14:45
 */
public interface IUservice extends IService<User> {
    void deductMoneyById(Long id, Double money);

    List<User> queryUsers(String name, Integer status, Integer minBalance, Integer maxBalance);
}
