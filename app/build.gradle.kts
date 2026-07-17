plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.wamt"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    buildFeatures{
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.wamt"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "Room.schemaLocation" to "$projectDir/schemas",
                    "Room.incremental" to "true",
                    "Room.generateKotlin" to "false"
                )
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    androidTestImplementation(libs.androidx.core.testing)
    implementation(libs.hilt)
    annotationProcessor(libs.hilt.compiler)

}