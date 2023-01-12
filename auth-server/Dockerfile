FROM openjdk:17-jdk-alpine
LABEL maintainer="Flávio de Souza Santos"
WORKDIR /opt/app
COPY target/*.jar .
COPY startup.sh .
RUN chmod +x startup.sh
ENV APPLICATION_PROFILE="development"
EXPOSE 80
ENTRYPOINT ["/opt/app/startup.sh"]
CMD ["-D", "FOREGROUND"]