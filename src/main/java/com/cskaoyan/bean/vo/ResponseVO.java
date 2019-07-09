package com.cskaoyan.bean.vo;

public class ResponseVO<T> {
    int errno;
    T data;
    String errmsg;

    public ResponseVO() {
    }

    public ResponseVO(int errno, T data, String errmsg) {
        this.errno = errno;
        this.data = data;
        this.errmsg = errmsg;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }


    public ResponseVO unlogin(ResponseVO vo) {
        vo.setErrno(501);
        vo.setErrmsg("请登录");
        return vo;
    }

    public ResponseVO ok(ResponseVO vo) {
        vo.setErrno(0);
        vo.setErrmsg("成功");
        return vo;
    }

    public ResponseVO errParm(ResponseVO vo) {
        vo.setErrno(401);
        vo.setErrmsg("参数不对");
        return vo;
    }

    public ResponseVO errParmValue(ResponseVO vo) {
        vo.setErrno(402);
        vo.setErrmsg("参数值不对");
        return vo;
    }
}
