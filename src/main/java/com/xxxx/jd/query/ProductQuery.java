package com.xxxx.jd.query;

import com.xxxx.jd.base.BaseQuery;
import lombok.Data;

@Data
public class ProductQuery extends BaseQuery {
    private String name;
    private String merchant;
    private String type;
}
