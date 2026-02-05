/*************************************************
 * build.gradle.kts
 * Hubspot Mobile SDK
 *
 * Copyright (c) 2024 Hubspot, Inc.
 ************************************************/

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.dokka")
    id("com.google.devtools.ksp")

    id("com.vanniktech.maven.publish") version "0.34.0"
    id("net.researchgate.release") version "3.0.2"
}

release {
    failOnUnversionedFiles = false
    git {
        requireBranch.set("release")
    }
}

tasks.named("afterReleaseBuild") {
    dependsOn("publish")
}

android {
    namespace = "com.hubspot.mobilesdk"

    buildTypes {
        release {
            buildConfigField("Boolean", "DEBUG", "false")
            buildConfigField("String", "version", "\"${version}\"")
            postprocessing {
                consumerProguardFile("consumer-rules.pro")
                isMinifyEnabled = false
            }
        }

        debug {
            buildConfigField("Boolean", "DEBUG", "true")
            buildConfigField("String", "version", "\"${version}\"")
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi:1.15.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.google.firebase:firebase-messaging:23.4.0")
}

tasks.withType<Sign>().configureEach {
    enabled = false
}