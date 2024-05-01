package com.itheima.mp.domain.service.Impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.service.IUservice;
import com.itheima.mp.mapper.UserMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: SpringCloud_Study
 * @description:
 * @author: {}
 * @create: 2024/5/1 14:45
 */
public class IUserviceImpl extends ServiceImpl<UserMapper, User> implements IUservice {

    @Override
    @Transactional
    public void deductMoneyById(Long id, Integer money) {
        // 查询用户
        User user = this.getById(id);
        // 校验用户状态
        if (user == null || user.getStatus() == 2) {
            throw new RuntimeException("用户状态异常");
        }
        // 校验余额是否充足
        if (user.getBalance() < money) {
            throw new RuntimeException("余额不足");
        }

        // 扣减余额
        // baseMapper.deductBalance(id, money);
        int remainBalance = user.getBalance() - money;
        //修改
        lambdaUpdate()
                .set(User::getBalance, remainBalance)
                .set(remainBalance == 0, User::getStatus, 2)
                .eq(User::getId,id)
                .update();
    }

    @Override
    public List<User> queryUsers(String name, Integer status, Integer minBalance, Integer maxBalance) {
        // List<User> users = lambdaQuery()
        //         .like(name != null, User::getUsername, name)
        //         .eq(status != null, User::getStatus, status)
        //         .ge(minBalance != null, User::getBalance, minBalance)
        //         .ge(maxBalance != null, User::getBalance, maxBalance)
        //         .list();

        return lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .ge(maxBalance != null, User::getBalance, maxBalance)
                .list();
    }
}
