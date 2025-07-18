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

#### สำหรับตาราง email_tracking_logs

```
-- Create the email_tracking_logs table if it doesn't exist
CREATE TABLE IF NOT EXISTS `wsarachai_db`.`email_tracking_logs` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `tracking_id` VARCHAR(255) NOT NULL,
  `recipient_email` VARCHAR(255) NULL,
  `ip_address` VARCHAR(50) NULL,
  `user_agent` TEXT NULL,
  `timestamp` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_tracking_id` (`tracking_id`),
  INDEX `idx_recipient_email` (`recipient_email`),
  INDEX `idx_timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## start project

- docker-compose up -d

## stop project

- docker-compose down

## Other

- check mysql port `netstat -an | grep -i listen | grep -E 3306`

#run debug
C:\share\apache-tomcat-9.0.95\bin> .\catalina.bat jpda start

## Debugging with Remote Debugging

To debug the application remotely:

1. Start Tomcat with JPDA (Java Platform Debugger Architecture) enabled:

   ```
   C:\share\apache-tomcat-9.0.95\bin> .\catalina.bat jpda start
   ```

2. In your IDE, connect to the remote debugging port (default: 8000)

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

4. To check if an email has been opened programmatically, use the status endpoint:
   ```
   GET https://your-server.com/wsarachai/email/opened?id=TRACKING_ID
   ```
   This returns a JSON response with the tracking status:
   ```json
   {
     "trackingId": "TRACKING_ID",
     "opened": true,
     "openCount": 2,
     "firstOpenedAt": "2025-07-10T14:30:45.123Z",
     "lastOpenedAt": "2025-07-10T16:20:15.789Z"
   }
   ```

### Checking Email Opens

To check if recipients have opened your emails:

1. **Through the Admin Interface**:

   - Navigate to `/admin/email-tracking/logs` in your application
   - View all email open events in the tracking logs table
   - Search for specific tracking IDs to see if a particular email was opened
   - Delete individual tracking logs or all logs for a specific tracking ID

2. **Programmatically**:

   - Query the `email_tracking_logs` table in your database
   - Check for entries with your tracking ID
   - Multiple entries indicate the email was opened multiple times

3. **Creating SQL Queries**:

   ```sql
   -- Check if a specific email was opened
   SELECT * FROM email_tracking_logs WHERE tracking_id = 'YOUR_TRACKING_ID';

   -- Get a count of opens for a specific email
   SELECT COUNT(*) FROM email_tracking_logs WHERE tracking_id = 'YOUR_TRACKING_ID';

   -- Get list of all opens with timestamps
   SELECT tracking_id, recipient_email, timestamp, ip_address
   FROM email_tracking_logs
   ORDER BY timestamp DESC;

   -- Delete all tracking logs for a specific tracking ID
   DELETE FROM email_tracking_logs WHERE tracking_id = 'YOUR_TRACKING_ID';
   ```

### Example Integration with Google Apps Script

```javascript
function sendTrackedEmails() {
  const sheet = SpreadsheetApp.getActiveSpreadsheet().getActiveSheet();
  const data = sheet.getDataRange().getValues();

  for (let i = 1; i < data.length; i++) {
    const email = data[i][0];
    const firstName = data[i][1];
    const lastName = data[i][2];
    const trackingId = data[i][3];
    const status = data[i][4];

    if (status === "") {
      const subject =
        "ยืนยันการลงทะเบียนอบรม n8n Starter Training - MJU Alumni (July 2025)";
      const trackingPixel = `<img src="https://itscidev.mju.ac.th/wsarachai/email/track?id=${trackingId}" width="1" height="1" alt="" />`;

      const body = `
        เรียนคุณ ${firstName} ${lastName},<br><br>
        ขอขอบคุณที่ลงทะเบียนเข้าร่วมอบรม “n8n Starter Training - MJU Alumni” ซึ่งจะจัดขึ้นในวันที่ 19-20 กรกฎาคม 2568 เวลา 09:30 - 17:00 น. ณ ห้องปฏิบัติการคอมพิวเตอร์ Science Digital Lab ชั้น 1 อาคารแม่โจ้ 60 ปี คณะวิทยาศาสตร์ มหาวิทยาลัยแม่โจ้<br><br>
        การลงทะเบียนของคุณได้รับการยืนยันเรียบร้อยแล้ว ทีมงานขอแจ้งรายละเอียดเบื้องต้นดังนี้:<br><br>

        <strong>สิ่งที่ต้องเตรียม:</strong><br>
        <ul>
          <li>คอมพิวเตอร์โน้ตบุ๊กที่สามารถเชื่อมต่ออินเทอร์เน็ต</li>
          <li>ไม่จำเป็นต้องติดตั้งซอฟต์แวร์เพิ่มเติม ผู้จัดอบรมเตรียมระบบไว้ให้พร้อมใช้งาน</li>
        </ul><br><br>

        <strong>ติดต่อสอบถามเพิ่มเติม:</strong><br>
        <ul>
          <li>Facebook: IT.Major.MJU</li>
          <li>อีเมล: infoitsci@mju.ac.th</li>
          <li>โทร: 053-873900</li>
        </ul><br><br>

        หากคุณมีข้อสงสัยเพิ่มเติมหรือต้องการเปลี่ยนแปลงข้อมูลการลงทะเบียน กรุณาติดต่อทีมงานได้ทุกเมื่อ<br><br>

        ขอแสดงความนับถือ<br>
        ทีมงานจัดอบรม n8n Starter Training<br>
        สาขาเทคโนโลยีสารสนเทศ คณะวิทยาศาสตร์ มหาวิทยาลัยแม่โจ้<br><br>

        ${trackingPixel}
      `;

      GmailApp.sendEmail(email, subject, "", {
        htmlBody: body,
      });
      sheet.getRange(i + 1, 5).setValue("EMAIL_SENT");
    } else {
      const response = UrlFetchApp.fetch(
        `https://itscidev.mju.ac.th/wsarachai/email/opened?id=${trackingId}`
      );
      const data = JSON.parse(response.getContentText());
      if (data.opened) {
        console.log("Email was opened " + data.openCount + " times");
        console.log("First opened at: " + new Date(data.firstOpenedAt));
        sheet.getRange(i + 1, 5).setValue("EMAIL_OPENED");
      } else {
        console.log("Email has not been opened yet");
      }
    }
  }
}
```
