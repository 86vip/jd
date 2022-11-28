package com.xxxx.jd.base;

public class Result {
    public static ResultInfo success() {
        return new ResultInfo();
    }

    public static ResultInfo success(String msg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public static ResultInfo success(String msg, Object result) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(msg);
        resultInfo.setResult(result);
        return resultInfo;
    }

    public static ResultInfo fail() {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(300);
        resultInfo.setMsg("发生错误！");
        return resultInfo;
    }

    public static ResultInfo fail(String msg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(300);
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public static ResultInfo fail(String msg, Object result) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(300);
        resultInfo.setMsg(msg);
        resultInfo.setResult(result);
        return resultInfo;
    }
}
