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
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
