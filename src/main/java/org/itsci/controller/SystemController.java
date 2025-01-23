package org.itsci.controller;

import org.itsci.model.*;
import org.itsci.service.CourseService;
import org.itsci.service.StudentAttenService;
import org.itsci.service.SystemService;
import org.itsci.service.UserService;
import org.itsci.utils.CSVHelper;
import org.itsci.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/system")
public class SystemController {
    private static final String COMMA_DELIMITER = ",";

    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    private StudentAttenService studentAttenService;

    @Autowired
    SystemService systemService;

    @Autowired
    UserService<Teacher> userService;

    @Autowired
    CourseService courseService;

    @GetMapping("/subject")
    public String subjectForm(Model model)
    {
        List<Subject> subjects = systemService.findAllSubject();
        List<Curriculum> curriculums = systemService.findAllCurriculum();

        model.addAttribute("subjects", subjects);
        model.addAttribute("curriculums", curriculums);

        return "system/subject-form";
    }

    @GetMapping("/student/atten/export/cvs/{courseId}/{sectionId}")
    public HttpEntity<byte[]> exportCVSAttendance(@PathVariable long courseId, @PathVariable long sectionId) throws IOException {
        String filename = "students.csv";
        List<Enrollment> enrollments = studentAttenService.findEnrollmentBySectionId(sectionId);

        List<AttenData> attendanceList = createAttenData(enrollments);
        List<String[]> dataLines = new ArrayList<>();

        for(AttenData attenData : attendanceList) {
            List<String> dataLineList = new ArrayList<>();

            dataLineList.add(attenData.getEnrollment().getStudent().getStudentId());
            for(Attendance atten : attenData.getAttenLec()) {
                dataLineList.add(atten.getStatus().name());
            }
            for(Attendance atten : attenData.getAttenLab()) {
                dataLineList.add(atten.getStatus().name());
            }

            String [] dataArr = new String[dataLineList.size()];
            for (int i=0; i<dataLineList.size(); i++) {
                dataArr[i] = dataLineList.get(i);
            }

            dataLines.add(dataArr);
        }

        byte[] documentBody = null;

        try (OutputStream csvOutputFile = new ByteArrayOutputStream()) {
            CSVHelper.convertToCSVBytes(csvOutputFile, dataLines);
            documentBody = csvOutputFile.toString().getBytes();
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType("application/csv"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        header.setContentLength(documentBody.length);

        return new HttpEntity<>(documentBody, header);
    }

    @GetMapping("/student/atten/export/{courseId}/{sectionId}")
    public String exportAttendance(Model model, @PathVariable long courseId, @PathVariable long sectionId) {
        Course course = studentAttenService.findCourseById(courseId);
        List<Enrollment> enrollments = studentAttenService.findEnrollmentBySectionId(sectionId);

        Section section = null;
        for (Section sec : course.getSections()) {
            if (sec.getId() == sectionId) {
                section = sec;
                break;
            }
        }

        assert section != null;

        List<AttenData> attendanceList = createAttenData(enrollments);
        model.addAttribute("course", course);
        model.addAttribute("section", section);
        model.addAttribute("attenDatas", attendanceList);

        return "system/export-student";
    }

    private List<AttenData> createAttenData(List<Enrollment> enrollments) {
        List<AttenData> attenDataList = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            AttenData attenData = new AttenData(enrollment);
            SortedSet<Attendance> lecAttendances = studentAttenService.findAttendancesByType(enrollment, "lec");
            SortedSet<Attendance> labAttendances = studentAttenService.findAttendancesByType(enrollment, "lab");
            for (int i=0; i<AttenData.numberOfWeek; i++) {
                try {
                    boolean found = false;
                    for (Attendance att : lecAttendances) {
                        if (att.getWeekNo() == i) {
                            attenData.getAttenLec()[i] = att;
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        attenData.getAttenLec()[i].setStatus(EAttendanceStatus.ABSENT);
                    }
                } catch (Exception ex) {
                    attenData.getAttenLec()[i].setStatus(EAttendanceStatus.ABSENT);
                }

                try {
                    boolean found = false;
                    for (Attendance att : labAttendances) {
                        if (att.getWeekNo() == i) {
                            attenData.getAttenLab()[i] = att;
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        attenData.getAttenLab()[i].setStatus(EAttendanceStatus.ABSENT);
                    }
                } catch (Exception ex) {
                    attenData.getAttenLab()[i].setStatus(EAttendanceStatus.ABSENT);
                }
            }
            attenDataList.add(attenData);
        }
        return attenDataList;
    }

    @GetMapping("/student/list/{term}")
    public String studentMgrPage(Model model, @PathVariable String term) {
        List<Student> students = systemService.findStudent(term);
        model.addAttribute("students", students);
        return "system/student-mgr";
    }

    @GetMapping("/student/import")
    public String studentImportPage(Model model) {

        model.addAttribute("message", null);
        return "system/import-student";
    }

    @GetMapping("/clear/cache")
    @CacheEvict(value = { "enrollments", "courses", "members", "attendances", "config" }, allEntries = true)
    public String clearCache() {
        return "redirect:/";
    }


    @PostMapping(path="/student/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String uploadCSVFile(Model model, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String message = "";
        int numCol = 0;
        HttpSession session = request.getSession(false);

        String year = DateUtils.getCurrentSemesterYear();
        String term = DateUtils.getCurrentSemesterTerm(new Date());

        Teacher teacher = userService.getUser(1L, Teacher.class);

        assert teacher != null;
        String semester = term + "/" + year;

        ArrayList<TeacherCourse> teacherCourses = new ArrayList<>(courseService.listCourseByTeacherAndSemester(teacher, semester));
        Collections.sort(teacherCourses);

        List<Section> sections = new ArrayList<>();

        for (TeacherCourse tc : teacherCourses) {
            sections.addAll(tc.getCourse().getSections());
        }

        try {
            List<List<String>> records = new ArrayList<>();
            Scanner scanner = new Scanner(file.getInputStream());
            while (scanner.hasNextLine()) {
                records.add(this.getRecordFromLine(scanner.nextLine()));
            }
            if (records.size() > 0) {
                numCol = records.get(0).size();
            }

            List<String> fields = new ArrayList<>();
            for (int i=1; i<=numCol; i++) {
                fields.add("Field"+i);
            }

            session.setAttribute("records", records);

            model.addAttribute("fields", fields);
            model.addAttribute("records", records);
            model.addAttribute("sections", sections);
        } catch (Exception e) {
            message = "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
            return "system/import-student";
        }
        return "system/upload_form";
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    @PostMapping("/student/enroll")
    @CacheEvict(value = { "enrollments", "members" }, allEntries = true)
    public String enrollStudent(Model model,
                                @RequestParam("no") String no,
                                @RequestParam("code") String code,
                                @RequestParam("prename") String prename,
                                @RequestParam("firstname") String firstname,
                                @RequestParam("lastname") String lastname,
                                @RequestParam("section_id") String sectionId,
                                HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        List<List<String>> records = (List<List<String>>) session.getAttribute("records");

        int iNo = Integer.parseInt(no);
        int iCode = Integer.parseInt(code);
        int iPrename = Integer.parseInt(prename);
        int iFirstname = Integer.parseInt(firstname);
        int iLastname = Integer.parseInt(lastname);
        int iSectionId = Integer.parseInt(sectionId);

        List<Student> students = new ArrayList<>();
        for (List<String> lst : records) {
            String studentId = lst.get(iCode).replaceAll("[^0-9]","");
            Student stu = systemService.findStudentByStudentId(studentId);
            if (stu == null) {
                stu = new Student();
            }
            String val = lst.get(iNo).replaceAll("[^0-9]","");
            stu.setStudentNo(Integer.parseInt(val));
            stu.setStudentId(studentId);
            stu.setPrename(lst.get(iPrename));
            stu.setFirstName(lst.get(iFirstname));
            stu.setLastName(lst.get(iLastname));
            stu.setStartFromYear(Integer.parseInt(stu.getStudentId().substring(0, 2)));

            Login login = stu.getLogin();
            if (login == null) {
                login =new Login();
                login.getAuthorities().add(systemService.getAuthority(EAuthorityType.ROLE_STUDENT));
                login.setEnabled(true);
                login.setUsername(stu.getStudentId());
                String encrypted = bCryptPasswordEncoder.encode(stu.getStudentId());
                login.setPassword("{bcrypt}" + encrypted);
                stu.setLogin(login);
            }

            students.add(stu);
        }

        Section section = systemService.findSectionById(iSectionId);

        for (Student stu : students) {
            if (!systemService.isStudentEnrollment(stu, section)) {
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(stu);
                enrollment.setSection(section);
                systemService.saveOrUpdateStudent(stu);
                systemService.saveEnrollment(enrollment);
            }
        }

        return "redirect:/system/student/import";
    }
}
