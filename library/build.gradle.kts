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

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
