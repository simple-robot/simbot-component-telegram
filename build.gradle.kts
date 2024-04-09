/*
 * Copyright (c) 2024. ForteScarlet.
 *
 * This file is part of simbot-component-telegram.
 *
 * simbot-component-telegram is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * simbot-component-telegram is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with simbot-component-telegram.
 * If not, see <https://www.gnu.org/licenses/>.
 */


import io.gitlab.arturbosch.detekt.Detekt
import love.forte.gradle.common.core.project.setup
import love.forte.gradle.common.core.repository.Repositories

plugins {
    idea
    `simbot-telegram-changelog-generator`
    `simbot-telegram-dokka-multi-module`
    `simbot-telegram-nexus-publish`
    alias(libs.plugins.detekt)
}

setup(P.ComponentTelegram)

buildscript {
    repositories {
        mavenCentral()
    }
}


logger.info("=== Current version: {} ===", version)

allprojects {
    setup(P.ComponentTelegram)
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

detekt {
    source.setFrom(
        "simbot-component-telegram-api",
        "simbot-component-telegram-core",
        "simbot-component-telegram-stdlib",
        "simbot-component-telegram-type",
    )

    config.setFrom(rootDir.resolve("config/detekt/detekt.yml"))
    baseline = file("$projectDir/config/detekt/baseline.xml")
    parallel = true
    reportsDir = rootProject.layout.buildDirectory.dir("detekt/report").get().asFile
}

// https://detekt.dev/blog/2019/03/03/configure-detekt-on-root-project/
tasks.withType<Detekt>().configureEach {
    exclude("**/resources/")
    exclude("**/build/")
    exclude("**/*Test/kotlin/")
    exclude("**/*Test/java/")
}
