FROM eclipse-temurin:17.0.5_8-jdk
LABEL maintainer="Flávio de Souza Santos"
WORKDIR /opt/app
COPY target/*.jar .
COPY startup.sh .
RUN chmod +x startup.sh
ENV APPLICATION_PROFILE="development"
EXPOSE 8888
ENTRYPOINT ["/opt/app/startup.sh"]
CMD ["-D", "FOREGROUND"]
