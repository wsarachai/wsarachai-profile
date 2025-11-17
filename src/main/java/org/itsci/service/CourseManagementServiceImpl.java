package org.itsci.service;

import org.itsci.controller.bean.CourseManagementBean;
import org.itsci.dao.*;
import org.itsci.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for Course Management operations
 */
@Service
@Transactional
public class CourseManagementServiceImpl implements CourseManagementService {

  @Autowired
  private CourseDao courseDao;

  @Autowired
  private SectionDao sectionDao;
  @Autowired
  private SubjectDao subjectDao;

  @Autowired
  private TeacherCourseDao teacherCourseDao;

  @Autowired
  private TeacherDao teacherDao;

  @Autowired
  private RoomDao roomDao;

  @Autowired
  private CurriculumDao curriculumDao;

  @Override
  @Transactional(readOnly = true)
  public List<Course> findAllCoursesWithDetails() {
    return courseDao.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Course> findCoursesBySemester(String semester) {
    return courseDao.findBySemester(semester);
  }

  @Override
  @Transactional(readOnly = true)
  public Course findCourseById(Long id) {
    return courseDao.getCourseById(id);
  }

  @Override
  public Course createCourse(CourseManagementBean bean) {
    Course course = new Course();

    Subject subject = subjectDao.getSubjectById(bean.getSubjectId());
    course.setSubject(subject);
    course.setSemester(bean.getSemester());
    course.setStartSemester(bean.getStartSemester());

    courseDao.save(course);
    return course;
  }

  @Override
  public Course updateCourse(CourseManagementBean bean) {
    Course course = courseDao.getCourseById(bean.getCourseId());

    Subject subject = subjectDao.getSubjectById(bean.getSubjectId());
    course.setSubject(subject);
    course.setSemester(bean.getSemester());
    course.setStartSemester(bean.getStartSemester());

    courseDao.update(course);
    return course;
  }

  @Override
  public void deleteCourse(Long id) {
    // For now, just delete the course (may need to handle teacher assignments
    // separately)
    courseDao.delete(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Section findSectionById(Long id) {
    return sectionDao.findById(id);
  }

  @Override
  public Section createSection(CourseManagementBean bean) {
    Section section = new Section();

    Course course = courseDao.getCourseById(bean.getCourseId());
    section.setCourse(course);
    section.setGroupNumber(bean.getGroupNumber());
    section.setNumberOfSeat(bean.getNumberOfSeat());
    section.setLecDay(bean.getLecDay());
    section.setLabDay(bean.getLabDay());
    section.setStartLectureTime(bean.getStartLectureTime());
    section.setEndLectureTime(bean.getEndLectureTime());
    section.setStartLabTime(bean.getStartLabTime());
    section.setEndLabTime(bean.getEndLabTime());
    if (bean.getLecRoomId() != null) {
      Room lecRoom = roomDao.getRoomById(bean.getLecRoomId());
      section.setLecRoom(lecRoom);
    }

    if (bean.getLabRoomId() != null) {
      Room labRoom = roomDao.getRoomById(bean.getLabRoomId());
      section.setLabRoom(labRoom);
    }

    section.setLatitue(bean.getLatitude());
    section.setLongitude(bean.getLongitude());

    sectionDao.save(section);

    // Add section to course's sections
    course.getSections().add(section);
    courseDao.update(course);

    return section;
  }

  @Override
  public Section updateSection(CourseManagementBean bean) {
    Section section = sectionDao.findById(bean.getSectionId());

    section.setGroupNumber(bean.getGroupNumber());
    section.setNumberOfSeat(bean.getNumberOfSeat());
    section.setLecDay(bean.getLecDay());
    section.setLabDay(bean.getLabDay());
    section.setStartLectureTime(bean.getStartLectureTime());
    section.setEndLectureTime(bean.getEndLectureTime());
    section.setStartLabTime(bean.getStartLabTime());
    section.setEndLabTime(bean.getEndLabTime());

    if (bean.getLecRoomId() != null) {
      Room lecRoom = roomDao.getRoomById(bean.getLecRoomId());
      section.setLecRoom(lecRoom);
    } else {
      section.setLecRoom(null);
    }

    if (bean.getLabRoomId() != null) {
      Room labRoom = roomDao.getRoomById(bean.getLabRoomId());
      section.setLabRoom(labRoom);
    } else {
      section.setLabRoom(null);
    }

    section.setLatitue(bean.getLatitude());
    section.setLongitude(bean.getLongitude());

    sectionDao.save(section);
    return section;
  }

  @Override
  public void deleteSection(Long id) {
    // Note: SectionDao doesn't have delete method, this needs to be implemented
    throw new UnsupportedOperationException("Delete section functionality not yet implemented");
  }

  @Override
  public TeacherCourse assignTeacherToCourse(Long teacherId, Long courseId, String status) {
    TeacherCourse teacherCourse = new TeacherCourse();        Teacher teacher = teacherDao.getById(teacherId);
    Course course = courseDao.getCourseById(courseId);

    teacherCourse.setTeacher(teacher);
    teacherCourse.setCourse(course);
    teacherCourse.setStatus(status);

    teacherCourseDao.save(teacherCourse);
    return teacherCourse;
  }

  @Override
  public void removeTeacherAssignment(Long teacherCourseId) {
    // Note: TeacherCourseDao doesn't have delete method, this needs to be
    // implemented
    throw new UnsupportedOperationException("Remove teacher assignment functionality not yet implemented");
  }

  @Override
  @Transactional(readOnly = true)
  public List<TeacherCourse> findTeacherAssignmentsByCourse(Long courseId) {
    // Note: TeacherCourseDao doesn't have this method, return empty list for now
    return List.of();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Subject> findAllSubjects() {
    return subjectDao.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Teacher> findAllTeachers() {
    return teacherDao.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Room> findAllRooms() {
    return roomDao.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Curriculum> findAllCurriculums() {
    return curriculumDao.findAll();
  }
}
