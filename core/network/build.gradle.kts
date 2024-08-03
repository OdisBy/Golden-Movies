import java.io.IOException
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aetherinsight.goldentomatoes.core.network"
    compileSdk = rootProject.extra.get("compileSdk") as Int

    defaultConfig {
        minSdk = rootProject.extra.get("minSdk") as Int

        buildConfigField("String", "API_KEY", getApiKey())

    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)


    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Chucker
    debugImplementation(libs.chucker.library)
    releaseImplementation(libs.chucker.library.noop)
}

fun getApiKey(): String {
    val apiKeyFile = rootProject.file("apikey.properties")

    return if (apiKeyFile.exists()) {
        val apikeyProperties = Properties()
        try {
            apikeyProperties.load(apiKeyFile.inputStream())
            apikeyProperties.getProperty("API_KEY", "null")
        } catch (e: IOException) {
            println("API Key não encontrada. Verifique o arquivo apikey.properties.")
            "null"
        }
    } else {
        println("API Key não encontrada. Verifique o arquivo apikey.properties.")
        "null"
    }
}

