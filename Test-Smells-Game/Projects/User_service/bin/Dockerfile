FROM openjdk:17 as builder

USER root

RUN jlink \
    --module-path "$JAVA_HOME/jmods" \
    --add-modules java.compiler,java.sql,java.naming,java.management,java.instrument,java.rmi,java.desktop,jdk.internal.vm.compiler.management,java.xml.crypto,java.scripting,java.security.jgss,jdk.httpserver,java.net.http,jdk.naming.dns,jdk.crypto.cryptoki,jdk.unsupported \
    --verbose \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /opt/jre-minimal

USER app

# Now it is time for us to build our real image on top of an slim debian image

FROM bitnami/minideb:bullseye

# Copy the JRE created in the last step into our $JAVA_HOME

COPY --from=builder /opt/jre-minimal $JAVA_HOME

ADD database database

COPY target/users_service.jar user_service.jar

EXPOSE 8081


ENTRYPOINT ["java","-jar","/user_service.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]