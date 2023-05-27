plugins {
    kotlin("jvm") version "1.8.21"
    id("fabric-loom") version "1.2-SNAPSHOT" apply false // To avoid Java 19 error
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `maven-publish`
    java
}

allprojects {
    group = "com.marcusslover.caselib"
    version = "1.0-SNAPSHOT"

    apply(plugin = "kotlin")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "maven-publish")


    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation(kotlin("test"))
    }

    tasks.test {
        useJUnitPlatform()
    }

    kotlin {
        jvmToolchain(17)
    }

    java {
        withSourcesJar()
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.marcusslover.caselib"
                version = "1.0-SNAPSHOT"

                from(components["java"])
            }
        }
    }
}