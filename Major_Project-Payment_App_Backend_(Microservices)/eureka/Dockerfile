FROM openjdk:17
LABEL maintainer="arindam"
WORKDIR /app
ADD target/eureka-0.0.1-SNAPSHOT.jar /app/jar
#ENV SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3309/userdb?createDatabaseIfNotExist=true
#ENV SPRING_DATASOURCE_USERNAME: root
#ENV SPRING_DATASOURCE_PASSWORD: admin
EXPOSE 8761
ENTRYPOINT ["java",  "-jar" , "jar" , "app" , "exec"]