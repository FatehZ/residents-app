// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies{
        classpath("com.google.dagger:hilt-android-gradle-plugin:${libs.versions.hilt}")
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.library) apply false
    id("com.google.dagger.hilt.android") version libs.versions.hilt apply false

}