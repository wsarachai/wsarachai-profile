package org.itsci.controller;

import org.itsci.model.*;
import org.itsci.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
@RequestMapping("/system")
public class SystemController {
    private static final String COMMA_DELIMITER = ",";

    @Autowired
    ResourceBundleMessageSource messageSource;

    @Autowired
    SystemService systemService;

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
    @CacheEvict(value = { "enrollments", "courses", "members" }, allEntries = true)
    public String clearCache() {
        return "redirect:/";
    }


    @PostMapping(path="/student/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String uploadCSVFile(Model model, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String message = "";
        int numCol = 0;
        HttpSession session = request.getSession(false);

        List<Section> sections = systemService.findAllSection();

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
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(stu);
            enrollment.setSection(section);
            systemService.saveOrUpdateStudent(stu);
            systemService.saveEnrollment(enrollment);
        }

        return "redirect:/system/student/import";
    }
}
