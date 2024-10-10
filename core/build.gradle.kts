import fe.buildsrc.Version
import fe.buildsrc.publishing.PublicationComponent
import fe.buildsrc.publishing.asProvider
import fe.buildsrc.publishing.publish

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("net.nemerosa.versioning")
    `maven-publish`
}

group = "fe.android.lifecycle.util.core"

android {
    namespace = group.toString()
    compileSdk = Version.COMPILE_SDK

    defaultConfig {
        minSdk = Version.MIN_SDK
    }

    kotlin {
        jvmToolchain(Version.JVM)
    }

    dependencies {
        implementation(AndroidX.lifecycle.runtime)
        implementation(AndroidX.lifecycle.common)
//        implementation(platform(AndroidX.compose.bom))
//        implementation(PinnedVersions.ComposeUi)
//        implementation(PinnedVersions.Material3)
    }

    publishing {
        multipleVariants {
            allVariants()
            withSourcesJar()
        }
    }
}

publishing.publish(
    project,
    group.toString(),
    versioning.asProvider(project),
    PublicationComponent.RELEASE
)
