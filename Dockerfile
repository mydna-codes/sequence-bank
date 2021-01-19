FROM openjdk:11-jre-slim

ENV KUMULUZEE_VERSION=not_set
ENV KUMULUZEE_ENV_NAME=dev
ENV KUMULUZEE_ENV_PROD=false
ENV KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://localhost:5432/sequence-bank
ENV KUMULUZEE_DATASOURCES0_USERNAME=postgres
ENV KUMULUZEE_DATASOURCES0_PASSWORD=postgres
ENV KEYCLOAK_REALM=not_set
ENV KEYCLOAK_CLIENTID=not_set
ENV KEYCLOAK_AUTHSERVERURL=not_set
ENV KEYCLOAK_AUTH_CLIENTSECRET=not_set

RUN mkdir /app
WORKDIR /app

ADD ./api/target/sequence-bank.jar /app

EXPOSE 8080

CMD ["java", "-cp", "classes:dependency/*", "com.kumuluz.ee.EeApplication"]