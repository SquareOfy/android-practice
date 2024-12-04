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

rootProject.name = "SecondApp"
include(":app")
include(":ex1_kakaologin")
include(":a_editText")
include(":b_othertext")
include(":c_button")
include(":d_imageView")
include(":e_widget")
include(":f_viewpage2")
include(":g_relativelayout")
include(":j_constraintlayout")
include(":k_linearlayout")
include(":h_framelayout")
include(":i_gridlayout")
