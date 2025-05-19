plugins {
    id("java") 
}

dependencies {
    implementation(project(":contract"))
    implementation(project(":domainLogic"))
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
