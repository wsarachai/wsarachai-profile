package org.itsci.dao;

import org.itsci.model.Teacher;

import java.util.List;

public interface TeacherDao {
  List<Teacher> findAll();

  Teacher getById(Long id);

  void save(Teacher teacher);

  void update(Teacher teacher);

  void delete(Long id);
}
