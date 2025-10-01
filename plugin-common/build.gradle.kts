import org.gradle.kotlin.dsl.support.uppercaseFirstChar

plugins {
    id("ridge.blossom-conventions")
}

dependencies {
    compileOnly(projects.ridgeApi)
    compileOnly(libs.spigotApi) {
        exclude("junit")
        exclude("org.yaml", "snakeyaml")
    }
    compileOnly(libs.log4j)

    implementation(platform(libs.adventure.bom))
    implementation(libs.adventure.platform.bukkit)
    implementation(libs.adventure.api)
    implementation(libs.adventure.text.minimessage)
    implementation(libs.adventure.text.serializer.legacy)
    implementation(libs.configlib.spigot)
    implementation(libs.litecommands.bukkit)
    implementation(libs.litecommands.adventure.platform)
    implementation(libs.msgpack)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("id", rootProject.name)
                property("name", rootProject.name.uppercaseFirstChar())
                property("version", project.version.toString())
            }
        }
    }
}

tasks {
    withType<ProcessResources>().configureEach {
        expandProperties("plugin.yml")
    }
}