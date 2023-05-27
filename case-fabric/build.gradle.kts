plugins {
    id("fabric-loom")
}

repositories {
    maven { url = uri("https://maven.fabricmc.net/") }
}

dependencies {
    minecraft("com.mojang:minecraft:1.19.4")
    mappings("net.fabricmc:yarn:1.19.4+build.2")
    modImplementation("net.fabricmc:fabric-loader:0.14.21")

    val apiModules = arrayOf(
        "fabric-api-base",
        "fabric-command-api-v2",
        "fabric-lifecycle-events-v1",
        "fabric-networking-api-v1"
    )

    apiModules.forEach {
        modImplementation(fabricApi.module(it, "0.82.0+1.19.4"))
    }
    implementation(project(":case-common"))
}

tasks {
    shadowJar {
        dependencies {
            exclude("net.fabricmc:.*")
            exclude("/mappings/*")
        }
    }
}

