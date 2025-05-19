plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.guava)
    implementation(project(":contract"))
    implementation(project(":domainLogic"))
    implementation(project(":cli"))
    implementation(project(":eventSystem"))
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.11.3")
       }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "MainCLI"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
