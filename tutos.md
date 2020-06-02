# REST API Login

https://docs.spring.io/spring-security-oauth2-boot/docs/current/reference/html/boot-features-security-oauth2-authorization-server.html

## oauth2 login into application
Això es pot treure de el application.yml

curl ismaguapo:bestpasswordintheworld@localhost:8080/oauth/token -dgrant_type=client_credentials -dscope=any