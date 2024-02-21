FROM maven AS build

WORKDIR /usr/src/app

COPY . .

RUN mvn -Dmaven.test.skip=true package

RUN mv ./target/*.jar ./app.jar

FROM openjdk:17 AS runtime

ARG MANDACARU_POSTGRES_HOST
ARG MANDACARU_POSTGRES_PORT
ARG MANDACARU_POSTGRES_DB
ARG MANDACARU_POSTGRES_USER
ARG MANDACARU_POSTGRES_PASSWORD
ARG MANDACARU_SQL_INIT_MODE

WORKDIR /usr/src/app

COPY --from=build /usr/src/app/app.jar /usr/src/app/app.jar

CMD ["java", "-jar", "/usr/src/app/app.jar"]

EXPOSE 8080
