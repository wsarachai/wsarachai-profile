#!/bin/bash
mysqldump pvdb > "backup-file-"$(date +"%Y_%m_%d_%I_%M_%p").sql"" -u root -p 
