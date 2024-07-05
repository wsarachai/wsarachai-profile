package org.itsci.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Student extends Member {
    @Column(name="student_id", length = 10)
    private String studentId;
    @Column(name="start_from_year")
    private int startFromYear;
    @OneToMany(mappedBy = "student")
    private Set<CourseSectionRegistration> courseRegistered = new HashSet<>();

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Set<CourseSectionRegistration> getCourseRegistered() {
        return courseRegistered;
    }

    public void setCourseRegistered(Set<CourseSectionRegistration> courseRegistered) {
        this.courseRegistered = courseRegistered;
    }

    public int getStartFromYear() {
        return startFromYear;
    }

    public void setStartFromYear(int startFromYear) {
        this.startFromYear = startFromYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}
