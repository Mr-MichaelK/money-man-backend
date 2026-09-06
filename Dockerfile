# syntax=docker/dockerfile:1

################################################################################

# Create a stage for resolving and downloading dependencies.
FROM eclipse-temurin:21-jdk-jammy as deps

WORKDIR /build

# Copy the gradle wrapper with executable permissions.
COPY --chmod=0755 gradlew gradlew
COPY gradle/ gradle/

# Copy the build configuration files.
COPY build.gradle settings.gradle ./

# Download dependencies as a separate step to take advantage of Docker's caching.
# Leverage a cache mount to /root/.gradle so that subsequent builds don't re-download packages.
RUN --mount=type=cache,target=/root/.gradle ./gradlew dependencies --no-daemon

################################################################################

# Create a stage for building the application.
FROM deps as package

WORKDIR /build

# Copy source files.
COPY ./src src/

# Build the application jar file.
# Leverage the Gradle cache again to speed up builds.
RUN --mount=type=cache,target=/root/.gradle ./gradlew bootJar --no-daemon -x test && \
    mv build/libs/*-SNAPSHOT.jar build/libs/app.jar || mv build/libs/*.jar build/libs/app.jar

################################################################################

# Create a new stage for running the application.
FROM eclipse-temurin:21-jre-jammy AS final

# Create a non-privileged user that the app will run under.
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

# Copy the executable fat jar from the "package" stage.
COPY --from=package /build/build/libs/app.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]
