package org.itsci.controller;

import org.itsci.model.*;
import org.itsci.service.StudentAttenService;
import org.itsci.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Simple controller to populate development/test data into the database.
 * This endpoint is intentionally guarded and should only be enabled for
 * local development. Enable via environment variable `DEV_POPULATE=true`
 * or system property `-Dapp.dev.populate=true`.
 */
@RestController
@RequestMapping("/system/populate")
public class DevPopulateController {

  @Autowired
  private StudentAttenService studentAttenService;

  @Autowired
  private UserService<User> userService;

  @Value("${app.dev.populate:false}")
  private boolean devPopulateEnabled;

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @GetMapping("/seed")
  public String seed(@RequestParam(name = "force", required = false, defaultValue = "false") boolean force,
      HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();

    boolean envFlag = "true".equalsIgnoreCase(System.getenv("DEV_POPULATE"));
    boolean allowed = devPopulateEnabled || envFlag || (force && isLocalRequest(request));

    if (!allowed) {
      sb.append(
          "Populate disabled. Enable with env DEV_POPULATE=true or system property app.dev.populate=true or call with ?force=true from localhost.\n");
      sb.append("Current flags: app.dev.populate=").append(devPopulateEnabled).append(", DEV_POPULATE=").append(envFlag)
          .append("\n");
      return sb.toString();
    }

    // 1. Authorities
    try {
      Set<Authority> existing = studentAttenService.findAllAuthorities();
      if (existing == null || existing.isEmpty()) {
        sb.append("Creating authorities...\n");
        for (EAuthorityType t : EAuthorityType.values()) {
          Authority a = new Authority();
          a.setRoleName(t);
          a.setDescription(t.toString());
          studentAttenService.saveAuthority(a);
        }
      } else {
        sb.append("Authorities already exist (count=").append(existing.size()).append(").\n");
      }

      // 2. Curriculums
      List<Curriculum> currs = studentAttenService.findAllCurriculums();
      if (currs == null || currs.isEmpty()) {
        sb.append("Creating curriculums...\n");
        Curriculum c1 = new Curriculum();
        c1.setYear(2560);
        studentAttenService.saveCurriculum(c1);
        Curriculum c2 = new Curriculum();
        c2.setYear(2565);
        studentAttenService.saveCurriculum(c2);
      } else {
        sb.append("Curriculums already exist (count=").append(currs.size()).append(").\n");
      }

      // 3. Rooms
      List<Room> rooms = studentAttenService.findAllRooms();
      if (rooms == null || rooms.isEmpty()) {
        sb.append("Creating rooms...\n");
        Room r1 = new Room();
        r1.setBuildingName("อาคารตึกจุฬาภรณ์");
        r1.setRoomId("3202-1");
        r1.setBuildingId("105");
        studentAttenService.saveRoom(r1);

        Room r2 = new Room();
        r2.setBuildingName("อาคารเสาวรัจ นิตยวรรธนะ");
        r2.setRoomId("Lab ไอที 2");
        r2.setBuildingId("105");
        studentAttenService.saveRoom(r2);

        Room r3 = new Room();
        r3.setBuildingName("อาคารเรียนรวม 80 ปี");
        r3.setRoomId("80-301");
        r3.setBuildingId("147");
        studentAttenService.saveRoom(r3);
      } else {
        sb.append("Rooms already exist (count=").append(rooms.size()).append(").\n");
      }

      // 4. Subjects (simple examples)
      List<Subject> subjects = studentAttenService.findAllSubjects();
      if (subjects == null || subjects.isEmpty()) {
        sb.append("Creating subjects...\n");
        Subject s1 = new Subject();
        s1.setCode("ทส493");
        s1.setCredit(3.0);
        s1.setCreditDetail("3 (3-0-6)");
        s1.setDescription("หัวข้อสนใจด้านเทคโนโลยีสารสนเทศ");
        s1.setEnabled(true);
        s1.setEngName("Selected Topic in Information Technology");
        s1.setThaiName("การศึกษาหัวข้อสนใจด้านเทคโนโลยีสารสนเทศ");
        s1.setType("แกน");
        s1.setCurriculum(studentAttenService.findCurriculumById(1L));
        studentAttenService.saveSubject(s1);

        Subject s2 = new Subject();
        s2.setCode("10306241");
        s2.setCredit(3.0);
        s2.setCreditDetail("3 (3-0-6)");
        s2.setDescription("ระบบบริหารจัดการเว็บไซต์");
        s2.setEnabled(true);
        s2.setEngName("Web Content Management System");
        s2.setThaiName("ระบบบริหารจัดการเว็บไซต์");
        s2.setType("แกน");
        s2.setCurriculum(studentAttenService.findCurriculumById(2L));
        studentAttenService.saveSubject(s2);
      } else {
        sb.append("Subjects already exist (count=").append(subjects.size()).append(").\n");
      }

      // 5. Create a teacher/login with username 'keng' if no users exist
      List<User> users = studentAttenService.findAllUsers();
      if (users == null || users.isEmpty()) {
        sb.append("Creating default teacher user 'keng'...\n");
        Login login = new Login();
        login.setUsername("keng");
        String raw = "keng";
        String hashed = passwordEncoder.encode(raw);
        login.setPassword("{bcrypt}" + hashed);
        login.setEnabled(true);

        // set authorities
        Set<Authority> auths = new HashSet<>();
        Authority a1 = studentAttenService.findByRoleName(EAuthorityType.ROLE_ADMIN);
        if (a1 != null)
          auths.add(a1);
        Authority a2 = studentAttenService.findByRoleName(EAuthorityType.ROLE_TEACHER);
        if (a2 != null)
          auths.add(a2);
        login.setAuthorities(auths);

        Teacher teacher = new Teacher();
        teacher.setPrename("อาจารย์ ดร.");
        teacher.setFirstName("วัชรินทร์");
        teacher.setLastName("สาระไชย");
        teacher.setLogin(login);

        studentAttenService.saveUser(teacher);
        sb.append("User 'keng' created (password is 'keng').\n");
      } else {
        sb.append("Users already exist (count=").append(users.size()).append(").\n");
      }

      sb.append("Finished populating test data.\n");
    } catch (Exception ex) {
      sb.append("Error while populating: ").append(ex.getMessage()).append("\n");
      ex.printStackTrace();
    }

    return sb.toString();
  }

  private boolean isLocalRequest(HttpServletRequest request) {
    String addr = request.getRemoteAddr();
    return addr != null && (addr.startsWith("127.") || addr.equals("0:0:0:0:0:0:0:1") || addr.equals("::1"));
  }
}
