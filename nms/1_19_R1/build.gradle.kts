plugins {
    id("xyz.gmitch215.specialsource-gradle") version "1.0.0"
}

val mcVersion = "1.19.2"

dependencies {
    api(project(":mobchip-base"))
    api(project(":mobchip-abstraction"))
    api(project(":mobchip-1_14_R1"))

    compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT:remapped-mojang")
    testImplementation("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT:remapped-mojang")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    assemble {
        dependsOn("remap")
    }

    jar.configure {
        enabled = false
    }

    remap {
        dependsOn("shadowJar")

        inputTaskName.set("shadowJar")
        archiveFileName.set("${project.name}-${project.version}.jar")
    }
}