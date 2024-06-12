plugins {
    
    // TODO : Use BoM
    val kotlinVersion = "2.0.20-saturn-411"
    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    alias(libs.plugins.androidLibrary).apply(false)
}
