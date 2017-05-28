# Require tool 
- java 8
- maven 3.3.9
- docker 1.13.0

# Stack
- Spring boot 
- H2 (in memory db)
- Mysql (for production use)
- Flyway (DB version control)
- Retrofit (http client) 
- Swagger

# How to run 

- ### local profile 
  use h2 in memory DB  for  test in local machine
```
  mvn clean install;
  mvn spring-boot:run;
```  

- ### prod profile
  use mysql for db  and use docker for setup the enviroment 
```
  mvn clean install
  mvn docker:build
  docker-compose up
```
# Documentation

  Once application was run you can access swagger page at 

  http://localhost:8080/swagger-ui.html

# How to test 

  I have provided posman collection that you can use for test application or you try to run integration test by.

```
 mvn clean test -Pintegration
```


  Also you can connect to DB to view the data directly.

   ## h2 

    1 go to http://localhost:8080/h2-console

    2 put jdbc:h2:mem:testdb  in JDBC URL

    3 click connect  

  ## mysql 

   connect to  localhost:3306
   
   ```
   usename: root
   password: admin 
   schema: todo
   ```


    