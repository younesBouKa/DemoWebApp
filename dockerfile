FROM maven AS build
WORKDIR /app
COPY . .
RUN mvn package

From tomcat:8-jre8
COPY --from=build /app/target/demowebapp.war /usr/local/tomcat/webapps