import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)




        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            //https://github.com/Tlaster/PreCompose/blob/master/docs/setup.md
            api(compose.foundation)
            api(compose.animation)
            api(libs.precompose)
           // api(libs.precompose.molecule) // For Molecule intergration
            api(libs.precompose.viewmodel) // For ViewModel intergration
           // api(libs.precompose.koin) // For Koin intergration

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)

            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.serialization)

           // implementation(libs.kermit) // с ошибкой

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            //implementation(libs.koin.android)
          //  implementation(libs.koin.androidx.compose)

        }
        task("testClasses")
    }
}

android {
    namespace = "com.dev_marinov.my_compose_multi"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.dev_marinov.my_compose_multi"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
dependencies {
//    implementation(libs.androidx.core)
    //implementation(libs.androidx.core.ktx)
      implementation(libs.koin.android)
     implementation(libs.koin.androidx.compose)

   // implementation(libs.koin.bom)
    implementation(libs.koin.core)
}

