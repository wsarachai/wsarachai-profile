package org.itsci.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends Staff {
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Course> courseSet = new HashSet<>();

    public Set<Course> getCourseSectionSet() {
        return courseSet;
    }

    public void setCourseSectionSet(Set<Course> courseSet) {
        this.courseSet = courseSet;
    }
}
