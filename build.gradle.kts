plugins {
    id("java")
    application
    id("com.gradleup.shadow") version "9.3.0"
}

group = "fr.ensimag"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
//    maven {
//        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
//        mavenContent {
//            snapshotsOnly()
//        }
//    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

//    implementation(platform("dev.tamboui:tamboui-bom:0.2.0-SNAPSHOT"))
//    // For Toolkit DSL (recommended)
//    implementation("dev.tamboui:tamboui-toolkit")
//
//    // JLine backend (required)
//    implementation("dev.tamboui:tamboui-jline3-backend")

    compileOnly("org.projectlombok:lombok:1.18.44")
    annotationProcessor("org.projectlombok:lombok:1.18.44")

    testCompileOnly("org.projectlombok:lombok:1.18.44")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.44")

    implementation("org.yaml:snakeyaml:2.6")
    implementation("org.jline:jline:3.26.1")
}

application {
    mainClass = "fr.ensimag.Main"
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
    jvmArgs(
        "-Dfile.encoding=UTF-8",
        "-Dstdout.encoding=UTF-8",
        "-Dsun.stdout.encoding=UTF-8"
    )
}