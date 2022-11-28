package com.xxxx.jd.query;

import com.xxxx.jd.base.BaseQuery;
import lombok.Data;

@Data
public class UserQuery extends BaseQuery {
    private String userName;    // 用户名
    private String email;       // 邮箱
    private String phone;       // 手机号
}
