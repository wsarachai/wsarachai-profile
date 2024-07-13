package org.itsci.dao;

import org.itsci.model.Attendance;

public interface AttendanceDao {
    void save(Attendance attendance);
    Attendance update(Attendance attendance);
    void delete(Long id);
    Attendance findById(long id);
}
