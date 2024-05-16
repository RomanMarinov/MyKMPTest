plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("plugin.serialization") version "1.9.23"
   // alias(libs.plugins.jetbrainsKotlinAndroid)


//    implementation("org.jetbrains.compose:compose-gradle-plugin:1.6.2")
    //id("dev.icerock.mobile.multiplatform-resources")
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

//            export("dev.icerock.moko:resources:0.23.0")
//            export("dev.icerock.moko:graphics:0.9.0")
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            // отключил для теста на работу koin
//            implementation(libs.koin.android)
//            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.cio)

//            implementation("net.thauvin.erik.urlencoder:urlencoder-lib:1.5.0")
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

            //implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.serialization)


            implementation(libs.kermit.v203) //Add latest version

            // отключил для теста на работу koin
            implementation(libs.koin.core)

            implementation(libs.koin.compose) // в этом проблема

//            implementation("org.jetbrains.compose.annotation-internal:annotation:1.6.2")
            //implementation(libs.koin.android)
            //implementation(libs.koin.androidx.compose)

            //api("dev.icerock.moko:resources:0.23.0")

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

           // implementation(libs.kotlinx.coroutines.core)
            implementation("media.kamel:kamel-image:0.9.4")


            //implementation("org.jetbrains.kotlin:kotlin-serialization:2.0.0-RC1")

            implementation("net.thauvin.erik.urlencoder:urlencoder-lib:1.5.0")
            //implementation("io.github.kevinnzou:compose-webview:0.33.6")

            implementation("io.github.kevinnzou:compose-webview-multiplatform:1.9.2")

//            implementation("com.google.accompanist:accompanist-webview:0.34.0")


//            implementation("dev.icerock.moko:resources:0.23.0")
            //implementation("org.osmdroid:osmdroid-android:6.1.11")


            // это перепроверить надо ли это
            implementation("org.osmdroid:osmdroid-android:6.1.16")
//            implementation("tech.utsmankece:osm-android-compose:0.0.3")
//            implementation("com.google.maps.android:maps-compose:4.4.0")
//            implementation("com.google.android.gms:play-services-maps:18.2.0")
            ///////////


        }

        iosMain {
            dependencies {
               // implementation("io.ktor:ktor-client-ios:2.3.10")
                implementation(libs.ktor.client.darwin)


                
            }
            //sourceSets["main"].resources.srcDirs("src/commonMain/resources")

            //resources.srcDirs("src/commonMain/resources","src/iosMain/resources")
//            resources.srcDirs("src/commonMain/resources","src/iosMain/resources")



        }
        task("testClasses")
    }
}

//ios {
//
//}

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
        vectorDrawables {
            useSupportLibrary = true
        }
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
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
dependencies {
    implementation(libs.androidx.core) // корутины
    //implementation(libs.androidx.core.ktx)

    // отключил для теста на работу koin
      implementation(libs.koin.android)
     implementation(libs.koin.androidx.compose)
    implementation(libs.koin.compose)

    // отключил для теста на работу koin
    implementation(libs.koin.core)

   // implementation("io.insert-koin:koin-annotations:1.3.0")
    // ksp("io.insert-koin:koin-ksp-compiler:1.3.0")

    implementation("org.jetbrains.compose.annotation-internal:annotation:1.6.2")
    implementation(libs.androidx.constraintlayout)
    


//    implementation(libs.kotlinx.coroutines.core)
//    implementation("io.github.kevinnzou:compose-webview-multiplatform:1.9.2")


    implementation("org.osmdroid:osmdroid-android:6.1.16")
   // implementation("tech.utsmankece:osm-android-compose:0.0.3")
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
//    implementation(libs.firebase.crashlytics.buildtools)
   // implementation(libs.androidx.compose.material)

    // красивый переход от Лакнера
    // https://www.youtube.com/watch?v=mE5bLb42_Os
    //implementation("androidx.compose.animation:animation:1.7.0-alpha07")

}

