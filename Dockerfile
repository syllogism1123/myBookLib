FROM openjdk:19

ENV ENVIRONMENT=prod

LABEL maintainer=xin.du@neuefische.de

EXPOSE 8080

ADD book-backend/target/app.jar app.jar

CMD [ "sh", "-c", "java -jar /app.jar" ]