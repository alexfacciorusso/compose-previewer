plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    `maven-publish`
}

group = "com.alexfacciorusso"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.common)
}

kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        register<MavenPublication>("lib") {
            from(components["java"])
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}
