FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV TELEGRAM_NAME=bot
ENV TELEGRAM_TOKEN=7111556401:bAFfqL4-8ZwbYxAfR7fgqap6h7b2HqgK5oE
ENV BOT_DB_USERNAME=test
ENV BOT_DB_PASSWORD=test_password
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}","-Dspring.datasource.username=${BOT_DB_USERNAME}", "-Dtelegram.name=${TELEGRAM_NAME}", "-Dtelegram.token=${TELEGRAM_TOKEN}","-jar","/app.jar"]