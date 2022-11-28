package com.xxxx.jd.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * t_user
 * @author 
 */
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    private String userName;

    private String userPwd;

    private String trueName;

    private String email;

    private String phone;

    private Integer isValid;

    private String roleIds;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDate;

    private static final long serialVersionUID = 1L;
}