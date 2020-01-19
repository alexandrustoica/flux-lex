FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /build/libs/flux.lexical.analyzer-1.0.jar flux.jar
COPY /source.flux source.flux
ENTRYPOINT ["java", "-jar", "flux.jar"]