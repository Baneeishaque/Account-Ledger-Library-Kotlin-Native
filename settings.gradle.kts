rootProject.name = "Account-Ledger-Library-Kotlin-Native"
include("lib")
pluginManagement {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        }
    }
}
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        }
        maven {
            url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        }
    }
}
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
