package com.cskaoyan.vo;

public class QueryVo {
    int errno;
    QueryData data;
    String errmsg;

    public QueryVo() {
    }

    public QueryVo(int errno, QueryData data, String errmsg) {
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

    public QueryData getData() {
        return data;
    }

    public void setData(QueryData data) {
        this.data = data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "QueryVo{" +
                "errno=" + errno +
                ", data=" + data +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
