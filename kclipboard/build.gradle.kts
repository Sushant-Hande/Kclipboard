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
version = "0.1.6"

kotlin {
    android {
        namespace = "org.jetbrains.kotlinx.multiplatform.library.template"
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
        name = ""
        description = ""
        inceptionYear = ""
        url = ""
        licenses {
            license {
                name = ""
                url = ""
                distribution = ""
            }
        }
        developers {
            developer {
                id = ""
                name = ""
                url = ""
            }
        }
        scm {
            url = ""
            connection = ""
            developerConnection = ""
        }
    }
}
