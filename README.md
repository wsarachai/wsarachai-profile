# wsarachai-profile
## MySQL Settings
### คำสั่งสำหรับสร้างฐานข้อมูล
```
CREATE SCHEMA `wsarachai_db` DEFAULT CHARACTER SET utf8mb4 ;
```

### คำสั่งสำหรับแทรกข้อมูลเริ่มต้นลงระบบฐานข้อมูล
```
use wsarachai_db;

INSERT INTO `wsarachai_db`.`authorities` (`authority`, `description`) VALUES ('ROLE_ADMIN', 'ผู้ดูแลระบบ');
INSERT INTO `wsarachai_db`.`authorities` (`authority`, `description`) VALUES ('ROLE_MEMBER', 'สมาชิก');
INSERT INTO `wsarachai_db`.`logins` (`enabled`, `password`, `username`) VALUES ('1', '{bcrypt}$2a$10$/GUlfBF1jG6Z7h2IiF6UGOCniw.HQeza8pWpW/x2eGWm6LL/rAlLO', 'watcharin_s@mju.ac.th');
INSERT INTO `wsarachai_db`.`users` (`DTYPE`, `firstName`, `lastName`, `expiredDate`, `validFrom`, `login_id`) VALUES ('Member', 'Watcharin', 'Sarachai', NOW(), DATE_ADD(NOW(), INTERVAL 1 YEAR), '1');
INSERT INTO `wsarachai_db`.`user_authority` (`user_id`, `authority_id`) VALUES ('1', '1');
INSERT INTO `wsarachai_db`.`user_authority` (`user_id`, `authority_id`) VALUES ('1', '2');


## start project
   - docker-compose up -d

## stop project
   - docker-compose down

## Other
   - check mysql port `netstat -an | grep -i listen | grep -E 3306`
