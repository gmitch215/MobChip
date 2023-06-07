rootProject.name = "mobchip-parent"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

listOf("base", "abstraction", "bukkit").forEach {
    include(":mobchip-$it")
    project(":mobchip-$it").projectDir = rootDir.resolve(it)
}

listOf(
    "1_13_R1",
    "1_13_R2",
    "1_14_R1",
    "1_15_R1",
    "1_16_R1",
    "1_16_R2",
    "1_16_R3",
    "1_17_R1",
    "1_18_R1",
    "1_18_R2",
    "1_19_R1",
    "1_19_R2",
    "1_19_R3",
    "1_20_R1",
).forEach {
    include(":mobchip-$it")
    project(":mobchip-$it").projectDir = rootDir.resolve("nms/$it")
}