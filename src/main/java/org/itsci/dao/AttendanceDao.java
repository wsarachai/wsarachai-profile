package org.itsci.dao;

import org.itsci.model.Attendance;

import java.util.List;

public interface AttendanceDao {
    void save(Attendance attendance);
    void update(Attendance attendance);
    void delete(Long id);
    Attendance findById(long id);
    List<Attendance> findAll();
}
