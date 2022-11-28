package com.xxxx.jd.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_permission
 * @author 
 */
@Data
public class Permission implements Serializable {
    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 模块ID
     */
    private Integer moduleId;

    /**
     * 权限值
     */
    private String aclValue;

    private Date createDate;

    private Date updateDate;

    private static final long serialVersionUID = 1L;
}