package com.springcloud.alibaba.common.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "shop_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 商品id
     */
    private Integer pid;

    /**
     * 商品名称
     */
    private String pname;

    /**
     * 商品单价
     */
    private Double pprice;

    /**
     * 购买数量
     */
    private Integer number;


}
