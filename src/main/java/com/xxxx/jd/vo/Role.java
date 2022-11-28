package com.xxxx.jd.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_role
 * @author 
 */
@Data
public class Role implements Serializable {
    private Integer id;

    private String roleName;

    /**
     * 备注
     */
    private String roleRemark;

    private Date createDate;

    private Date updateDate;

    private Integer isValid;

    private static final long serialVersionUID = 1L;
}