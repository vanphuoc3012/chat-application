FROM openjdk:11
COPY ./target/ChatApplication-0.0.1-SNAPSHOT.jar /usr/src/chatapplication/
WORKDIR /usr/src/chatapplication
EXPOSE 8080
CMD ["java", "-jar", "ChatApplication-0.0.1-SNAPSHOT.jar"]
