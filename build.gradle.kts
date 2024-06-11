plugins {
    
    // TODO : Use BoM
    val kotlinVersion = "2.0.10-383"
    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    alias(libs.plugins.androidLibrary).apply(false)
}
