package com.itheima.mp.emuns;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import javassist.runtime.Desc;
import lombok.Getter;

/**
 * @program: SpringCloud_Study
 * @description:
 * @author: {}
 * @create: 2024/5/2 18:07
 */
@Getter
public enum UserStatus {
    NORMAL(1, "正常"),
    FRPZEN(2, "冻结");

    @EnumValue
    private final int value;
    @JsonFormat
    private final String desc;

    UserStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
