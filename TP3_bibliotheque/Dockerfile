# syntax=docker/dockerfile:experimental
FROM maven:3.6.3-adoptopenjdk-8 AS builder
COPY src /TP3_bibliotheque/src
COPY pom.xml /TP3_bibliotheque
RUN --mount=type=cache,target=/root/.m2 \
    cd /TP3_bibliotheque && mvn --batch-mode clean package
#RUN mvn -f /TP3_bibliotheque/pom.xml clean package

FROM openjdk:8u275-jre
COPY --from=builder /TP3_bibliotheque/target/TP3_bibliotheque-*-jar-with-dependencies.jar /TP3_bibliotheque.jar

CMD java -jar /TP3_bibliotheque.jar