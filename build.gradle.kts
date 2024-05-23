plugins {
    
    // TODO : Use BoM
    val kotlinVersion = "2.0.10-346"
    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
}
