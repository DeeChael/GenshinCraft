pluginManagement {

    repositories {
        gradlePluginPortal()
        maven {
            url = uri("https://papermc.io/repo/repository/maven-public/")
        }
    }

}

rootProject.name = "genshin-craft"
include("genshin-craft-paper")
include("genshin-craft-velocity")
