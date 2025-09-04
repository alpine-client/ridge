/*
 * This file is part of Ridge - https://github.com/alpine-client/ridge
 * Copyright (C) 2025 Crystal Development, LLC
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.external.javadoc.StandardJavadocDocletOptions
import org.gradle.kotlin.dsl.expand
import org.gradle.kotlin.dsl.getByType
import org.gradle.language.jvm.tasks.ProcessResources
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.text.trim

val Project.libs: LibrariesForLibs
    get() = rootProject.extensions.getByType()

fun Project.isRelease(): Boolean {
    return !project.version.toString().contains("-")
}

fun Javadoc.applyLinks(vararg links: String) {
    (options as StandardJavadocDocletOptions).apply {
        links(*links)
    }
}

fun ProcessResources.expandProperties(vararg files: String) {
    // The configuration-cache requires us to use temp variables.
    // Gradle recommends value providers, but those do not currently work with gradle.properties
    // see https://github.com/gradle/gradle/issues/23572
    val d = project.rootProject.description.toString()
    val v = project.rootProject.version.toString()
    filesMatching(files.toList()) {
        expand(
            "description" to d,
            "version" to v,
        )
    }
}

fun JavaCompile.addCompilerArgs() {
    options.compilerArgs.addAll(
        listOf(
            "-parameters",
            "-Xlint:-options",
            "-Xlint:deprecation",
            "-Xlint:unchecked"
        )
    )
}

fun Jar.includeLicenseFile() {
    from(project.rootProject.file("LICENSE")) {
        into("META-INF")
    }
}

fun Jar.configureManifest() {
    manifest {
        attributes(
            mapOf(
                "Manifest-Version" to "1.0",
                "Created-By" to "Gradle",
                "Built-JDK" to "${System.getProperty("java.version")} (${System.getProperty("java.vendor")})",
                "Built-By" to System.getProperty("user.name"),
                "Built-Date" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()),
                "Implementation-Title" to project.rootProject.name,
                "Implementation-Version" to project.rootProject.version,
                "Implementation-Vendor" to "Crystal Development, LLC.",
                "Specification-Title" to project.rootProject.name,
                "Specification-Version" to project.rootProject.version,
                "Specification-Vendor" to "Crystal Development, LLC.",
                "License" to "MPL-2.0",
                "License-URL" to "https://mozilla.org/MPL/2.0/"
            )
        )
    }
}