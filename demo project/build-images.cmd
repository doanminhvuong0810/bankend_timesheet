@echo off
start mvnw.cmd --pl demo -am spring-boot:build-image
start mvnw.cmd --pl discovery -am spring-boot:build-image
start mvnw.cmd --pl gateway -am spring-boot:build-image
start mvnw.cmd --pl security -am spring-boot:build-image
start mvnw.cmd --pl timesheet -am spring-boot:build-image
echo Press Enter after finish building images
pause
docker save -o demo.tar demo
docker save -o discovery.tar discovery 
docker save -o gateway.tar gateway 
docker save -o security.tar security
docker save -o timesheet.tar timesheet