FROM openjdk:17
LABEL maintainer="arindam"
WORKDIR /app
ADD target/user-service-0.0.1-SNAPSHOT.jar /app/jar
#ENV SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3309/userdb?createDatabaseIfNotExist=true
#ENV SPRING_DATASOURCE_USERNAME: root
#ENV SPRING_DATASOURCE_PASSWORD: admin
EXPOSE 4000
ENTRYPOINT ["java",  "-jar" , "jar" , "app" , "exec"]