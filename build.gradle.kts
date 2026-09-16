plugins {
    kotlin("jvm") version "1.9.24"
}

group = "com.hugobezucha.qa"
version = "1.0.0"

repositories {
    mavenCentral()
}

val appiumJavaClientVersion = "9.3.0"
val junitVersion = "5.10.2"

dependencies {
    implementation("io.appium:java-client:$appiumJavaClientVersion")
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
    // Appium session setup can be slow, default JUnit timeout is too tight
    systemProperty("junit.jupiter.execution.timeout.default", "5m")

    // Gradle's test task runs in its own forked JVM, so -D flags passed to
    // ./gradlew aren't visible to the test code unless forwarded explicitly.
    listOf("appium.serverUrl", "app.path").forEach { key ->
        System.getProperty(key)?.let { systemProperty(key, it) }
    }
}
