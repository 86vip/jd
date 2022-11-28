package com.xxxx.jd.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_user_role
 * @author 
 */
@Data
public class UserRole implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Date createDate;

    private Date updateDate;

    private static final long serialVersionUID = 1L;
}