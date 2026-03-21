plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.bisirkinmyfirstapp"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
        buildFeatures {
            viewBinding = true
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        defaultConfig {
        applicationId = "com.example.bisirkinmyfirstapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

//noinspection WrongGradleMethod
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.room3.runtime)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.10.0")
    //noinspection UseTomlInstead
    implementation("androidx.activity:activity-ktx:1.13.0")
    //noinspection GradleDependency,UseTomlInstead
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    //noinspection GradleDependency,UseTomlInstead
    implementation("com.google.android.material:material:1.9.0")

    }
}