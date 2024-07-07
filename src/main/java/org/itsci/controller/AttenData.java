package org.itsci.controller;

import org.itsci.model.Student;

public class AttenData {
    private Student student;
    private String status;
    private String [] attenLec = new String[15];
    private String [] attenLab = new String[15];

    public AttenData(Student student, String status) {
        this.student = student;
        this.status = status;
        for (int i = 0; i < 15; i++) {
            attenLec[i] = "0";
            attenLab[i] = "0";
        }
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getAttenLec() {
        return attenLec;
    }

    public void setAttenLec(String[] attenLec) {
        this.attenLec = attenLec;
    }

    public String[] getAttenLab() {
        return attenLab;
    }

    public void setAttenLab(String[] attenLab) {
        this.attenLab = attenLab;
    }
}
