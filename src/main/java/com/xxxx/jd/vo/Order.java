package com.xxxx.jd.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_order
 * @author 
 */
@Data
public class Order implements Serializable {
    /**
     * 订单号
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 送货地址
     */
    private String address;

    /**
     * 订单状态
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    private Integer isValid;

    private static final long serialVersionUID = 1L;
}