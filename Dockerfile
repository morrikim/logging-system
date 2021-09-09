#Company
#FROM openjdk:11.0.9-slim
#ARG VERSION
#ENV VERSION=${VERSION}
#COPY build/libs/api-${VERSION}.jar .
#CMD java -Duser.timezone=Asia/Seoul -jar api-${VERSION}.jar

FROM openjdk:11.0.11
LABEL maintainer="morriskim@kakao.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/logging-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} morris-springboot.jar
ENTRYPOINT ["java","-jar","morris-springboot.jar"]