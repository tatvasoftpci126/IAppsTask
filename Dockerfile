FROM amazoncorretto:17
VOLUME /temp
EXPOSE 9090
ADD target/iapps-0.0.1-SNAPSHOT.jar iapps-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/iapps-0.0.1-SNAPSHOT.jar"]