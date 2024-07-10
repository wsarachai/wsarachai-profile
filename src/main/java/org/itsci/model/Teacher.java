package org.itsci.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends Staff {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "teacher_courses",
            joinColumns = { @JoinColumn(name = "teacher_id")},
            inverseJoinColumns = { @JoinColumn(name = "teaching_class_id")})
    private Set<Course> courses = new HashSet<>();

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
