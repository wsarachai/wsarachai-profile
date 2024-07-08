package org.itsci.dao;

import org.itsci.model.Attendance;

public interface AttendanceDao {
    void saveAttendance(Attendance attendance);
    Attendance updateAttendance(Attendance attendance);
    void deleteAttendance(Long id);
    Attendance findAttendanceById(long id);
}
