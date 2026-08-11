plugins {
    alias(libs.plugins.nordic.android.library)
    alias(libs.plugins.nordic.publish.android)
}

group = "no.nordicsemi.android"

android {
    namespace = "no.nordicsemi.android.log.timber"

    defaultConfig {
        minSdk = 16
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

nordicPublishing {
    POM_ARTIFACT_ID = "log-timber"
    POM_NAME = "Timber extension for nRF Logger Library"
    POM_DESCRIPTION = "Timber extension for nRF Logger Library"
    POM_URL = "https://github.com/nordicsemi/nRF-Logger-API"
    POM_SCM_URL = "https://github.com/nordicsemi/nRF-Logger-API"
    POM_SCM_CONNECTION = "scm:git@github.com:nordicsemi/nRF-Logger-API.git"
    POM_SCM_DEV_CONNECTION = "scm:git@github.com:nordicsemi/nRF-Logger-API.git"
}

dependencies {
    api(project(":log"))
    api(libs.timber)
}
