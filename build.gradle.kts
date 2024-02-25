
import love.forte.gradle.common.core.project.setup
import love.forte.gradle.common.core.repository.Repositories

plugins {
    idea
    `simbot-telegram-changelog-generator`
    `simbot-telegram-dokka-multi-module`
    `simbot-telegram-nexus-publish`
}

setup(P.ComponentTelegram)

buildscript {
    repositories {
        mavenCentral()
    }
}


logger.info("=== Current version: {} ===", version)

allprojects {
    repositories {
        mavenCentral()
        maven {
            url = uri(Repositories.Snapshot.URL)
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}

idea {
    module.apply {
        isDownloadSources = true
    }
    project {
        modules.forEach { module ->
            module.apply {
                isDownloadSources = true
            }
        }
    }
}
