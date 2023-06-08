plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization")
    id ("com.apollographql.apollo3")
}

apollo {
    service("service") {
        packageNamesFromFilePaths()
    }
}

android {
    namespace = "com.example.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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

    val daggerHiltVersion = "2.44"

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //ksp
    ksp ("androidx.room:room-compiler:2.5.1")

    //DaggerHilt
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    ksp ("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")

    //ApolloClient
    implementation("com.apollographql.apollo3:apollo-runtime:3.8.2")

    // Room
    implementation("androidx.room:room-runtime:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")

    //Paging3
    implementation("androidx.paging:paging-runtime-ktx:3.2.0-alpha06")

    implementation(project(":core"))
}