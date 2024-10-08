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

rootProject.name = "Golden Movies"
include(":app")
include(":core:ui")
include(":core:network")
include(":feature:home")
include(":feature:details")
include(":feature:movielist")
include(":data:data")
include(":work-managers")
include(":data:remote")
include(":data:local")
include(":testutils")
include(":core:data")
include(":feature:search_bar")
include(":core:usecases")
