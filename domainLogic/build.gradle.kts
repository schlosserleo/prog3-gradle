plugins {
    id("java") 
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":contract"))
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.11.3")
            
            dependencies {
                // Add Mockito dependencies to the test suite
                implementation("org.mockito:mockito-core:5.8.0")
                implementation("org.mockito:mockito-junit-jupiter:5.8.0")
            }
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}
