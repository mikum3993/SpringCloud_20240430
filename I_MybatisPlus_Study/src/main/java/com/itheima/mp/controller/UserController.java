package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.service.IUservice;
import com.itheima.mp.domain.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: SpringCloud_Study
 * @description:
 * @author: {}
 * @create: 2024/5/1 15:15
 */
@RestController
@RequestMapping("/users")
@Api(tags = "用户管理接口")
@RequiredArgsConstructor
public class UserController {

    private final IUservice userService;

    @ApiOperation("新增用户")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userFormDTO) {
        User user = new User();
        BeanUtil.copyProperties(userFormDTO, user);
        userService.save(user);
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("/{id}")
    public void deleteUserById(@ApiParam("用户id") @PathVariable("id") Long id) {
        userService.removeById(id);
    }

    @ApiOperation("根据id查询用户接口")
    @GetMapping("/{id}")
    public UserVO selectUserById(@ApiParam("用户id") @PathVariable("id") Long id) {

        return userService.queryUserAndAddressById(id);
        // userService.getById(id);
    }

    @ApiOperation("根据id批量查询用户接口")
    @GetMapping("")
    public List<UserVO> selectUserByIds(@ApiParam("用户id集合") @RequestParam("ids") List<Long> ids) {

        // List<User> users = userService.listByIds(ids);
        //
        // return BeanUtil.copyToList(users, UserVO.class);

        return userService.queryUserAndAddressByIds(ids);
    }

    @ApiOperation("扣减用户余额操作")
    @PutMapping("/{id}/dediction/{money}")
    public void deductMoneyById(
            @ApiParam("用户id") @PathVariable("id") Long id,
            @ApiParam("金额") @PathVariable("money") Integer money) {
        userService.deductMoneyById(id,money);
    }

    @ApiOperation("根据复杂条件查询用户接口")
    @GetMapping("/list")
    public List<UserVO> queryUsers(UserQuery query) {
        //查询用户PO
        List<User> users=userService.queryUsers(query.getName()
                ,query.getStatus()
                ,query.getMinBalance()
                ,query.getMaxBalance());
        return BeanUtil.copyToList(users, UserVO.class);
    }
    @ApiOperation("分页查询用户")
    @GetMapping("/page")
    public PageDTO<UserVO> queryUsersPage(UserQuery query) {
        return userService.queryUserPage(query);
    }
}
