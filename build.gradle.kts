plugins {
    kotlin("jvm") version "1.8.0"
}

allprojects {
    group = "net.deechael"
    version = "1.00.0"

    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}