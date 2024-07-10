package org.itsci.controller;

import org.itsci.model.*;
import org.itsci.service.StudentAttenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/populate")
public class PopulateDataController {

    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    private StudentAttenService studentAttenService;

    @GetMapping("/student")
    public String populateStudent(Model model) {

        Set<Authority> authorities =studentAttenService.findAllAuthorities();
        if (authorities.size() <= 0) {
            Authority authority1 = new Authority();
            authority1.setAuthority("ROLE_ADMIN");
            authority1.setDescription("ผู้ดูแลระบบ");
            studentAttenService.saveAuthority(authority1);

            Authority authority2 = new Authority();
            authority2.setAuthority("ROLE_TEACHER");
            authority2.setDescription("อาจารย์");
            studentAttenService.saveAuthority(authority2);

            Authority authority3 = new Authority();
            authority3.setAuthority("ROLE_STAFF");
            authority3.setDescription("เจ้าหน้าที่");
            studentAttenService.saveAuthority(authority3);

            Authority authority4 = new Authority();
            authority4.setAuthority("ROLE_STUDENT");
            authority4.setDescription("นักเรียน");
            studentAttenService.saveAuthority(authority4);

            Authority authority5 = new Authority();
            authority5.setAuthority("ROLE_MEMBER");
            authority5.setDescription("สมาชิก");
            studentAttenService.saveAuthority(authority5);

            Authority authority6 = new Authority();
            authority6.setAuthority("ROLE_USER");
            authority6.setDescription("ผู้ใช้ทั่วไป");
            studentAttenService.saveAuthority(authority6);
        }

        List<Room> rooms = studentAttenService.findAllRooms();
        if (rooms.size() <= 0) {
            Room room1 = new Room();
            room1.setBuildingName("อาคารตึกจุฬาภรณ์");
            room1.setRoomId("3202-1");
            room1.setBuildingId("105");
            studentAttenService.saveRoom(room1);

            Room room2 = new Room();
            room2.setBuildingName("อาคารเสาวรัจ นิตยวรรธนะ");
            room2.setRoomId("Lab ไอที 2");
            room2.setBuildingId("105");
            studentAttenService.saveRoom(room2);

            Room room3 = new Room();
            room3.setBuildingName("อาคารเรียนรวม 80 ปี");
            room3.setRoomId("80-301");
            room3.setBuildingId("147");
            studentAttenService.saveRoom(room3);
        }

        List<Curriculum> curriculums = studentAttenService.findAllCurriculums();
        if (curriculums.size() <= 0) {
            Curriculum curriculum1 = new Curriculum();
            curriculum1.setYear(2560);
            studentAttenService.saveCurriculum(curriculum1);

            Curriculum curriculum2 = new Curriculum();
            curriculum2.setYear(2565);
            studentAttenService.saveCurriculum(curriculum2);
        }

        List<Subject> subjects = studentAttenService.findAllSubjects();
        if (subjects.size() <= 0) {
            Subject subject1 = new Subject();
            subject1.setCode("ทส493");
            subject1.setCredit(3.0);
            subject1.setCreditDetail("3 (3-0-6)");
            subject1.setDescription("หัวข้อใหม่ ๆ หรือหัวข้อที่กําลังเป็นที่สนใจเกี่ยวกับเทคโนโลยีสารสนเทศ หัวข้อจะเปลี่ยนไปในแต่ละภาคเรียน");
            subject1.setEnabled(true);
            subject1.setEngName("Selected Topic in Information Technology");
            subject1.setThaiName("การศึกษาหัวข้อสนใจด้านเทคโนโลยีสารสนเทศ");
            subject1.setType("แกน");
            subject1.setCurriculum(studentAttenService.getCurriculumById(1l));
            studentAttenService.saveSubject(subject1);

            Subject subject2 = new Subject();
            subject2.setCode("10306241");
            subject2.setCredit(3.0);
            subject2.setCreditDetail("3 (3-0-6)");
            subject2.setDescription("การศึกษาพื้นฐานของระบบบริหารจัดการเนื้อหาเว็บไซต์ การติดตั้งใช้งานระบบเว็บเซิร์ฟเวอร์และฐานข้อมูล การจัดการไฟล์ผ่าน FTP การใช้งานเครื่องมือต่าง ๆ ของ CMS การวางแผนและออกแบบจัดการเนื้อหาเว็บไซต์ การสร้างบทความ การจัดการและกำหนดระดับสิทธิ์ผู้ใช้งาน การใช้ปลั๊กอิน การพัฒนาเว็บไซต์อีคอมเมิร์ซ การติดตั้ง Template การบริหารจัดการเว็บไซต์ผ่านเครื่องแม่ข่าย");
            subject2.setEnabled(true);
            subject2.setEngName("Web Content Management System");
            subject2.setThaiName("ระบบบริหารจัดการเว็บไซต์");
            subject2.setType("แกน");
            subject2.setCurriculum(studentAttenService.getCurriculumById(2l));
            studentAttenService.saveSubject(subject2);

            Subject subject3 = new Subject();
            subject3.setCode("10306493");
            subject3.setCredit(3.0);
            subject3.setCreditDetail("3 (3-0-6)");
            subject3.setDescription("หัวข้อใหม่ ๆ หรือหัวข้อที่กําลังเป็นที่สนใจเกี่ยวกับเทคโนโลยีสารสนเทศ หัวข้อจะเปลี่ยนไปในแต่ละภาคเรียน");
            subject3.setEnabled(true);
            subject3.setEngName("Selected Topic in Information Technology");
            subject3.setThaiName("การศึกษาหัวข้อสนใจด้านเทคโนโลยีสารสนเทศ");
            subject3.setType("แกน");
            subject3.setCurriculum(studentAttenService.getCurriculumById(2l));
            studentAttenService.saveSubject(subject3);
        }

        Teacher teacher1 = null;
        List<User> users = studentAttenService.findAllUsers();
        if (users.size() <= 0) {
            Login login1 = new Login();
            login1.setUsername("keng");
            login1.setPassword("{bcrypt}$2a$10$/GUlfBF1jG6Z7h2IiF6UGOCniw.HQeza8pWpW/x2eGWm6LL/rAlLO");
            Authority authority1 = studentAttenService.findAuthority(EAuthorityType.ROLE_ADMIN);
            Authority authority2 = studentAttenService.findAuthority(EAuthorityType.ROLE_TEACHER);
            login1.getAuthorities().add(authority1);
            login1.getAuthorities().add(authority2);
            teacher1 = new Teacher();
            teacher1.setFirstName("วัชรินทร");
            teacher1.setLastName("สาระไชย");
            teacher1.setPrename("อาจารย์ ดร.");
            teacher1.setLogin(login1);
            studentAttenService.saveUser(teacher1);
        }

        List<Course> courses = studentAttenService.findAllCourse();
        if (courses.size() <= 0) {
            Course course1 = new Course();
            course1.setSemester("1/2567");
            course1.setSubject(studentAttenService.getSubjectById(1l));
            Set<Teacher> teachers = new HashSet<>();
            teachers.add(teacher1);
            course1.setTeachers(teachers);
            studentAttenService.saveCourse(course1);

            Course course2 = new Course();
            course2.setSemester("1/2567");
            course2.setSubject(studentAttenService.getSubjectById(2l));
            course2.setTeachers(teachers);
            studentAttenService.saveCourse(course2);

            Course course3 = new Course();
            course3.setSemester("1/2567");
            course3.setSubject(studentAttenService.getSubjectById(3l));
            course3.setTeachers(teachers);
            studentAttenService.saveCourse(course3);

            teacher1.setCourses(new HashSet<>(Arrays.asList(course1, course2, course3)));
            studentAttenService.saveUser(teacher1);
        }

//        '11:30', '11:30', '1', 'Wednesday', 'Wednesday', '42', '8:30', '8:30', '3', '1', '2'
//        '11:30', '11:30', '1', 'Wednesday', 'Wednesday', '50', '8:30', '8:30', '3', '1', '3'
//        '12:00', '14:00', '1', 'Monday', 'Wednesday', '61', '09:00', '12:00', '2', '1', '1'
//        '16:00', '14:00', '2', 'Thursday', 'Wednesday', '60', '13:00', '12:00', '2', '1', '1'
        List<Course> teachingClasses = studentAttenService.findAllTeachingCourseByTeacher(teacher1);
        if (teachingClasses.size() <= 0) {
            Section section1 = new Section();
            section1.setCourse(studentAttenService.getCourseById(1l));
            section1.setGroupNumber("1");
            section1.setLecDay(EDayOfWeek.WEDNESDAY);
            section1.setStartLectureTime("8:30");
            section1.setEndLectureTime("11:30");
            section1.setLabDay(EDayOfWeek.WEDNESDAY);
            section1.setStartLabTime("8:30");
            section1.setEndLabTime("11:30");
            section1.setLabRoom(studentAttenService.getRoomById(2l));
            section1.setLecRoom(studentAttenService.getRoomById(2l));
            section1.setNumberOfSeat(42);
            studentAttenService.saveSection(section1);

            Section section2 = new Section();
            section2.setCourse(studentAttenService.getCourseById(3l));
            section2.setGroupNumber("1");
            section2.setLecDay(EDayOfWeek.WEDNESDAY);
            section2.setStartLectureTime("8:30");
            section2.setEndLectureTime("11:30");
            section2.setLabDay(EDayOfWeek.WEDNESDAY);
            section2.setStartLabTime("8:30");
            section2.setEndLabTime("11:30");
            section2.setLabRoom(studentAttenService.getRoomById(2l));
            section2.setLecRoom(studentAttenService.getRoomById(2l));
            section2.setNumberOfSeat(50);
            studentAttenService.saveSection(section2);

            Section section3 = new Section();
            section3.setCourse(studentAttenService.getCourseById(2l));
            section3.setGroupNumber("1");
            section3.setLecDay(EDayOfWeek.WEDNESDAY);
            section3.setStartLectureTime("12:00");
            section3.setEndLectureTime("14:00");
            section3.setLabDay(EDayOfWeek.MONDAY);
            section3.setStartLabTime("09:00");
            section3.setEndLabTime("12:00");
            section3.setLabRoom(studentAttenService.getRoomById(3l));
            section3.setLecRoom(studentAttenService.getRoomById(1l));
            section3.setNumberOfSeat(61);
            studentAttenService.saveSection(section3);

            Section section4 = new Section();
            section4.setCourse(studentAttenService.getCourseById(2l));
            section4.setGroupNumber("2");
            section4.setLecDay(EDayOfWeek.WEDNESDAY);
            section4.setStartLectureTime("12:00");
            section4.setEndLectureTime("14:00");
            section4.setLabDay(EDayOfWeek.THURSDAY);
            section4.setStartLabTime("13:00");
            section4.setEndLabTime("16:00");
            section4.setLabRoom(studentAttenService.getRoomById(3l));
            section4.setLabRoom(studentAttenService.getRoomById(1l));
            section4.setNumberOfSeat(51);
            studentAttenService.saveSection(section4);
        }

//        List<TeachingClass> teachingClasses = studentAttenService.findAllTeachingClass();
//
//        for (long i = 1; i <= 4; i++) {
//            List<StudentSectionRegistration> courseSectionRegistrations = studentAttenService.findStudentByCourseSectionId(i);
//            for (StudentSectionRegistration courseSectionRegistration : courseSectionRegistrations) {
//                if (courseSectionRegistration.getLecAtten().size() <=0) {
//                    for (int j = 1; j <= 15; j++) {
//                        Attendance attendance = new Attendance();
//                        attendance.setWeekNo(j);
//                        attendance.setCourseSectionRegistration(courseSectionRegistration);
//                        attendance.setStatus(EAttendanceStatus.NA);
//                        courseSectionRegistration.getLecAtten().add(attendance);
//                    }
//                }
//                if (courseSectionRegistration.getLabAtten().size() <=0) {
//                    for (int j = 1; j <= 15; j++) {
//                        Attendance attendance = new Attendance();
//                        attendance.setWeekNo(j);
//                        attendance.setCourseSectionRegistration(courseSectionRegistration);
//                        attendance.setStatus(EAttendanceStatus.NA);
//                        courseSectionRegistration.getLabAtten().add(attendance);
//                    }
//                }
//                studentAttenService.saveCourseSectionRegistration(courseSectionRegistration);
//            }
//        }
        return "index";
    }
}
