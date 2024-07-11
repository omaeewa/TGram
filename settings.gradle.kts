pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    } 
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "TGram"
include(":app")
include(":feature")
include(":feature:authorization")
include(":feature:chats")
include(":core")
include(":core:ui")
include(":core:data")
include(":libtd-ktx")
include(":libtd")
include(":core:common")
include(":feature:chat")
