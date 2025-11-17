package org.itsci.controller.bean;

import org.itsci.model.EDayOfWeek;
import java.util.Date;

/**
 * Form bean for Course Management operations
 */
public class CourseManagementBean {

  // Course fields
  private Long courseId;

  private Long subjectId;

  private String semester;

  private Date startSemester;

  // Section fields
  private Long sectionId;

  private String groupNumber;

  private Integer numberOfSeat;

  private EDayOfWeek lecDay;
  private EDayOfWeek labDay;

  private String startLectureTime;
  private String endLectureTime;
  private String startLabTime;
  private String endLabTime;

  private Long lecRoomId;
  private Long labRoomId;

  private Double latitude;
  private Double longitude;

  // Teacher assignment fields
  private Long teacherId;
  private String assignmentStatus = "active";

  // Constructors
  public CourseManagementBean() {
  }

  // Getters and Setters
  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public Long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(Long subjectId) {
    this.subjectId = subjectId;
  }

  public String getSemester() {
    return semester;
  }

  public void setSemester(String semester) {
    this.semester = semester;
  }

  public Date getStartSemester() {
    return startSemester;
  }

  public void setStartSemester(Date startSemester) {
    this.startSemester = startSemester;
  }

  public Long getSectionId() {
    return sectionId;
  }

  public void setSectionId(Long sectionId) {
    this.sectionId = sectionId;
  }

  public String getGroupNumber() {
    return groupNumber;
  }

  public void setGroupNumber(String groupNumber) {
    this.groupNumber = groupNumber;
  }

  public Integer getNumberOfSeat() {
    return numberOfSeat;
  }

  public void setNumberOfSeat(Integer numberOfSeat) {
    this.numberOfSeat = numberOfSeat;
  }

  public EDayOfWeek getLecDay() {
    return lecDay;
  }

  public void setLecDay(EDayOfWeek lecDay) {
    this.lecDay = lecDay;
  }

  public EDayOfWeek getLabDay() {
    return labDay;
  }

  public void setLabDay(EDayOfWeek labDay) {
    this.labDay = labDay;
  }

  public String getStartLectureTime() {
    return startLectureTime;
  }

  public void setStartLectureTime(String startLectureTime) {
    this.startLectureTime = startLectureTime;
  }

  public String getEndLectureTime() {
    return endLectureTime;
  }

  public void setEndLectureTime(String endLectureTime) {
    this.endLectureTime = endLectureTime;
  }

  public String getStartLabTime() {
    return startLabTime;
  }

  public void setStartLabTime(String startLabTime) {
    this.startLabTime = startLabTime;
  }

  public String getEndLabTime() {
    return endLabTime;
  }

  public void setEndLabTime(String endLabTime) {
    this.endLabTime = endLabTime;
  }

  public Long getLecRoomId() {
    return lecRoomId;
  }

  public void setLecRoomId(Long lecRoomId) {
    this.lecRoomId = lecRoomId;
  }

  public Long getLabRoomId() {
    return labRoomId;
  }

  public void setLabRoomId(Long labRoomId) {
    this.labRoomId = labRoomId;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Long getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(Long teacherId) {
    this.teacherId = teacherId;
  }

  public String getAssignmentStatus() {
    return assignmentStatus;
  }

  public void setAssignmentStatus(String assignmentStatus) {
    this.assignmentStatus = assignmentStatus;
  }
}
