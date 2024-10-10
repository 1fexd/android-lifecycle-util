plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven(url = "https://plugins.gradle.org/m2")
}

dependencies {
    implementation("net.nemerosa.versioning:net.nemerosa.versioning.gradle.plugin:_")
}

kotlin {
    jvmToolchain(17)
}
