plugins {
    kotlin("multiplatform") // version "1.5.31"
    kotlin("plugin.serialization") // version "1.5.31"
}

group = "love.forte"
version = "2.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// buildscript {
//     repositories { mavenCentral() }
//
//     dependencies {
//         val kotlinVersion = "1.5.31"
//         classpath(kotlin("gradle-plugin", version = kotlinVersion))
//         classpath(kotlin("serialization", version = kotlinVersion))
//     }
// }


kotlin {
    explicitApi()

    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    jvm() {
        this.compilations.all {
            kotlinOptions.jvmTarget = "1.8"
            kotlinOptions.javaParameters = true
            testRuns.all {
                executionTask.configure {
                    useJUnit()
                }
            }
        }
    }

    js(IR) {
        browser()
        nodejs()
        useCommonJs()
    }


    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
            }
        }

        val jvmMain by getting {
            dependencies {
                // implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.0")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-properties:1.3.0")
                // implementation(kotlin("serialization-json"))
                // implementation(kotlin("serialization-properties"))
            }
        }
        val jsMain by getting
        val jsTest by getting
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.0")
                // implementation(kotlin("serialization"))
                // implementation(kotlin("serialization-core"))
                project(":core")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }
}