
FROM gradle:6.0.1-jdk8
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon
ENTRYPOINT ["java", "-jar", "/home/gradle/src/build/libs/flux.compiler-1.0.jar"]