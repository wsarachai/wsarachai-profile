package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @Column(name="code")
    private String code;
    @Column(name="type")
    private String type;
    @Column(name="thai_name", nullable = false)
    private String thaiName;
    @Column(name="eng_name", nullable = false)
    private String engName;
    @Column(name="description", columnDefinition="TEXT")
    private String description;
    @Column(name="credit", nullable = false)
    private Double credit;
    @Column(name = "credit_detail")
    private String creditDetail;
    @Column(name="enabled", columnDefinition = "TINYINT(1)")
    private boolean enabled;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="curriculum_id")
    private Curriculum curriculum;
//    @OneToMany(mappedBy = "section", fetch = FetchType.EAGER)
//    private Set<Enrollment> studentCourses = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThaiName() {
        return thaiName;
    }

    public void setThaiName(String thaiName) {
        this.thaiName = thaiName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getCreditDetail() {
        return creditDetail;
    }

    public void setCreditDetail(String creditDetail) {
        this.creditDetail = creditDetail;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

//    public Set<Enrollment> getStudentCourses() {
//        return studentCourses;
//    }
//
//    public void setStudentCourses(Set<Enrollment> studentCourses) {
//        this.studentCourses = studentCourses;
//    }
}
