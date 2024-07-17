package org.itsci.controller.bean;

import org.itsci.model.Member;

public class MemberBean {
    private long id;
    private String username;
    private String password;
    private String confirmPassword;
    private String newPassword;
    private String prename;
    private String firstName;
    private String lastName;
    private String address;

    public MemberBean() {
    }

    public MemberBean(Member member) {
        this.id = member.getId();
        this.username = member.getLogin().getUsername();
        this.password = member.getLogin().getPassword();
        this.prename = member.getPrename();
        this.firstName = member.getFirstName();
        this.lastName = member.getLastName();
        this.address = member.getAddress();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
