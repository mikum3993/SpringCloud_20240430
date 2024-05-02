package com.itheima.mp.domain.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.execution.JupiterEngineExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IUserviceTest {
    @Autowired
    private IUservice usersvice;

    @Test
    void insert() {
        User user = new User();
        user.setId(5L);
        user.setUsername("LiLei");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        // user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setInfo(UserInfo.of(24,"英文老师","female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        usersvice.save(user);
    }

    private User builder(int i) {
        User user = new User();
        user.setId(5L);
        user.setUsername("LiLei" + i);
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        // user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setInfo(UserInfo.of(24,"英文老师","female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        usersvice.save(user);
        return user;
    }

    @Test
    void testSaveOnByOne() {
        long b = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            usersvice.save(builder(i));
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时: " + (e - b));
    }

    @Test
    void testSaveBatch() {


        ArrayList<User> list = new ArrayList<>(1000);
        long b = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            list.add(builder(i));
            if (i % 1000 == 0) {
                usersvice.saveBatch(list);
                list.clear();
            }
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时: " + (e - b));
    }

    @Test
    void testPageQuery(){
        int pageNo =1,pageSize=2;
        Page<User> page = Page.of(pageNo, pageSize);
        page.addOrder(new OrderItem("balance",true));
        page.addOrder(new OrderItem("id",true));

        Page<User> p = usersvice.page(page);

        System.out.println(p.getTotal());

        System.out.println(p.getPages());

        List<User> users = p.getRecords();
        users.forEach(System.out::println);
        
    }
}