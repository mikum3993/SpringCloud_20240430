package com.itheima.mp.domain.po;

import lombok.Data;

/**
 * @program: SpringCloud_Study
 * @description:
 * @author: {}
 * @create: 2024/5/1 17:34
 */
@Data
public class Address {
    private Long id;

    private Long userId;

    private String province;

    private String city;

    private String town;

    private String mobile;

    private String street;

    private String contact;

    private Boolean isDefault;

    private String notes;
}
