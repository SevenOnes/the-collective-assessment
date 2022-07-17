FROM openjdk:17-oracle

VOLUME /tmp

ADD target/the-collective-assessment-0.0.1-SNAPSHOT.jar target/app.jar

RUN bash -c 'touch target/app.jar'

ENTRYPOINT ["java","-jar","target/app.jar"]