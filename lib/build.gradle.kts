import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

group = "ndk.banee"
version = "1.0-SNAPSHOT"

@OptIn(ExperimentalKotlinGradlePluginApi::class)
kotlin {

    targetHierarchy.default()

    jvm {
//        jvmToolchain(20)
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    mingwX64 {
        binaries {
            sharedLib {
                //TODO : Rename sub module to avoid basename property
                baseName = "account_ledger_lib"
            }
        }
    }

    sourceSets.all {
        languageSettings.apply {
            languageVersion = "1.9"
            apiVersion = "1.9"
            progressiveMode = true
            optIn("ExperimentalStdlibApi,ExperimentalEncodingApi")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation(platform("io.ktor:ktor-bom:2.3.4"))
                implementation("io.ktor:ktor-client-core")
                implementation("io.ktor:ktor-client-auth")
                implementation("io.ktor:ktor-client-content-negotiation")
                implementation("io.ktor:ktor-client-logging")
                implementation("io.ktor:ktor-serialization-kotlinx-json")
                implementation("com.soywiz.korlibs.klock:klock:2.7.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val mingwX64Main by getting {
            dependencies {
//                implementation("io.ktor:ktor-client-curl")
                implementation("io.ktor:ktor-client-winhttp")
            }
        }
    }
}
