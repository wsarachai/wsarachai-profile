package org.itsci.controller;

import org.itsci.model.EAttendanceStatus;
import org.itsci.model.Enrollment;
import org.itsci.model.Student;

public class AttenData {
    private Enrollment enrollment;
    private EAttendanceStatus[] attenLec = new EAttendanceStatus[15];
    private EAttendanceStatus [] attenLab = new EAttendanceStatus[15];

    public AttenData(Enrollment enrollment) {
        this.enrollment = enrollment;
        for (int i = 0; i < 15; i++) {
            attenLec[i] = EAttendanceStatus.NA;
            attenLab[i] = EAttendanceStatus.NA;
        }
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
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
