FROM openjdk:8
ADD build/libs/solution-0.0.1-SNAPSHOT.jar solution-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","solution-0.0.1-SNAPSHOT.jar"]
