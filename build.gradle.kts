plugins {
    kotlin("jvm") version "1.9.23"
}

group = "pl.bioinf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    flatDir {
        dirs("libs")
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("pl.bioinf:generator-inst-bioinf:1.0-SNAPSHOT")
    implementation("org.apache.commons:commons-text:1.10.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}