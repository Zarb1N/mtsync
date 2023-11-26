# temp container to build using gradle
FROM gradle:7-jdk17 as builder

WORKDIR /opt/mtsync/app-build/

# Copy gradle settings
COPY    build.gradle    ./
COPY    settings.gradle ./
COPY    docker/ ./

# Copy project sources
COPY    src ./src

# Build jar
RUN gradle bootJar

FROM openjdk:17-slim

# Set unified timezone
ENV     TZ Europe/Moscow

# Copy built jar

COPY    --from=builder --chown=root /opt/mtsync/app-build/build/libs/*.jar /opt/mtsync/lib/app.jar

# Copy launcher and some additional files if needed
COPY    docker /opt/libooker
CMD java -server \
    -Dspring.profiles.active=docker \
    -jar /opt/mtsync/lib/app.jar