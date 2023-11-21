## code-sparring-back

### 연결 설정

- resources/db.properties

```properties
spring.datasource.hikari.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.hikari.jdbc-url=jdbc:oracle:thin:@[서비스 유형]?TNS_ADMIN=[전자지갑 경로]
spring.datasource.hikari.username=[사용자명]
spring.datasource.hikari.password=[패스워드]
```