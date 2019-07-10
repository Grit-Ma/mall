package com.cskaoyan.bean.sys;

public class ChangePassword {
    String newPassword;
    String newPassword2;
    String oldPassword;

    public ChangePassword() {
    }

    public ChangePassword(String newPassword, String newPassword2, String oldPassword) {
        this.newPassword = newPassword;
        this.newPassword2 = newPassword2;
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
