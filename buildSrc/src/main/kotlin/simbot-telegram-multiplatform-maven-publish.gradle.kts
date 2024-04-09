/*
 * Copyright (c) 2023-2024. ForteScarlet.
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

import love.forte.gradle.common.core.Gpg
import love.forte.gradle.common.core.project.setup
import love.forte.gradle.common.core.property.systemProp
import love.forte.gradle.common.publication.configure.multiplatformConfigPublishing

plugins {
    signing
    `maven-publish`
}

setup(P.ComponentTelegram)

val p = project
multiplatformConfigPublishing {
    project = P.ComponentTelegram
    isSnapshot = project.version.toString().contains("SNAPSHOT", true)

    // publishing {
    //     publications.withType<MavenPublication> {
    //         val dokkaJar = p.tasks.register("${name}DokkaJar", Jar::class) {
    //             group = JavaBasePlugin.DOCUMENTATION_GROUP
    //             description = "Assembles Kotlin docs with Dokka into a Javadoc jar"
    //             archiveClassifier.set("javadoc")
    //             from(tasks.named("dokkaHtml"))
    //
    //             // Each archive name should be distinct, to avoid implicit dependency issues.
    //             // We use the same format as the sources Jar tasks.
    //             // https://youtrack.jetbrains.com/issue/KT-46466
    //             archiveBaseName.set("${archiveBaseName.get()}-${name}")
    //         }
    //         artifact(dokkaJar)
    //     }
    // }

    val jarJavadoc by tasks.registering(Jar::class) {
        group = "documentation"
        archiveClassifier.set("javadoc")
        if (!(isSnapshot || isSnapshot() || isSimbotLocal())) {
            archiveClassifier.set("javadoc")
            from(tasks.findByName("dokkaHtml"))
        }
    }


    // val dokkaJar = p.tasks.register("${publication.name}DokkaJar", Jar::class) {
    //     group = JavaBasePlugin.DOCUMENTATION_GROUP
    //     description = "Assembles Kotlin docs with Dokka into a Javadoc jar"
    //     archiveClassifier.set("javadoc")
    //     from(tasks.named("dokkaHtml"))
    //
    //     // Each archive name should be distinct, to avoid implicit dependency issues.
    //     // We use the same format as the sources Jar tasks.
    //     // https://youtrack.jetbrains.com/issue/KT-46466
    //     archiveBaseName.set("${archiveBaseName.get()}-${publication.name}")
    // }

    artifact(jarJavadoc)
    releasesRepository = ReleaseRepository
    snapshotRepository = SnapshotRepository
    gpg = Gpg.ofSystemPropOrNull()

    if (isSimbotLocal()) {
        mainHost = null
    }

    publicationsFromMainHost += listOf("wasm", "wasm32", "wasm_js")
    mainHostSupportedTargets += listOf("wasm", "wasm32", "wasm_js")
}

// TODO see https://github.com/gradle-nexus/publish-plugin/issues/208#issuecomment-1465029831
// val signingTasks: TaskCollection<Sign> = tasks.withType<Sign>()
// tasks.withType<PublishToMavenRepository>().configureEach {
//     mustRunAfter(signingTasks)
// }

show()

fun show() {
    //// show project info
    logger.info(
        """
        |=======================================================
        |= project.group:       {}
        |= project.name:        {}
        |= project.version:     {}
        |= project.description: {}
        |= os.name:             {}
        |=======================================================
    """.trimIndent(),
        group, name, version, description, systemProp("os.name")
    )
}


inline val Project.sourceSets: SourceSetContainer
    get() = extensions.getByName("sourceSets") as SourceSetContainer

internal val TaskContainer.dokkaHtml: TaskProvider<org.jetbrains.dokka.gradle.DokkaTask>
    get() = named<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtml")
