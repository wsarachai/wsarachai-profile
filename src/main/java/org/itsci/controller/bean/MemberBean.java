package org.itsci.controller.bean;

import org.itsci.model.Login;
import org.itsci.model.Member;

public class MemberBean {
    private String username;
    private String password;
    private String confirmPassword;
    private String newPassword;
    private Member member;

    public MemberBean() {
    }

    public MemberBean(Login login) {
        this.username = login.getUsername();
        this.password = login.getPassword();
        this.confirmPassword = "";
        this.newPassword = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
