#!/bin/bash
mysql pvdb < del-currents-script.sql > output.tab -u root -p
