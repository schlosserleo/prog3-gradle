plugins {
    id("java") 
}

dependencies {
    implementation(project(":contract"))
    implementation(project(":domainLogic"))
    implementation(project(":eventSystem"))
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
