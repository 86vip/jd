package com.xxxx.jd.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_product
 * @author 
 */
@Data
public class Product implements Serializable {
    /**
     * 产品id
     */
    private Integer id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品商家
     */
    private String merchant;

    /**
     * 产品类型
     */
    private String type;

    /**
     * 引进日期
     */
    private Date createDate;

    /**
     * 更新日期
     */
    private Date updateDate;

    private Integer isValid;

    private static final long serialVersionUID = 1L;
}