import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.aetherinsight.goldentomatoes.work_managers"
    compileSdk = rootProject.extra.get("compileSdk") as Int

    defaultConfig {
        minSdk = rootProject.extra.get("minSdk") as Int
    }

    composeCompiler {
        includeSourceInformation = true

        featureFlags = setOf(
            ComposeFeatureFlag.StrongSkipping,
            ComposeFeatureFlag.OptimizeNonSkippingGroups
        )
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
    implementation(project(":data:data"))

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.worker)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.core.ktx)

    implementation(libs.work.runtime)

    implementation(libs.timber)
}
