ARG BUILD_ENV=development

FROM maven:3.6.3-jdk-11 AS maven

## Build in dev environment
FROM maven AS build-development
COPY . /sources
WORKDIR /build
RUN mkdir /build/tmp    
RUN mvn -f /sources/pom.xml clean install -s /sources/settings.xml
RUN cp /sources/target/*.jar ./application.jar

FROM build-${BUILD_ENV} as build

FROM paykita/base-distroless-java11
WORKDIR /app
COPY --from=build /build/application.jar ./
COPY --from=build --chown=1000:1000 /build/tmp /app/tmp
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
