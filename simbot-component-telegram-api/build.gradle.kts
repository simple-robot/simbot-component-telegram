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

import love.forte.gradle.common.core.project.setup
import love.forte.gradle.common.kotlin.multiplatform.applyTier1
import love.forte.gradle.common.kotlin.multiplatform.applyTier2
import love.forte.gradle.common.kotlin.multiplatform.applyTier3
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import java.nio.file.StandardOpenOption
import kotlin.io.path.writeText

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    alias(libs.plugins.ksp)
    `simbot-telegram-dokka-partial-configure`
}

setup(P.ComponentTelegram)

useK2()
configJavaCompileWithModule("simbot.component.telegram.api")
// apply(plugin = "simbot-telegram-multiplatform-maven-publish")

//configJsTestTasks()

kotlin {
    explicitApi()
    applyDefaultHierarchyTemplate()

    sourceSets.configureEach {
        languageSettings {
        }
    }

    configKotlinJvm()

    js(IR) {
        configJs()
    }

    applyTier1()
    applyTier2()
    applyTier3(supportKtorClient = true)

    // withKotlinTargets { target ->
    //     targets.findByName(target.name)?.compilations?.all {
    //         // 'expect'/'actual' classes (including interfaces, objects, annotations, enums, and 'actual' typealiases) are in Beta. You can use -Xexpect-actual-classes flag to suppress this warning. Also see: https://youtrack.jetbrains.com/issue/KT-61573
    //         kotlinOptions.freeCompilerArgs += "-Xexpect-actual-classes"
    //     }
    // }

    sourceSets {
        commonMain.dependencies {
            api(project(":simbot-component-telegram-type"))
            api(libs.kotlinx.coroutines.core)
            api(libs.simbot.logger)
            api(libs.simbot.common.suspend)
            api(libs.simbot.common.core)
            compileOnly(libs.simbot.common.annotations)

            api(libs.ktor.client.core)
            api(libs.ktor.client.contentNegotiation)
            api(libs.kotlinx.serialization.json)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.debug)
            implementation(libs.kotlinx.coroutines.test)
            // https://ktor.io/docs/http-client-testing.html
            implementation(libs.ktor.client.mock)
        }

        jvmMain.dependencies {
//            compileOnly(libs.simbot.api) // use @Api4J annotation
        }

        jvmTest.dependencies {
            implementation(libs.ktor.client.cio)
            implementation(libs.log4j.api)
            implementation(libs.log4j.core)
            implementation(libs.log4j.slf4j2)
            implementation(libs.kotlinx.coroutines.reactor)
            implementation(libs.reactor.core)
            // mockK
            implementation(libs.mockk)
        }

        jsMain.dependencies {
            api(libs.ktor.client.js)
            implementation(libs.simbot.common.annotations)
        }

        nativeMain.dependencies {
            implementation(libs.simbot.common.annotations)
        }

        mingwTest.dependencies {
            implementation(libs.ktor.client.winhttp)
        }
    }

}

dependencies {
    add("kspCommonMainMetadata", project(":internal-processors:update-events-processor"))
}

// see https://github.com/google/ksp/issues/567#issuecomment-1510477456
tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}

tasks.withType<DokkaTaskPartial>().configureEach {
    dokkaSourceSets.configureEach {
        suppressGeneratedFiles.set(false)
    }
}
//
// ksp {
//     arg("qg.api.reader.enable", (!isCi).toString())
//     arg("qg.api.finder.api.output", rootDir.resolve("generated-docs/api-list.md").absolutePath)
//     arg("qg.api.finder.event.output", rootDir.resolve("generated-docs/event-list.md").absolutePath)
// }

data class SupportListItem(val depth: Int, val name: String, val link: String, val mark: Boolean? = null)

tasks.create("updateSupportListsDoc") {
    group = "documentation"

    val apiDir = "src/commonMain/kotlin/love/forte/simbot/telegram/api"
    val apiFile = project.file(apiDir)

    val builder = StringBuilder()
    builder.appendLine("# Supported API List\n")

    val list = mutableListOf<SupportListItem>()

    project.fileTree(apiFile) {
        include("**/*Api.kt")
        exclude("utils/**")
    }.visit(object : FileVisitor {
        override fun visitDir(dirDetails: FileVisitDetails) {
            list.add(
                SupportListItem(
                    depth = dirDetails.relativePath.segments.size - 1,
                    name = dirDetails.name,
                    link = apiDir + "/" + dirDetails.relativePath.pathString,
                    mark = null,
                )
            )
        }

        override fun visitFile(fileDetails: FileVisitDetails) {
            val name = fileDetails.name
                .substringBeforeLast(".kt")

            list.add(
                SupportListItem(
                    depth = fileDetails.relativePath.segments.size - 1,
                    name = name,
                    link = apiDir + "/" + fileDetails.relativePath.pathString,
                    mark = true,
                )
            )
        }
    })

    list.forEach { item ->
        repeat(item.depth) {
            builder.append("  ")
        }
        builder.append("- ")
        item.mark?.also {
            if (it) {
                builder.append(" [x] ")
            } else {
                builder.append(" [ ] ")
            }
        }
        builder.append("[")
            .append(item.name)
            .append("](")
            .append(item.link)
            .append(")\n")
    }

    builder.appendLine("- [ ] **Others not listed**")

    with(project.file("supports.md")) {
        toPath().writeText(
            builder, Charsets.UTF_8,
            StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.CREATE
        )
    }
}
