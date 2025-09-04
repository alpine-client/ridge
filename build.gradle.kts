plugins {
    id("ridge.shadow-conventions")
}

subprojects {
    apply {
        plugin("ridge.base-conventions")
        plugin("ridge.spotless-conventions")
    }
}

dependencies {
    implementation(projects.ridgeApi)
    implementation(projects.ridgeCommon)
}