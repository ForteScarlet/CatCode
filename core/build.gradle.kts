plugins {
    kotlin("multiplatform") // version "1.5.30"
}

group = "love.forte"
version = "2.0-SNAPSHOT"

repositories {
    mavenCentral()
}

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
            //
        }

        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
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