package com.xxxx.jd.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * t_order_product
 * @author 
 */
@Data
public class OrderProduct implements Serializable {
    private Integer id;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 产品数量
     */
    private Integer number;

    private Integer isValid;

    private static final long serialVersionUID = 1L;
}