# Cognitive Services - Age Detector con Spring Boot
**Servicio Cognitivo de Rapid API**, Age Detector, este modelo detecta todos los rostros de una imagen y determina automáticamente la edad de cada persona basándose en sus atributos faciales. Mediante visión artificial, nuestro detector de edad analiza los atributos faciales y estima la edad más probable. Devuelve una lista con todos los rostros de la imagen y su edad correspondiente, con sus probabilidades.

**1. Cognitive Services**
<img src ="https://wakeupandcode.com/wp-content/uploads/2019/08/azure-cognitive-services-bootcamp-event-image.png" align="right" style="width: 200px"/>
- Rapid API - Age Detector
- Detector de Edad en Imágenes

**2. Spring Boot**
<img src ="https://miro.medium.com/v2/resize:fit:716/1*98O4Gb5HLSlmdUkKg1DP1Q.png" align="right" style="height:60px; width: 200px"/>
- Java: JDK 17
- IDE: IntelliJ IDEA | Visual Studio Code | Codespace
- Maven: Apache Maven
- Frameworks: Spring Boot

**3. Maven Dependencias:**
<img src ="https://upload.wikimedia.org/wikipedia/commons/thumb/5/52/Apache_Maven_logo.svg/1280px-Apache_Maven_logo.svg.png" align="right" style="width: 200px"/>
* spring-boot-starter-webflux
* spring-boot-starter-data-r2dbc
* lombok
* reactor-test
* r2dbc-postgresql

## **Dependencias Spring WebFlux + Postgre (SQL)**

Spring WebFlux | Data R2DBC | Project Reactor | R2DBC PostgreSQL
```
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-r2dbc</artifactId>
</dependency>
<dependency>
      <groupId>io.projectreactor</groupId>
      <artifactId>reactor-test</artifactId>
      <scope>test</scope>
</dependency>
<dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>r2dbc-postgresql</artifactId>
      <scope>runtime</scope>
</dependency>
```
## **Dependencias Spring WebFlux + MongoDB (NoSQL)**

Spring WebFlux | Data MongoDB Reactive | Project Reactor
```
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
</dependency>
<dependency>
      <groupId>io.projectreactor</groupId>
      <artifactId>reactor-test</artifactId>
      <scope>test</scope>
</dependency>
```
## **Dependencias Swagger para Spring WebFlux**
```
<dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
      <version>2.0.2</version>
</dependency>
```
