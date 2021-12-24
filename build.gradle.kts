import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "cn.tinytiny"
version = "1.0-SNAPSHOT"

repositories {
    // 依赖包使用aliyun maven public 镜像
    maven { setUrl("https://maven.aliyun.com/repository/public/") }
    maven { setUrl("https://maven.aliyun.com/repository/spring/") }
    mavenCentral()
}

dependencies {
//    implementation(group = "org.opencv", name = "opencv", version = "4.5.2")
    implementation(
        fileTree(
            mapOf(
                "dir" to "/usr/local/Cellar/opencv/4.5.4_1/share/java/opencv4/",
                "include" to listOf("*.jar")
            )
        )
    )
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}