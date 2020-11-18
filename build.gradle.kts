import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
}
group = "me.flyin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven ( "https://oss.jfrog.org/artifactory/oss-snapshot-local" )
}
dependencies {
    testImplementation(kotlin("test-junit"))
    implementation ("io.github.microutils:kotlin-logging:2.0.3")
    implementation("com.github.elbekD:kt-telegram-bot:1.3.5")
    implementation("org.slf4j:slf4j-simple:1.7.6")
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}