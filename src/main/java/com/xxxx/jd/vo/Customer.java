package com.xxxx.jd.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_customer
 * @author 
 */
@Data
public class Customer implements Serializable {
    private Integer id;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 客户电话
     */
    private String phone;

    /**
     * 客户地址
     */
    private String address;

    private Integer isValid;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    private static final long serialVersionUID = 1L;
}