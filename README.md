## code-sparring-back

### ERD
![코드스파링](https://github.com/user-attachments/assets/0e7b8040-6f3e-45b0-8e66-1d1ceeb96e02)


### 연결 설정

- resources/application.properties

```properties
# jpa setting
spring.jpa.database=oracle
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
# others
app.frontURL=*
spring.output.ansi.enabled=always
```

- resources/db.properties

```properties
spring.datasource.hikari.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.hikari.jdbc-url=jdbc:oracle:thin:@[서비스 유형]?TNS_ADMIN=[전자지갑 경로]
spring.datasource.hikari.username=[사용자명]
spring.datasource.hikari.password=[패스워드]
```
