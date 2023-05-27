pluginManagement {
    repositories {
        maven { url = uri("https://maven.fabricmc.net/") }
        gradlePluginPortal()
    }
}

rootProject.name = "Case"
include("case-common")
include("case-fabric")
include("case-bukkit")
