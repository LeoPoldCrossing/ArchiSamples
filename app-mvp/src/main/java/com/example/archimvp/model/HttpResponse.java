package com.example.archimvp.model;

/**
 * Created by LeoPoldCrossing on 2017/3/15.
 */

public class HttpResponse<T> {
    private int code;
    private String msg;
    private T newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getNewslist() {
        return newslist;
    }

    public void setNewslist(T newslist) {
        this.newslist = newslist;
    }
}
