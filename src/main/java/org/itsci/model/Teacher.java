package org.itsci.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends Staff {
    @OneToMany
    private Set<CourseSection> courseSectionSet = new HashSet<>();

    public Set<CourseSection> getCourseSectionSet() {
        return courseSectionSet;
    }

    public void setCourseSectionSet(Set<CourseSection> courseSectionSet) {
        this.courseSectionSet = courseSectionSet;
    }
}
