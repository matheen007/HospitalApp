@echo off
javac -cp "lib\mysql-connector-j-9.2.0.jar" -d bin src\*.java  
java -cp "lib\mysql-connector-j-9.2.0.jar;bin" HospitalApp
pause
