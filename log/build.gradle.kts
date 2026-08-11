plugins {
    alias(libs.plugins.nordic.android.library)
    alias(libs.plugins.nordic.publish.android)
}

group = "no.nordicsemi.android"

android {
    namespace = "no.nordicsemi.android.log"

    defaultConfig {
        minSdk = 16
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

nordicPublishing {
    POM_ARTIFACT_ID = "log"
    POM_NAME = "nRF Logger API Library"
    POM_DESCRIPTION = "nRF Logger API Library"
    POM_URL = "https://github.com/nordicsemi/nRF-Logger-API"
    POM_SCM_URL = "https://github.com/nordicsemi/nRF-Logger-API"
    POM_SCM_CONNECTION = "scm:git@github.com:nordicsemi/nRF-Logger-API.git"
    POM_SCM_DEV_CONNECTION = "scm:git@github.com:nordicsemi/nRF-Logger-API.git"
}

dependencies {
    // Required for @NonNull and @Nullable
    api(libs.androidx.annotation)
}
