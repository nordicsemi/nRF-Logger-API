plugins {
    alias(libs.plugins.nordic.android.application)
}

android {
    namespace = "no.nordicsemi.android.log.example"

    defaultConfig {
        applicationId = "no.nordicsemi.android.log.example"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":log"))
    implementation(libs.material)
    implementation(libs.androidx.fragment)
}
