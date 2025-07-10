# wsarachai-profile

## More reference about Thymeleaf

- [Thymeleaf](https://www.thymeleaf.org/)
- [Thymeleaf - Spring Boot](https://www.thymeleaf.org/doc/articles/springbootforms.html)
- [Thymeleaf - Spring Boot - Layout Dialect](https://www.thymeleaf.org/doc/articles/layouts.html)
- [Thymeleaf - MVC Temlate](https://www.baeldung.com/thymeleaf-in-spring-mvc)

## MySQL Settings

### คำสั่งสำหรับสร้างฐานข้อมูล

```
CREATE SCHEMA `wsarachai_db` DEFAULT CHARACTER SET utf8mb4 ;
```

### คำสั่งสำหรับแทรกข้อมูลเริ่มต้นลงระบบฐานข้อมูล

#### สำหรับตาราง authorities

```
use wsarachai_db;
INSERT INTO `wsarachai_db`.`authorities` (`authority`, `description`) VALUES ('ROLE_ADMIN', 'ผู้ดูแลระบบ');
INSERT INTO `wsarachai_db`.`authorities` (`authority`, `description`) VALUES ('ROLE_TEACHER', 'อาจารย์');
INSERT INTO `wsarachai_db`.`authorities` (`authority`, `description`) VALUES ('ROLE_STAFF', 'เจ้าหน้าที่');
INSERT INTO `wsarachai_db`.`authorities` (`authority`, `description`) VALUES ('ROLE_STUDENT', 'นักเรียน');
INSERT INTO `wsarachai_db`.`authorities` (`authority`, `description`) VALUES ('ROLE_MEMBER', 'สมาชิก');
INSERT INTO `wsarachai_db`.`authorities` (`authority`, `description`) VALUES ('ROLE_USER', 'ผู้ใช้ทั่วไป');
```

#### สำหรับตาราง curriculums

```
INSERT INTO `wsarachai_db`.`curriculums` (`eng_degree_name`, `eng_name`, `thai_degree_name`, `thai_name`, `type`, `year`) VALUES ('Bachelor Of Science In Information Technology', 'Bachelor Of Science In Information Technology', 'วิทยาศาสตรบัณฑิต (เทคโนโลยีสารสนเทศ)', 'หลักสูตรวิทยาศาสตรบัณฑิต สาขาวิชาเทคโนโลยีสารสนเทศ', 'หลักสูตรระดับปริญญาตรี หลักสูตร 4 ปี', '60');
INSERT INTO `wsarachai_db`.`curriculums` (`eng_degree_name`, `eng_name`, `thai_degree_name`, `thai_name`, `type`, `year`) VALUES ('Bachelor Of Science In Information Technology', 'Bachelor Of Science In Information Technology', 'วิทยาศาสตรบัณฑิต (เทคโนโลยีสารสนเทศ)', 'หลักสูตรวิทยาศาสตรบัณฑิต สาขาวิชาเทคโนโลยีสารสนเทศ', 'หลักสูตรระดับปริญญาตรี หลักสูตร 4 ปี', '65');
```

#### สำหรับตาราง subjects

```
INSERT INTO `wsarachai_db`.`subjects` (`code`, `credit`, `credit_detail`, `description`, `enabled`, `eng_name`, `thai_name`, `type`, `curriculum_id`) VALUES ('ทส493', '3', '3 (3-0-6)', 'หัวข้อใหม่ ๆ หรือหัวข้อที่กําลังเป็นที่สนใจเกี่ยวกับเทคโนโลยีสารสนเทศ หัวข้อจะเปลี่ยนไปในแต่ละภาคเรียน', '1', 'Selected Topic in Information Technology', 'การศึกษาหัวข้อสนใจด้านเทคโนโลยีสารสนเทศ', 'แกน', '1');
INSERT INTO `wsarachai_db`.`subjects` (`code`, `credit`, `credit_detail`, `description`, `enabled`, `eng_name`, `thai_name`, `type`, `curriculum_id`) VALUES ('10306241', '3', '3 (3-0-6)', 'การศึกษาพื้นฐานของระบบบริหารจัดการเนื้อหาเว็บไซต์ การติดตั้งใช้งานระบบเว็บเซิร์ฟเวอร์และฐานข้อมูล การจัดการไฟล์ผ่าน FTP การใช้งานเครื่องมือต่าง ๆ ของ CMS การวางแผนและออกแบบจัดการเนื้อหาเว็บไซต์ การสร้างบทความ การจัดการและกำหนดระดับสิทธิ์ผู้ใช้งาน การใช้ปลั๊กอิน การพัฒนาเว็บไซต์อีคอมเมิร์ซ การติดตั้ง Template การบริหารจัดการเว็บไซต์ผ่านเครื่องแม่ข่าย', '1', 'Web Content Management System', 'ระบบบริหารจัดการเว็บไซต์', 'แกน', '2');
INSERT INTO `wsarachai_db`.`subjects` (`code`, `credit`, `credit_detail`, `description`, `enabled`, `eng_name`, `thai_name`, `type`, `curriculum_id`) VALUES ('10306493', '3', '3 (3-0-6)', 'หัวข้อใหม่ ๆ หรือหัวข้อที่กําลังเป็นที่สนใจเกี่ยวกับเทคโนโลยีสารสนเทศ หัวข้อจะเปลี่ยนไปในแต่ละภาคเรียน', '1', 'Selected Topic in Information Technology', 'การศึกษาหัวข้อสนใจด้านเทคโนโลยีสารสนเทศ', 'แกน', '2');
```

#### สำหรับตาราง rooms

```
INSERT INTO `wsarachai_db`.`rooms` (`building_id`, `building_name`, `room_id`) VALUES ('อาคารตึกจุฬาภรณ์', '3202-1', '105');
INSERT INTO `wsarachai_db`.`rooms` (`building_id`, `building_name`, `room_id`) VALUES ('อาคารเสาวรัจ นิตยวรรธนะ', 'Lab ไอที 2', '105');
INSERT INTO `wsarachai_db`.`rooms` (`building_id`, `building_name`, `room_id`) VALUES ('อาคารเรียนรวม 80 ปี', '80-301', '147');
```

#### สำหรับตาราง logins

```
INSERT INTO `wsarachai_db`.`logins` (`enabled`, `password`, `username`) VALUES ('1', 'keng', '{bcrypt}$2a$10$/GUlfBF1jG6Z7h2IiF6UGOCniw.HQeza8pWpW/x2eGWm6LL/rAlLO');
INSERT INTO `wsarachai_db`.`logins` (`enabled`, `password`, `username`) VALUES ('1', '0123456789', '{bcrypt}$2a$10$/GUlfBF1jG6Z7h2IiF6UGOCniw.HQeza8pWpW/x2eGWm6LL/rAlLO');
```

#### สำหรับตาราง users

```
INSERT INTO `wsarachai_db`.`users` (`DTYPE`, `first_name`, `last_name`, `prename`, `login_id`) VALUES ('Teacher', 'วัชรินทร์', 'สาระไชย', 'อาจารย์ ดร.', '1');
INSERT INTO `wsarachai_db`.`users` (`DTYPE`, `first_name`, `last_name`, `prename`, `start_from_year`, `student_id`, `login_id`) VALUES ('Student', 'แดง', 'นามกสุล', 'นาย', '2566', '0123456789', '2');
```

#### สำหรับตาราง courses

```
INSERT INTO `wsarachai_db`.`courses` (`semester`, `subject_id`) VALUES ('1/2567', '2');
INSERT INTO `wsarachai_db`.`courses` (`semester`, `subject_id`) VALUES ('1/2567', '1');
INSERT INTO `wsarachai_db`.`courses` (`semester`, `subject_id`) VALUES ('1/2567', '3');
```

#### สำหรับตาราง course_sections

```
INSERT INTO `wsarachai_db`.`course_sections` (`end_lab_time`, `end_lec_time`, `group_number`, `lab_day`, `lec_day`, `number_of_seat`, `start_lab_time`, `start_lec_time`, `lab_room_id`, `lec_room_id`, `course_id`) VALUES ('12:00', '14:00', '1', 'Monday', 'Wednesday', '61', '09:00', '12:00', '2', '1', '1');
INSERT INTO `wsarachai_db`.`course_sections` (`end_lab_time`, `end_lec_time`, `group_number`, `lab_day`, `lec_day`, `number_of_seat`, `start_lab_time`, `start_lec_time`, `lab_room_id`, `lec_room_id`, `course_id`) VALUES ('16:00', '14:00', '2', 'Thursday', 'Wednesday', '60', '13:00', '12:00', '2', '1', '1');
INSERT INTO `wsarachai_db`.`course_sections` (`end_lab_time`, `end_lec_time`, `group_number`, `lab_day`, `lec_day`, `number_of_seat`, `start_lab_time`, `start_lec_time`, `lab_room_id`, `lec_room_id`, `course_id`) VALUES ('11:30', '11:30', '1', 'Wednesday', 'Wednesday', '42', '8:30', '8:30', '3', '1', '2');
INSERT INTO `wsarachai_db`.`course_sections` (`end_lab_time`, `end_lec_time`, `group_number`, `lab_day`, `lec_day`, `number_of_seat`, `start_lab_time`, `start_lec_time`, `lab_room_id`, `lec_room_id`, `course_id`) VALUES ('11:30', '11:30', '1', 'Wednesday', 'Wednesday', '50', '8:30', '8:30', '3', '1', '3');
```

#### สำหรับตาราง user_authority

```
INSERT INTO `wsarachai_db`.`user_authority` (`login_id`, `authority_id`) VALUES ('1', '1');
INSERT INTO `wsarachai_db`.`user_authority` (`login_id`, `authority_id`) VALUES ('1', '2');
INSERT INTO `wsarachai_db`.`user_authority` (`login_id`, `authority_id`) VALUES ('2', '4');
```

#### สำหรับตาราง users_courses

```
INSERT INTO `wsarachai_db`.`users_courses` (`Teacher_id`, `courseSet_id`) VALUES ('1', '1');
INSERT INTO `wsarachai_db`.`users_courses` (`Teacher_id`, `courseSet_id`) VALUES ('1', '2');
INSERT INTO `wsarachai_db`.`users_courses` (`Teacher_id`, `courseSet_id`) VALUES ('1', '3');
```

#### สำหรับตาราง course_section_registrations

```
INSERT INTO `wsarachai_db`.`course_section_registrations` (`attendance`, `course_section_id`, `student_id`) VALUES ('{\'lec\': [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0], \'lab\': [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}', '1', '1');
```

## start project

- docker-compose up -d

## stop project

- docker-compose down

## Other

- check mysql port `netstat -an | grep -i listen | grep -E 3306`

#run debug
C:\share\apache-tomcat-9.0.95\bin> .\catalina.bat jpda start

## Email Tracking Feature

### Overview

The application includes an email tracking feature that allows you to monitor when recipients open emails by embedding a transparent tracking pixel.

### How to Use

1. Generate a unique tracking ID for each email recipient
2. Include the tracking pixel URL in your HTML email:
   ```
   <img src="https://your-server.com/wsarachai/email/track?id=TRACKING_ID" width="1" height="1" alt="" />
   ```
   Replace `TRACKING_ID` with the unique ID for the recipient

3. When the recipient opens the email, the tracking pixel will be loaded, and the open event will be recorded in the database with:
   - Tracking ID
   - IP address
   - User agent (browser/email client)
   - Timestamp

### Example Integration with Google Apps Script

```javascript
function sendTrackedEmails() {
  const sheet = SpreadsheetApp.getActiveSpreadsheet().getActiveSheet();
  const data = sheet.getDataRange().getValues();

  for (let i = 1; i < data.length; i++) {
    const firstName = data[i][0];
    const lastName = data[i][1];
    const email = data[i][2];
    const trackingId = data[i][3]; // Unique tracking ID for each recipient

    const subject = "Your Email Subject";
    const trackingPixel = `<img src="https://your-server.com/wsarachai/email/track?id=${trackingId}" width="1" height="1" alt="" />`;

    const body = `
      Hello ${firstName} ${lastName},<br><br>
      Your email content here.<br><br>
      ${trackingPixel}
    `;

    GmailApp.sendEmail(email, subject, "", {
      htmlBody: body
    });
  }
}
```
