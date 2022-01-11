
FROM openjdk:latest
EXPOSE 8080
VOLUME /tmp
ADD target/SpringBootExecutableJar-0.1.0-SNAPSHOT.jar SpringBootExecutableJar-0.1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/SpringBootExecutableJar-0.1.0-SNAPSHOT.jar"]

