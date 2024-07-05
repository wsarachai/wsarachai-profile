package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "curriculums")
public class Curriculum {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @Column(name="year")
    private int year;
    @Column(name="thai_name", nullable = false)
    private String thaiName;
    @Column(name="eng_name", nullable = false)
    private String engName;
    @Column(name="type")
    private String type;
    @Column(name="thai_degree_name")
    private String thaiDegreeName;
    @Column(name="eng_degree_name")
    private String engDegreeName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThaiDegreeName() {
        return thaiDegreeName;
    }

    public void setThaiDegreeName(String thaiDegreeName) {
        this.thaiDegreeName = thaiDegreeName;
    }

    public String getEngDegreeName() {
        return engDegreeName;
    }

    public void setEngDegreeName(String engDegreeName) {
        this.engDegreeName = engDegreeName;
    }
}
