package com.itheima.mp.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: SpringCloud_Study
 * @description:
 * @author: {}
 * @create: 2024/5/2 19:00
 */
@Data
@ApiModel(description = "分页结果")
public class PageDTO<T> {
    @ApiModelProperty("总条数")
    private Long total;
    @ApiModelProperty("总页数")
    private Long pages;
    @ApiModelProperty("集合")
    private List<T> list;

    // 封装VO结果
    public static <PO, VO> PageDTO<VO> of(Page<PO> p, Class<VO> clazz) {
        PageDTO<VO> dto = new PageDTO<>();
        // 1 总条数
        dto.setTotal(p.getTotal());
        // 2总页数
        dto.setTotal(p.getPages());
        // 3当前数据
        List<PO> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            dto.setList(Collections.emptyList());
            return dto;
        }
        // 4 拷贝UserVO
        // List<UserVO> userVOS = BeanUtil.copyToList(records, UserVO.class);
        dto.setList(BeanUtil.copyToList(records, clazz));
        // 5.返回
        return dto;
    }

    public static <PO, VO> PageDTO<VO> of(Page<PO> p, Function<PO, VO> convertor) {
        PageDTO<VO> dto = new PageDTO<>();
        // 1 总条数
        dto.setTotal(p.getTotal());
        // 2总页数
        dto.setTotal(p.getPages());
        // 3当前数据
        List<PO> records = p.getRecords();
        if (CollUtil.isEmpty(records)) {
            dto.setList(Collections.emptyList());
            return dto;
        }
        // 4 拷贝UserVO
        // List<UserVO> userVOS = BeanUtil.copyToList(records, UserVO.class);

        dto.setList(records.stream().map(convertor).collect(Collectors.toList()));
        // 5.返回
        return dto;
    }


}
