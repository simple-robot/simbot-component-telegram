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

import com.google.devtools.ksp.gradle.KspTaskMetadata
import love.forte.gradle.common.core.project.setup
import love.forte.gradle.common.kotlin.multiplatform.applyTier1
import love.forte.gradle.common.kotlin.multiplatform.applyTier2
import love.forte.gradle.common.kotlin.multiplatform.applyTier3
import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    alias(libs.plugins.ksp)
    `simbot-telegram-dokka-partial-configure`
    `simbot-telegram-suspend-transform-configure`
}

setup(P.ComponentTelegram)

configJavaCompileWithModule("simbot.component.telegram.core")
apply(plugin = "simbot-telegram-multiplatform-maven-publish")

// configJsTestTasks()

kotlin {
    explicitApi()
    applyDefaultHierarchyTemplate()

    sourceSets.configureEach {
        languageSettings {
            optIn("love.forte.simbot.annotations.InternalSimbotAPI")
        }
    }

    configKotlinJvm()

    js(IR) {
        configJs()
    }

    applyTier1()
    applyTier2()
    applyTier3(supportKtorClient = true)

    sourceSets {
        commonMain.dependencies {
            api(project(":simbot-component-telegram-api"))
            api(project(":simbot-component-telegram-stdlib"))

            implementation(libs.simbot.api)
            implementation(libs.simbot.common.annotations)

            api(libs.kotlinx.coroutines.core)
            api(libs.simbot.logger)
            api(libs.simbot.common.suspend)
            api(libs.simbot.common.atomic)
            api(libs.simbot.common.core)

            api(libs.ktor.client.core)
            // api(libs.ktor.client.contentNegotiation)
            // api(libs.kotlinx.serialization.json)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.simbot.api)
            implementation(libs.simbot.core)
            implementation(libs.kotlinx.coroutines.debug)
            implementation(libs.kotlinx.coroutines.test)
            // https://ktor.io/docs/http-client-testing.html
            implementation(libs.ktor.client.mock)
        }

        jvmMain.dependencies {
            compileOnly(libs.simbot.common.annotations)
            compileOnly(libs.reactor.core)
            compileOnly(libs.kotlinx.coroutines.reactive)
        }

        jvmTest.dependencies {
            implementation(libs.ktor.client.cio)
            implementation(libs.simbot.logger.slf4jimpl)
            implementation(libs.kotlinx.coroutines.reactor)
            implementation(libs.reactor.core)
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }

        mingwTest.dependencies {
            implementation(libs.ktor.client.winhttp)
        }
    }

}


// https://github.com/google/ksp/issues/963#issuecomment-1894144639
dependencies {
    kspCommonMainMetadata(project(":internal-processors:component-events-processor"))
    kspCommonMainMetadata(project(":internal-processors:include-component-message-elements-processor"))
    kspCommonMainMetadata(project(":internal-processors:component-sending-resolvers-processor"))
}
kotlin.sourceSets.commonMain {
    // solves all implicit dependency trouble and IDEs source code detection
    // see https://github.com/google/ksp/issues/963#issuecomment-1894144639
    tasks.withType<KspTaskMetadata> { kotlin.srcDir(destinationDirectory) }
}

tasks.withType<DokkaTaskPartial>().configureEach {
    dokkaSourceSets.configureEach {
        suppressGeneratedFiles.set(false)
    }
}
