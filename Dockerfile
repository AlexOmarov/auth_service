FROM openjdk:8-jdk-alpine
LABEL maintainer="dungeonswdragons@gmail.com"
RUN addgroup -S dwd && adduser -S dwd -G dwd
USER dwd:dwd
VOLUME /tmp
EXPOSE 8080
ARG DEPENDENCY=build/deps
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","ru/somarov/auth_service/AuthServiceApplicationKt"]