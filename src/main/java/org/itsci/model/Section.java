package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sections")
public class Section implements Comparable<Section> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    @Column(name = "lec_day", columnDefinition = "VARCHAR(30)")
    @Convert(converter = EDayOfWeekAttributeConverter.class)
    private EDayOfWeek lecDay;
    @Column(name = "lab_day", columnDefinition = "VARCHAR(30)")
    @Convert(converter = EDayOfWeekAttributeConverter.class)
    private EDayOfWeek labDay;
    @Column(name = "group_number", length = 3)
    private String groupNumber;
    @Column(name = "number_of_seat", length = 3)
    private int numberOfSeat;
    @Column(name = "start_lec_time", length = 6)
    private String startLectureTime;
    @Column(name = "end_lec_time", length = 6)
    private String endLectureTime;
    @Column(name = "start_lab_time", length = 6)
    private String startLabTime;
    @Column(name = "end_lab_time", length = 6)
    private String endLabTime;
    @ManyToOne
    @JoinColumn(name = "lec_room_id")
    private Room lecRoom;
    @ManyToOne
    @JoinColumn(name = "lab_room_id")
    private Room labRoom;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;
    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
    private Set<Enrollment> enrollments = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
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

    public Room getLecRoom() {
        return lecRoom;
    }

    public void setLecRoom(Room lecRoom) {
        this.lecRoom = lecRoom;
    }

    public Room getLabRoom() {
        return labRoom;
    }

    public void setLabRoom(Room labRoom) {
        this.labRoom = labRoom;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public int compareTo(Section o) {
        if (o != null && o.getGroupNumber() != null && this.getGroupNumber() != null) {
            return this.getGroupNumber().compareTo(o.getGroupNumber());
        } else {
            return 0;
        }
    }
}
