package org.itsci.service;

import org.itsci.controller.bean.CourseManagementBean;
import org.itsci.model.*;

import java.util.List;

/**
 * Service interface for Course Management operations
 */
public interface CourseManagementService {

  // Course operations
  List<Course> findAllCoursesWithDetails();
  List<Course> findCoursesBySemester(String semester);

  Course findCourseById(Long id);

  Course createCourse(CourseManagementBean bean);

  Course updateCourse(CourseManagementBean bean);

  void deleteCourse(Long id);

  // Section operations
  Section findSectionById(Long id);

  Section createSection(CourseManagementBean bean);

  Section updateSection(CourseManagementBean bean);

  void deleteSection(Long id);

  // Teacher assignment operations
  TeacherCourse assignTeacherToCourse(Long teacherId, Long courseId, String status);

  void removeTeacherAssignment(Long teacherCourseId);

  List<TeacherCourse> findTeacherAssignmentsByCourse(Long courseId);

  // Supporting data operations
  List<Subject> findAllSubjects();

  List<Teacher> findAllTeachers();

  List<Room> findAllRooms();

  List<Curriculum> findAllCurriculums();
}
