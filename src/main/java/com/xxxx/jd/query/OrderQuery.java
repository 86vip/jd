package com.xxxx.jd.query;

import com.xxxx.jd.base.BaseQuery;
import lombok.Data;

@Data
public class OrderQuery extends BaseQuery {
    private Integer id;
    private String customerName;
    private Integer state;
}
