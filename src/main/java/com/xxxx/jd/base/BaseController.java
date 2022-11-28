package com.xxxx.jd.base;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class BaseController {

    public ResultInfo success() {
        return new ResultInfo();
    }

    public ResultInfo success(String msg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public ResultInfo success(String msg, Object result) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(msg);
        resultInfo.setResult(result);
        return resultInfo;
    }

}
