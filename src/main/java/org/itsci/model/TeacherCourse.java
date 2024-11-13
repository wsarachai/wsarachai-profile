package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "teacher_courses")
public class TeacherCourse implements Comparable<TeacherCourse> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name="status")
    private String status;

    @ManyToOne
    @JoinColumn(name="teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public int compareTo(TeacherCourse o) {
        Subject subject = o.getCourse().getSubject();
        Subject ownSubject = this.getCourse().getSubject();
        return subject.getCode().compareTo(ownSubject.getCode());
    }
}
