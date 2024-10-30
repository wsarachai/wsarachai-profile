package org.itsci.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends Staff {

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    private Set<TeacherCourse> teacherCourses = new HashSet<>();

    public Set<TeacherCourse> getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(Set<TeacherCourse> teacherCourses) {
        this.teacherCourses = teacherCourses;
    }
}
