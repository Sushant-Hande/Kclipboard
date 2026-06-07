import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.vanniktech.mavenPublish)
    id("org.jetbrains.kotlinx.kover") version "0.9.8"
}

// ...no top-level dependencies added for instrumentation to avoid
// configuration errors across Kotlin/Android source-set layout versions.

group = "dev.sushanthande.kclipboard"
version = "1.0.0"

kotlin {
    android {
        namespace = "dev.sushanthande.kclipboard"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava() // enable java compilation support
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.runtime)
            implementation(libs.foundation)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        // Add coroutine dependency for host JVM tests (androidHostTest)
        val androidHostTest by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlin.test)
            }
        }
        // Add dependencies for device instrumentation tests (androidDeviceTest)
        val androidDeviceTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.junit)
                implementation(libs.ext.junit)
                implementation(libs.core)
                implementation("androidx.compose.ui:ui-test-junit4:${libs.versions.composeMultiplatform.get()}")
            }
        }
    }
}

kover {
    reports {
        filters {
            excludes {
                // Exclude auto-generated classes or specific packages
                classes("*BuildConfig*", "*.DI*", "*ViewModelModule*")
            }
        }
    }
}

// Fix Compose resources configuration for androidDeviceTest variant
// The Compose plugin doesn't properly handle device test variants, so we disable this task
afterEvaluate {
    tasks.matching { it.name == "copyAndroidDeviceTestComposeResourcesToAndroidAssets" }.forEach {
        it.enabled = false
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "kclipboard", version.toString())

    pom {
        name = "Kclipboard"
        description = "Kotlin Multiplatform library for clipboard management."
        inceptionYear = "2026"
        url = "https://github.com/Sushant-Hande/Kclipboard"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "Sushant-Hande"
                name = "Sushant Hande"
                email = "sushanthande1@gmail.com"
                url = "https://sushanthande.dev"
                roles.add("Kotlin Multiplatform Developer")
            }
        }
        scm {
            url = "https://github.com/Sushant-Hande/Kclipboard"
            connection = "scm:git:git://github.com/Sushant-Hande/Kclipboard.git"
            developerConnection = "scm:git:ssh://git@github.com/Sushant-Hande/Kclipboard.git"
        }
    }
}
