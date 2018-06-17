FROM openjdk:alpine

ENV LOCALE "pt_BR.UTF-8"
ENV LOCALTIME "America/Sao_Paulo"

WORKDIR /app

RUN apk update && \
    apk add bash && \
    apk add openssl && \
    apk add ca-certificates && \
    update-ca-certificates && \
    mkdir -p migrations && \
    mkdir -p bin && \
    wget https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/4.2.0/flyway-commandline-4.2.0.tar.gz && \
    tar xvf flyway-commandline-4.2.0.tar.gz && \
    rm -f flyway-commandline-4.2.0.tar.gz  && \
    mv flyway-4.2.0 bin/flyway && \
    chmod a+x bin/flyway

ADD build/libs/login-0.0.1-SNAPSHOT.jar login.jar
ADD migrations migrations

EXPOSE 4567
EXPOSE 8080

CMD ["java", "-Xmx100m","-jar","login.jar"]
