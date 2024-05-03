package com.itheima.mp.domain.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.service.IUservice;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.emuns.UserStatus;
import com.itheima.mp.mapper.UserMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
        if (user == null || user.getStatus() == UserStatus.FRPZEN) {
            throw new RuntimeException("用户状态异常");
        }
        // 校验余额是否充足
        if (user.getBalance() < money) {
            throw new RuntimeException("余额不足");
        }

        // 扣减余额
        // baseMapper.deductBalance(id, money);
        int remainBalance = user.getBalance() - money;
        // 修改
        lambdaUpdate()
                .set(User::getBalance, remainBalance)
                .set(remainBalance == 0, User::getStatus, UserStatus.FRPZEN)
                .eq(User::getId, id)
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

    @Override
    public UserVO queryUserAndAddressById(Long id) {
        // 查询用户
        // User user = this.getById(id);
        // if (user == null || user.getStatus() == UserStatus.FRPZEN) {
        //     throw new RuntimeException("用户异常");
        // }
        // // //查询地址
        // // // 2.查询收货地址
        // List<Address> addresses = Db.lambdaQuery(Address.class)
        //         .eq(Address::getUserId, id)
        //         .list();
        // // // 3.处理vo 转User的PO为VO
        // UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        //
        // // 转地址VO
        // if (CollUtil.isNotEmpty(addresses)) {
        //     userVO.setAddresses(
        //             BeanUtil.copyToList(addresses, AddressVO.class));
        // }
        // return userVO;
        return null;
    }

    @Override
    public List<UserVO> queryUserAndAddressByIds(List<Long> ids) {
        // // 1.查询用户
        // List<User> users = listByIds(ids);
        // if (CollUtil.isEmpty(users)) {
        //     return Collections.emptyList();
        // }
        // // 2.查询地址
        // // 2.1获取用户id集合
        // List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        //
        // // 2.2 根据用户id查询地址
        // List<Address> addresses = Db.lambdaQuery(Address.class)
        //         .in(Address::getUserId, userIds)
        //         .list();
        // // 2.3转换地址VO
        // List<AddressVO> addressVOList = BeanUtil.copyToList(addresses, AddressVO.class);
        // // 2.4 梳理地址集合(用户地址及和分组处理)   ： 分类整理，相同用户的放入一个集合中
        // Map<Long, List<AddressVO>> addressMap = new HashMap<>(0);
        // if (CollUtil.isNotEmpty(addressVOList)) {
        //     addressMap = addressVOList.stream()
        //             .collect(Collectors.groupingBy(AddressVO::getUserId));
        // }
        //
        // // 3.转VO返回
        // List<UserVO> list = new ArrayList<>(users.size());
        //
        // for (User user : users) {
        //     // 3.1转UserVO
        //     UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
        //     list.add(vo);
        //     // 3.2转换地址VO
        //     vo.setAddresses(addressMap.get(user.getId()));
        // }
        //
        // return list;
        return null;
    }

    @Override
    public PageDTO<UserVO> queryUserPage(UserQuery query) {
        String name = query.getName();
        Integer status = query.getStatus();
        // 1.构建分页查询条件
        // 1.1.分页条件
        Page<User> page = Page.of(query.getPageNo(), query.getPageSize());
        // 1.2 排序条件
        if (StrUtil.isNotBlank(query.getSortBy())) {
            // 不为空
            page.addOrder(new OrderItem(query.getSortBy(), query.isAsc()));
        } else {
            // 为空，默认以更新时间排序
            page.addOrder(new OrderItem("update_time", false));
        }
        // Page<User> page = query.toMpPageDefaultSortByUpdateTime();
        // 2.分页查询
        Page<User> p = lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .page(page);

        // 3.封装VO结果
        // PageDTO<UserVO> dto = new PageDTO<>();
        // // 3.1 总条数
        // dto.setTotal(p.getTotal());
        // // 3.2总页数
        // dto.setTotal(p.getPages());
        // // 3.3当前数据
        // List<User> records = p.getRecords();
        // if (CollUtil.isEmpty(records)) {
        //     dto.setList(Collections.emptyList());
        //     return dto;
        // }
        // // 3.4 拷贝UserVO
        // // List<UserVO> userVOS = BeanUtil.copyToList(records, UserVO.class);
        // dto.setList(BeanUtil.copyToList(records, UserVO.class));
        // // 4.返回
        // return dto;
        // return PageDTO.of(p, UserVO.class);
        // return PageDTO.of(p, user -> BeanUtil.copyProperties(user, UserVO.class));
        return PageDTO.of(p, user -> {
            // 1.拷贝基础属性
            UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
            // 2.处理特殊逻辑
            vo.setUsername(vo.getUsername().substring(0, vo.getUsername().length() - 2) + "**");
            return vo;
        });
    }
}
