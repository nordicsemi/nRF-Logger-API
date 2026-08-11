// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    // Nordic plugins are defined in https://github.com/nordicsemi/Nordic-Gradle-Plugins
    alias(libs.plugins.nordic.android.application) apply false
    alias(libs.plugins.nordic.android.library) apply false
    alias(libs.plugins.nordic.publish.android) apply false

    // This applies Nordic look & feel to generated Dokka documentation.
    alias(libs.plugins.nordic.dokka) apply true
}

// Configure main Dokka page
dokka {
    pluginsConfiguration.html {
        homepageLink.set("https://github.com/nordicsemi/nRF-Logger-API")
    }
}
