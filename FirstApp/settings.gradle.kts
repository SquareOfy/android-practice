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
    }
}

rootProject.name = "FirstApp"
include(":app")
include(":ex2")
include(":ex3_compactivityapp")
include(":ex4_compactivityapp2")
include(":ex5_intent_implicit")
include(":ex6_lifecycle")
include(":ex7_task")
