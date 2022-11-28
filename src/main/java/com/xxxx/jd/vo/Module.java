package com.xxxx.jd.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_module
 * @author 
 */
@Data
public class Module implements Serializable {
    private Integer id;

    /**
     * 资源名称
     */
    private String moduleName;

    private Integer parentId;

    /**
     * 等级
     */
    private Integer grade;

    /**
     * 权限值
     */
    private String optValue;

    private Byte isValid;

    private Date createDate;

    private Date updateDate;

    private static final long serialVersionUID = 1L;
}