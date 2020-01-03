FROM openjdk:8
VOLUME /tmp
ADD ./target/servicioATM-0.0.1-SNAPSHOT.jar atm.jar
ENTRYPOINT ["java","-jar","/atm.jar"]