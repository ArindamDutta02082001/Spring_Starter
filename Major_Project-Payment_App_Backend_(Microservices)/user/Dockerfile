FROM openjdk:17
LABEL maintainer="arindam"
WORKDIR /app
ADD target/user-0.0.1-SNAPSHOT.jar /app/jar
EXPOSE 4000
ENTRYPOINT ["java",  "-jar" , "jar" , "app" , "exec"]