FROM openjdk:17.0.2-jdk

WORKDIR /app/gestormensajes

#COPY ./pom.xml /app/gestormensajes/
#COPY ./target/gestormensajes-0.0.1-SNAPSHOT.jar .
#COPY . .
RUN mkdir ./logs
RUN groupadd -r testuser && adduser -r -g testuser testuser

USER root
COPY ./.mvn ./.mvn
COPY ./mvnw .
RUN chown testuser:testuser ./mvnw && chmod 744 ./mvnw
COPY ./pom.xml .

#RUN ./mvnw clean package -DskipTests
#USER root
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip

COPY ./src ./src

#RUN ./mvnw clean package -DskipTests
RUN ./mvnw clean package

EXPOSE 8001

ENTRYPOINT ["java","-jar","./target/gestormensajes-0.0.1-SNAPSHOT.jar"]
