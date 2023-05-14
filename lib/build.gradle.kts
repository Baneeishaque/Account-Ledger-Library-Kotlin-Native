plugins {
    // kotlin("multiplatform") version "1.9.20-mercury-653"
    // kotlin("multiplatform") version "1.9.20-dev-947"
    // kotlin("multiplatform") version "1.9.20-dev-810"
    // kotlin("multiplatform") version "1.8.21-395"
    kotlin("multiplatform") version "1.8.21"
}

group = "ndk.banee"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isArm64 = System.getProperty("os.arch") == "aarch64"
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" && isArm64 -> macosArm64("native")
        hostOs == "Mac OS X" && !isArm64 -> macosX64("native")
        hostOs == "Linux" && isArm64 -> linuxArm64("native")
        hostOs == "Linux" && !isArm64 -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }
    nativeTarget.apply {
        binaries {
            sharedLib {
                baseName = "native"
            }
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val nativeMain by getting
        val nativeTest by getting
    }
}

val nativeProcessResources by tasks.getting(ProcessResources::class) {

    exclude(".gitkeep")
}
