/*
 * This file is part of Ridge - https://github.com/alpine-client/ridge
 * Copyright (C) 2025 Crystal Development, LLC
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("ridge.base-conventions")
    id("com.gradleup.shadow")
}

components.named<AdhocComponentWithVariants>("java") {
    withVariantsFromConfiguration(configurations["shadowRuntimeElements"]) {
        skip() // do not publish shaded jar to maven
    }
}

tasks {
    named<Jar>("jar") {
        archiveClassifier.set("unshaded")
    }
    named<ShadowJar>("shadowJar") {
        // https://gradleup.com/shadow/configuration/merging/#handling-duplicates-strategy
        duplicatesStrategy = DuplicatesStrategy.WARN
        failOnDuplicateEntries.set(true)
        archiveClassifier.set("")

        enableAutoRelocation.set(true)
        relocationPrefix.set("com.alpineclient.ridge.libs")

        mergeServiceFiles()
        configureExclusions()
    }
    named("build") {
        dependsOn(named("shadowJar"))
    }
}

fun ShadowJar.configureExclusions() {
    // Exclude plugin.yml file from configlib
    "plugin.yml".also {
        filesMatching(it) {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}