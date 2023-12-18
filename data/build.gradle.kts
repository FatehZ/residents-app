plugins {
    id("com.android.library")
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "com.ktxdevelopment.data"
    compileSdk = 34

    configurations.all {
        resolutionStrategy {
            eachDependency {
                if ((requested.group == "org.jetbrains.kotlin") && (requested.name.startsWith("kotlin-stdlib"))) {
                    useVersion("1.8.0")
                }
            }
        }
    }

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packagingOptions {
        resources.excludes.add("META-INF/**/*")
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.core.ktx)
    kapt(libs.androidx.room.compiler)

    implementation(libs.net.gson)

    implementation(libs.hilt.android)
    implementation(libs.logging.interceptor.v491)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}