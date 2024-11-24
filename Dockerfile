# Install JDK
FROM eclipse-temurin:23-jdk

LABEL maintainer="hazim"

# BUILD APP
#-----------
# Create dir /app & change current dir into /app
WORKDIR /app

# Copy files/dir over (COPY [src] [dst/dir name])
COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .
COPY src src    

# Build jar app
#For Railway
RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true


# RUN APP
#---------
# For Railway
ENV PORT=8080

# Specify which port app needs
#EXPOSE ${SERVER_PORT}
EXPOSE ${PORT}

# Run app
#ENTRYPOINT java -jar target/day12-0.0.1-SNAPSHOT.jar
# For Railway
ENTRYPOINT SERVER_PORT=${PORT} java -jar target/day15-0.0.1-SNAPSHOT.jar