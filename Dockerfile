# Start with a base image containing Java runtime
FROM adoptopenjdk/openjdk8-openj9

# Add Maintainer Info
LABEL maintainer="oscar.dap.dev@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/aplazo-api-1.0-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} aplazoApi.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/aplazoApi.jar"]
