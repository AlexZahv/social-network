FROM openjdk:17-oraclelinux8

COPY ./target/*.jar otus.jar

EXPOSE 8080

CMD ["java","-jar","/otus.jar"]
