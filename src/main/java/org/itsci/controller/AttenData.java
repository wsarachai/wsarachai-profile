package org.itsci.controller;

import org.itsci.model.EAttendanceStatus;
import org.itsci.model.Student;

public class AttenData {
    private Student student;
    private String status;
    private EAttendanceStatus[] attenLec = new EAttendanceStatus[15];
    private EAttendanceStatus [] attenLab = new EAttendanceStatus[15];

    public AttenData(Student student, String status) {
        this.student = student;
        this.status = status;
        for (int i = 0; i < 15; i++) {
            attenLec[i] = EAttendanceStatus.NA;
            attenLab[i] = EAttendanceStatus.NA;
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

    public EAttendanceStatus[] getAttenLec() {
        return attenLec;
    }

    public void setAttenLec(EAttendanceStatus[] attenLec) {
        this.attenLec = attenLec;
    }

    public EAttendanceStatus[] getAttenLab() {
        return attenLab;
    }

    public void setAttenLab(EAttendanceStatus[] attenLab) {
        this.attenLab = attenLab;
    }
}
