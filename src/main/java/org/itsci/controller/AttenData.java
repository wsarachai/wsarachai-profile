package org.itsci.controller;

import org.itsci.model.Attendance;
import org.itsci.model.EAttendanceStatus;
import org.itsci.model.Enrollment;

public class AttenData {
    public static final int numberOfWeek = 16;
    private Enrollment enrollment;
    private Attendance[] attenLec = new Attendance[numberOfWeek];
    private Attendance[] attenLab = new Attendance[numberOfWeek];

    public AttenData(Enrollment enrollment) {
        this.enrollment = enrollment;
        for (int i = 0; i < numberOfWeek; i++) {
            attenLec[i] = new Attendance();
            attenLab[i] = new Attendance();
            attenLab[i].setStatus(EAttendanceStatus.NA);
            attenLec[i].setStatus(EAttendanceStatus.NA);
        }
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public Attendance[] getAttenLec() {
        return attenLec;
    }

    public void setAttenLec(Attendance[] attenLec) {
        this.attenLec = attenLec;
    }

    public Attendance[] getAttenLab() {
        return attenLab;
    }

    public void setAttenLab(Attendance[] attenLab) {
        this.attenLab = attenLab;
    }
}
