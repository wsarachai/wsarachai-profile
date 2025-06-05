package org.itsci.controller;

// import org.itsci.model.*;
// import org.itsci.service.StudentAttenService;
// import org.itsci.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// import java.util.*;

@Controller
@RequestMapping("/system")
public class PopulateDataController {

    @Autowired
    ResourceBundleMessageSource messageSource;

    // @Autowired
    // private StudentAttenService studentAttenService;
    //
    // @GetMapping("/fiximage")
    // public String fixImage(Model model) {
    // List<Attendance> attendances = studentAttenService.findAllAttendances();
    // for (Attendance attendance : attendances) {
    // if (attendance.getStudentImage() != null) {
    // Image image = new Image();
    // image.setImage(attendance.getStudentImage());
    // studentAttenService.saveImage(image);
    // attendance.setImage1_id(image.getId());
    // }
    // if (attendance.getCodeImage() != null) {
    // Image image = new Image();
    // image.setImage(attendance.getCodeImage());
    // studentAttenService.saveImage(image);
    // attendance.setImage2_id(image.getId());
    // }
    // studentAttenService.updateAttendance(attendance);
    // }
    // return "index";
    // }

    // private void populateData1_67() {

    // Set<Authority> authorities =studentAttenService.findAllAuthorities();
    // if (authorities.size() <= 0) {
    // Authority authority1 = new Authority();
    // authority1.setRoleName(EAuthorityType.ROLE_ADMIN);
    // authority1.setDescription("ผู้ดูแลระบบ");
    // studentAttenService.saveAuthority(authority1);

    // Authority authority2 = new Authority();
    // authority2.setRoleName(EAuthorityType.ROLE_TEACHER);
    // authority2.setDescription("อาจารย์");
    // studentAttenService.saveAuthority(authority2);

    // Authority authority3 = new Authority();
    // authority3.setRoleName(EAuthorityType.ROLE_STAFF);
    // authority3.setDescription("เจ้าหน้าที่");
    // studentAttenService.saveAuthority(authority3);

    // Authority authority4 = new Authority();
    // authority4.setRoleName(EAuthorityType.ROLE_STUDENT);
    // authority4.setDescription("นักเรียน");
    // studentAttenService.saveAuthority(authority4);

    // Authority authority5 = new Authority();
    // authority5.setRoleName(EAuthorityType.ROLE_MEMBER);
    // authority5.setDescription("สมาชิก");
    // studentAttenService.saveAuthority(authority5);

    // Authority authority6 = new Authority();
    // authority6.setRoleName(EAuthorityType.ROLE_USER);
    // authority6.setDescription("ผู้ใช้ทั่วไป");
    // studentAttenService.saveAuthority(authority6);
    // }

    // List<Room> rooms = studentAttenService.findAllRooms();
    // if (rooms.size() <= 0) {
    // Room room1 = new Room();
    // room1.setBuildingName("อาคารตึกจุฬาภรณ์");
    // room1.setRoomId("3202-1");
    // room1.setBuildingId("105");
    // studentAttenService.saveRoom(room1);

    // Room room2 = new Room();
    // room2.setBuildingName("อาคารเสาวรัจ นิตยวรรธนะ");
    // room2.setRoomId("Lab ไอที 2");
    // room2.setBuildingId("105");
    // studentAttenService.saveRoom(room2);

    // Room room3 = new Room();
    // room3.setBuildingName("อาคารเรียนรวม 80 ปี");
    // room3.setRoomId("80-301");
    // room3.setBuildingId("147");
    // studentAttenService.saveRoom(room3);
    // }

    // List<Curriculum> curriculums = studentAttenService.findAllCurriculums();
    // if (curriculums.size() <= 0) {
    // Curriculum curriculum1 = new Curriculum();
    // curriculum1.setYear(2560);
    // studentAttenService.saveCurriculum(curriculum1);

    // Curriculum curriculum2 = new Curriculum();
    // curriculum2.setYear(2565);
    // studentAttenService.saveCurriculum(curriculum2);
    // }

    // List<Subject> subjects = studentAttenService.findAllSubjects();
    // if (subjects.size() <= 0) {
    // Subject subject1 = new Subject();
    // subject1.setCode("ทส493");
    // subject1.setCredit(3.0);
    // subject1.setCreditDetail("3 (3-0-6)");
    // subject1.setDescription("หัวข้อใหม่ ๆ
    // หรือหัวข้อที่กําลังเป็นที่สนใจเกี่ยวกับเทคโนโลยีสารสนเทศ
    // หัวข้อจะเปลี่ยนไปในแต่ละภาคเรียน");
    // subject1.setEnabled(true);
    // subject1.setEngName("Selected Topic in Information Technology");
    // subject1.setThaiName("การศึกษาหัวข้อสนใจด้านเทคโนโลยีสารสนเทศ");
    // subject1.setType("แกน");
    // subject1.setCurriculum(studentAttenService.findCurriculumById(1l));
    // studentAttenService.saveSubject(subject1);

    // Subject subject2 = new Subject();
    // subject2.setCode("10306241");
    // subject2.setCredit(3.0);
    // subject2.setCreditDetail("3 (3-0-6)");
    // subject2.setDescription("การศึกษาพื้นฐานของระบบบริหารจัดการเนื้อหาเว็บไซต์
    // การติดตั้งใช้งานระบบเว็บเซิร์ฟเวอร์และฐานข้อมูล การจัดการไฟล์ผ่าน FTP
    // การใช้งานเครื่องมือต่าง ๆ ของ CMS การวางแผนและออกแบบจัดการเนื้อหาเว็บไซต์
    // การสร้างบทความ การจัดการและกำหนดระดับสิทธิ์ผู้ใช้งาน การใช้ปลั๊กอิน
    // การพัฒนาเว็บไซต์อีคอมเมิร์ซ การติดตั้ง Template
    // การบริหารจัดการเว็บไซต์ผ่านเครื่องแม่ข่าย");
    // subject2.setEnabled(true);
    // subject2.setEngName("Web Content Management System");
    // subject2.setThaiName("ระบบบริหารจัดการเว็บไซต์");
    // subject2.setType("แกน");
    // subject2.setCurriculum(studentAttenService.findCurriculumById(2l));
    // studentAttenService.saveSubject(subject2);

    // Subject subject3 = new Subject();
    // subject3.setCode("10306493");
    // subject3.setCredit(3.0);
    // subject3.setCreditDetail("3 (3-0-6)");
    // subject3.setDescription("หัวข้อใหม่ ๆ
    // หรือหัวข้อที่กําลังเป็นที่สนใจเกี่ยวกับเทคโนโลยีสารสนเทศ
    // หัวข้อจะเปลี่ยนไปในแต่ละภาคเรียน");
    // subject3.setEnabled(true);
    // subject3.setEngName("Selected Topic in Information Technology");
    // subject3.setThaiName("การศึกษาหัวข้อสนใจด้านเทคโนโลยีสารสนเทศ");
    // subject3.setType("แกน");
    // subject3.setCurriculum(studentAttenService.findCurriculumById(2l));
    // studentAttenService.saveSubject(subject3);
    // }

    // Teacher teacher1 = null;
    // List<User> users = studentAttenService.findAllUsers();
    // if (users.size() <= 0) {
    // Login login1 = new Login();
    // login1.setUsername("keng");
    // login1.setPassword("{bcrypt}$2a$10$qH/4NYQsFpKcOyOUce6VLuG8fC045j7z498JF81yS3x4NBCguonEi");
    // Authority authority1 =
    // studentAttenService.findByRoleName(EAuthorityType.ROLE_ADMIN);
    // Authority authority2 =
    // studentAttenService.findByRoleName(EAuthorityType.ROLE_TEACHER);
    // login1.getAuthorities().add(authority1);
    // login1.getAuthorities().add(authority2);
    // teacher1 = new Teacher();
    // teacher1.setFirstName("วัชรินทร");
    // teacher1.setLastName("สาระไชย");
    // teacher1.setPrename("อาจารย์ ดร.");
    // teacher1.setLogin(login1);
    // studentAttenService.saveUser(teacher1);
    // }

    // if (teacher1 == null) {
    // teacher1 = studentAttenService.findTeacherById(1l);
    // }

    // List<Course> courses = studentAttenService.findAllCourse();
    // if (courses.size() <= 0) {
    // Course course1 = new Course();
    // course1.setSemester("1/2567");
    // course1.setStartSemester(DateUtils.getDateFrom(2567, 6, 3));
    // course1.setSubject(studentAttenService.findSubjectById(1l));

    // Section section1 = new Section();
    // section1.setGroupNumber("01");
    // section1.setLecDay(EDayOfWeek.WEDNESDAY);
    // section1.setStartLectureTime("8:30");
    // section1.setEndLectureTime("11:30");
    // section1.setLabDay(EDayOfWeek.WEDNESDAY);
    // section1.setStartLabTime("8:30");
    // section1.setEndLabTime("11:30");
    // section1.setLabRoom(studentAttenService.findRoomById(2l));
    // section1.setLecRoom(studentAttenService.findRoomById(2l));
    // section1.setNumberOfSeat(42);
    // section1.setCourse(course1);
    // course1.getSections().add(section1);

    // studentAttenService.saveCourse(course1);

    // Course course2 = new Course();
    // course2.setSemester("1/2567");
    // course2.setStartSemester(DateUtils.getDateFrom(2567, 6, 3));
    // course2.setSubject(studentAttenService.findSubjectById(2l));

    // Section section3 = new Section();
    // section3.setGroupNumber("01");
    // section3.setLecDay(EDayOfWeek.WEDNESDAY);
    // section3.setStartLectureTime("12:00");
    // section3.setEndLectureTime("14:00");
    // section3.setLabDay(EDayOfWeek.MONDAY);
    // section3.setStartLabTime("09:00");
    // section3.setEndLabTime("12:00");
    // section3.setLabRoom(studentAttenService.findRoomById(2l));
    // section3.setLecRoom(studentAttenService.findRoomById(1l));
    // section3.setNumberOfSeat(61);

    // Section section4 = new Section();
    // section4.setGroupNumber("02");
    // section4.setLecDay(EDayOfWeek.WEDNESDAY);
    // section4.setStartLectureTime("12:00");
    // section4.setEndLectureTime("14:00");
    // section4.setLabDay(EDayOfWeek.THURSDAY);
    // section4.setStartLabTime("13:00");
    // section4.setEndLabTime("16:00");
    // section4.setLabRoom(studentAttenService.findRoomById(2l));
    // section4.setLecRoom(studentAttenService.findRoomById(1l));
    // section4.setNumberOfSeat(51);

    // section3.setCourse(course2);
    // section4.setCourse(course2);
    // course2.getSections().add(section3);
    // course2.getSections().add(section4);

    // studentAttenService.saveCourse(course2);

    // Course course3 = new Course();
    // course3.setSemester("1/2567");
    // course3.setStartSemester(DateUtils.getDateFrom(2567, 6, 3));
    // course3.setSubject(studentAttenService.findSubjectById(3l));

    // Section section2 = new Section();
    // section2.setGroupNumber("01");
    // section2.setLecDay(EDayOfWeek.WEDNESDAY);
    // section2.setStartLectureTime("8:30");
    // section2.setEndLectureTime("11:30");
    // section2.setLabDay(EDayOfWeek.WEDNESDAY);
    // section2.setStartLabTime("8:30");
    // section2.setEndLabTime("11:30");
    // section2.setLabRoom(studentAttenService.findRoomById(2l));
    // section2.setLecRoom(studentAttenService.findRoomById(2l));
    // section2.setNumberOfSeat(50);

    // section2.setCourse(course3);
    // course3.getSections().add(section2);

    // studentAttenService.saveCourse(course3);

    // // course1.getTeachers().add(teacher1);
    // // course2.getTeachers().add(teacher1);
    // // course3.getTeachers().add(teacher1);
    // // teacher1.getCourses().add(course1);
    // // teacher1.getCourses().add(course2);
    // // teacher1.getCourses().add(course3);
    // // studentAttenService.updateUser(teacher1);
    // }
    // }

    // private void populateData2_67() {

    // Subject subject = studentAttenService.findBySubjectCode("10306103");
    // if (subject == null) {
    // subject = new Subject();
    // subject.setCode("10306103");
    // subject.setCredit(3.0);
    // subject.setCreditDetail("3 (2-3-5)");
    // subject.setDescription("ปัญหาและกระบวนการแก้ปัญหาภาษาคอมพิวเตอร์
    // กระบวนการพัฒนาโปรแกรม ภาษาการโปรแกรม ลักษณะของโปรแกรมแบบโครงสร้าง
    // โครงสร้างข้อมูลที่จำเป็นและการนำไป ใช้งาน การออกแบบเป็นโมดูล
    // เทคนิคการแก้ปัญหา การเรียงลำดับและการค้นหา การทดสอบโปรแกรมในระดับหน่วย
    // การจัดทำเอกสารประกอบโปรแกรม");
    // subject.setEnabled(true);
    // subject.setEngName("Software Development Process");
    // subject.setThaiName("กระบวนการพัฒนาซอฟต์แวร์");
    // subject.setType("แกน");
    // subject.setCurriculum(studentAttenService.findCurriculumById(2l));
    // studentAttenService.saveSubject(subject);
    // }

    // subject = studentAttenService.findBySubjectCode("10306214");
    // if (subject == null) {
    // subject = new Subject();
    // subject.setCode("10306214");
    // subject.setCredit(3.0);
    // subject.setCreditDetail("3 (2-3-5)");
    // subject.setDescription("ทบทวนแนวคิดเชิงวัตถุและความสัมพันธ์ระหว่างคลาส
    // การจัดการไฟล์และ Exception การค้นหาคลาส Coupling และ Cohesion การสืบทอดและ
    // Composition
    // หลักการออกแบบคลาสเพื่อรองรับการแก้ไขปัญหาในระดับที่สามารถนำไปประยุกต์กับการแก้ไขปัญหาเพื่อการโปรแกรม
    // ตลอดจนความรู้เบื้องต้นเกี่ยวกับการออกแบบรูปแบบมาตรฐานเชิงวัตถุ (Design
    // Pattern) รวมถึงการทดสอบระดับหน่วย");
    // subject.setEnabled(true);
    // subject.setEngName("Object Oriented Programming");
    // subject.setThaiName("การโปรแกรมเชิงวัตถุ");
    // subject.setType("แกน");
    // subject.setCurriculum(studentAttenService.findCurriculumById(2l));
    // studentAttenService.saveSubject(subject);
    // }

    // subject = studentAttenService.findBySubjectCode("10300402");
    // if (subject == null) {
    // subject = new Subject();
    // subject.setCode("10300402");
    // subject.setCredit(3.0);
    // subject.setCreditDetail("3 (2-2-5)");
    // subject.setDescription("ศึกษาเกี่ยวกับแนวคิดเกี่ยวกับความเข้าใจและการใช้ดิจิทัล
    // สิทธิและความรับผิดชอบ การใช้ชีวิตในสังคมดิจิทัล ความสำคัญของข้อมูลสารสนเทศ
    // การเข้าถึงแหล่งข้อมูล การพัฒนาทักษะในการสืบค้นและอ้างอิงข้อมูล
    // การใช้และจัดการสารสนเทศได้อย่างถูกต้องและมีประสิทธิภาพ และมีวิจารณญาณ
    // ตระหนักในจรรยาบรรณและผลกระทบที่มีต่อบุคคลและสังคม
    // รวมทั้งกฎหมายที่เกี่ยวข้อง");
    // subject.setEnabled(true);
    // subject.setEngName("Living in Digital Society");
    // subject.setThaiName("การใช้ชีวิตในสังคมดิจิทัล");
    // subject.setType("ศึกษาทั่วไป");
    // subject.setCurriculum(studentAttenService.findCurriculumById(2l));
    // studentAttenService.saveSubject(subject);
    // }

    // Teacher teacher = studentAttenService.findTeacherById(1L);

    // // subject = studentAttenService.findBySubjectCode("10300402");
    // // Course course = studentAttenService.findCourseBySubject(subject);
    // // if (course == null) {
    // // course = new Course();
    // // course.setSemester("2/2567");
    // // course.setStartSemester(DateUtils.getDateFrom(2567, 10, 18));
    // // course.setSubject(subject);
    // //
    // // SortedSet<Section> sectionList = course.getSections();
    // // if (sectionList == null || sectionList.isEmpty()) {
    // // Section section = new Section();
    // // section.setGroupNumber("03");
    // // section.setLecDay(EDayOfWeek.MONDAY);
    // // section.setStartLectureTime("15:00");
    // // section.setEndLectureTime("17:00");
    // // section.setLabDay(EDayOfWeek.THURSDAY);
    // // section.setStartLabTime("15:00");
    // // section.setEndLabTime("17:00");
    // // section.setLecRoom(studentAttenService.findRoomById(6l));
    // // section.setLabRoom(studentAttenService.findRoomById(6l));
    // // section.setNumberOfSeat(200);
    // // section.setCourse(course);
    // // course.getSections().add(section);
    // //
    // // studentAttenService.saveCourse(course);
    // //
    // // TeacherCourse teacherCourse = new TeacherCourse();
    // // teacherCourse.setTeacher(teacher);
    // // teacherCourse.setCourse(course);
    // // teacherCourse.setStatus("true");
    // // studentAttenService.saveTeacherCourse(teacherCourse);
    // //
    // // }
    // // }

    // subject = studentAttenService.findBySubjectCode("10306103");
    // Course course = studentAttenService.findCourseBySubject(subject);
    // if (course == null) {
    // course = new Course();
    // course.setSemester("2/2567");
    // course.setStartSemester(DateUtils.getDateFrom(2567, 10, 19));
    // course.setSubject(subject);

    // SortedSet<Section> sectionList = course.getSections();
    // if (sectionList == null || sectionList.isEmpty()) {
    // Section section = new Section();
    // section.setGroupNumber("01");
    // section.setLecDay(EDayOfWeek.TUESDAY);
    // section.setStartLectureTime("15:00");
    // section.setEndLectureTime("17:00");
    // section.setLabDay(EDayOfWeek.THURSDAY);
    // section.setStartLabTime("12:00");
    // section.setEndLabTime("15:00");
    // section.setLecRoom(studentAttenService.findRoomById(1l));
    // section.setLabRoom(studentAttenService.findRoomById(4l));
    // section.setNumberOfSeat(60);
    // section.setCourse(course);
    // course.getSections().add(section);

    // section = new Section();
    // section.setGroupNumber("02");
    // section.setLecDay(EDayOfWeek.THURSDAY);
    // section.setStartLectureTime("15:00");
    // section.setEndLectureTime("17:00");
    // section.setLabDay(EDayOfWeek.FRIDAY);
    // section.setStartLabTime("15:00");
    // section.setEndLabTime("18:00");
    // section.setLecRoom(studentAttenService.findRoomById(1l));
    // section.setLabRoom(studentAttenService.findRoomById(4l));
    // section.setNumberOfSeat(60);
    // section.setCourse(course);
    // course.getSections().add(section);

    // studentAttenService.saveCourse(course);

    // TeacherCourse teacherCourse = new TeacherCourse();
    // teacherCourse.setTeacher(teacher);
    // teacherCourse.setCourse(course);
    // teacherCourse.setStatus("true");
    // studentAttenService.saveTeacherCourse(teacherCourse);
    // }
    // }

    // subject = studentAttenService.findBySubjectCode("10306214");
    // course = studentAttenService.findCourseBySubject(subject);
    // if (course == null) {
    // course = new Course();
    // course.setSemester("2/2567");
    // course.setStartSemester(DateUtils.getDateFrom(2567, 10, 20));
    // course.setSubject(subject);

    // SortedSet<Section> sectionList = course.getSections();
    // if (sectionList == null || sectionList.isEmpty()) {
    // Section section = new Section();
    // section.setGroupNumber("01");
    // section.setLecDay(EDayOfWeek.WEDNESDAY);
    // section.setStartLectureTime("09:00");
    // section.setEndLectureTime("11:00");
    // section.setLabDay(EDayOfWeek.FRIDAY);
    // section.setStartLabTime("10:00");
    // section.setEndLabTime("13:00");
    // section.setLecRoom(studentAttenService.findRoomById(5l));
    // section.setLabRoom(studentAttenService.findRoomById(4l));
    // section.setNumberOfSeat(60);
    // section.setCourse(course);
    // course.getSections().add(section);

    // studentAttenService.saveCourse(course);

    // TeacherCourse teacherCourse = new TeacherCourse();
    // teacherCourse.setTeacher(teacher);
    // teacherCourse.setCourse(course);
    // teacherCourse.setStatus("true");
    // studentAttenService.saveTeacherCourse(teacherCourse);
    // }
    // }
    // }

    @GetMapping("/populate")
    public String populateStudent(Model model) {

        // populateData2_67();

        return "index";
    }
}
