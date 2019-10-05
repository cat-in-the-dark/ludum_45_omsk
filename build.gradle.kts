group = "org.catinthedark"
version = "1.0-SNAPSHOT"

tasks.wrapper {
    val gradleVersion = "5.4.1"
    distributionUrl = "https://services.gradle.org/distributions/gradle-$gradleVersion-all.zip"
}

plugins {
    kotlin("jvm") version "1.3.50" apply false
}
