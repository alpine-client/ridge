/*
 * This file is part of Ridge - https://github.com/alpine-client/ridge
 * Copyright (C) 2025 Crystal Development, LLC
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    id("ridge.base-conventions")
    id("com.gradleup.shadow")
}

tasks {
    named<Jar>("jar") {
        enabled = false
        archiveClassifier.set("unshaded")
    }
    named<ShadowJar>("shadowJar") {
        // https://gradleup.com/shadow/configuration/merging/#handling-duplicates-strategy
        duplicatesStrategy = DuplicatesStrategy.WARN
        failOnDuplicateEntries.set(true)
        archiveClassifier.set("")
        archiveFileName.set(archiveFileName.get().uppercaseFirstChar())

        configureRelocations()
        configureExclusions()
        mergeServiceFiles()
    }
    named("build") {
        dependsOn(named("shadowJar"))
    }
}

fun ShadowJar.configureRelocations() {
    val prefix = "com.alpineclient.ridge.libs"
    relocate("de.exlll.configlib", "$prefix.configlib")
    relocate("dev.rollczi.litecommands", "$prefix.litecommands")
    relocate("net.jodah.expiringmap", "$prefix.net.jodah.expiringmap")
    relocate("net.kyori.adventure", "$prefix.net.kyori.adventure")
    relocate("net.kyori.examination", "$prefix.net.kyori.examination")
    relocate("net.kyori.option", "$prefix.net.kyori.option")
    relocate("org.intellij.lang.annotations", "$prefix.org.intellij.lang.annotations")
    relocate("org.jetbrains.annotations", "$prefix.org.jetbrains.annotations")
    relocate("org.msgpack.core", "$prefix.org.msgpack.core")
    relocate("org.msgpack.value", "$prefix.org.msgpack.value")
    relocate("org.snakeyaml.engine", "$prefix.org.snakeyaml.engine")
    relocate("panda.std", "$prefix.panda.std")
}

fun ShadowJar.configureExclusions() {
    // Exclude plugin.yml file from configlib
    "plugin.yml".also {
        filesMatching(it) {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}