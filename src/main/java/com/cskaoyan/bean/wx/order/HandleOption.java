package com.cskaoyan.bean.wx.order;

public class HandleOption {
    private Boolean cancel;
    private Boolean comment;
    private Boolean confirm;
    private Boolean delete;
    private Boolean pay;
    private Boolean rebuy;
    private Boolean refund;

    public HandleOption() {
    }

    public HandleOption(Boolean cancel, Boolean comment, Boolean confirm, Boolean delete, Boolean pay, Boolean rebuy, Boolean refund) {
        this.cancel = cancel;
        this.comment = comment;
        this.confirm = confirm;
        this.delete = delete;
        this.pay = pay;
        this.rebuy = rebuy;
        this.refund = refund;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

    public Boolean getComment() {
        return comment;
    }

    public void setComment(Boolean comment) {
        this.comment = comment;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Boolean getPay() {
        return pay;
    }

    public void setPay(Boolean pay) {
        this.pay = pay;
    }

    public Boolean getRebuy() {
        return rebuy;
    }

    public void setRebuy(Boolean rebuy) {
        this.rebuy = rebuy;
    }

    public Boolean getRefund() {
        return refund;
    }

    public void setRefund(Boolean refund) {
        this.refund = refund;
    }
}
