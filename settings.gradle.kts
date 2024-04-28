rootProject.name = "mobchip-parent"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

listOf("base", "abstraction", "bukkit").forEach {
    include(":mobchip-$it")
    project(":mobchip-$it").projectDir = rootDir.resolve(it)
}

mapOf(
    "1_13_R1" to 8,
    "1_13_R2" to 8,
    "1_14_R1" to 8,
    "1_15_R1" to 8,
    "1_16_R1" to 8,
    "1_16_R2" to 8,
    "1_16_R3" to 8,
    "1_17_R1" to 16,
    "1_18_R1" to 17,
    "1_18_R2" to 17,
    "1_19_R1" to 17,
    "1_19_R2" to 17,
    "1_19_R3" to 17,
    "1_20_R1" to 17,
    "1_20_R2" to 17,
    "1_20_R3" to 17,
    "1_20_R4" to 21
).forEach {
    val id = it.key
    val minJava = it.value

    if (JavaVersion.current().isCompatibleWith(JavaVersion.toVersion(minJava))) {
        include(":mobchip-$id")
        project(":mobchip-$id").projectDir = rootDir.resolve("nms/$id")
    }
}