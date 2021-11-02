
plugins {
    // kotlin("jvm") // version "1.5.31" apply false
    // kotlin("multiplatform") // version "1.5.31" apply false
    kotlin("plugin.serialization") version "1.5.31"

}


group = "love.forte"
version = "2.0-SNAPSHOT"

buildscript {
    repositories { mavenCentral() }

    dependencies {
        val kotlinVersion = "1.5.31"
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
    }
}


allprojects {
    repositories {
        mavenCentral()
    }
}


