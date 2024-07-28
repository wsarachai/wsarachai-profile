package org.itsci.dao;

import org.itsci.model.Attendance;
import org.itsci.model.Enrollment;

import java.util.List;
import java.util.SortedSet;

public interface AttendanceDao {
    void save(Attendance attendance);
    void update(Attendance attendance);
    void delete(Long id);
    Attendance findById(long id);
    List<Attendance> findAll();
    SortedSet<Attendance> findByType(Enrollment enrollment, String type);
}
