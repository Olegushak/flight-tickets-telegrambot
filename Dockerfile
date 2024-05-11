FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV TELEGRAM_NAME=Find_best_ticket_bot
ENV TELEGRAM_TOKEN=7111563401:AAFfqL4-8ZwbYxAfR7cgqap6h7b2HqYK5oE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dtelegram.name=${TELEGRAM_NAME}", "-Dtelegram.token=${TELEGRAM_TOKEN}","-jar","/app.jar"]