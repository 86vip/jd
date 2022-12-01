package com.xxxx.jd.query;

import com.xxxx.jd.base.BaseQuery;
import lombok.Data;

@Data
public class CustomerQuery extends BaseQuery {
    private String name;
    private String phone;
    private String address;
}
