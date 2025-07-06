FROM openjdk:17

COPY target/student-service-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT [ "java","-jar","student-service-0.0.1-SNAPSHOT.jar" ]
