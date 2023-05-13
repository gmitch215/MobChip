dependencies {
    compileOnly("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT") {
        version {
            strictly("1.13-R0.1-SNAPSHOT")
        }
    }

    // API

    api(project(":mobchip-base"))
    api(project(":mobchip-abstraction"))

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
        "1_19_R3"
    ).forEach {
        api(project(":mobchip-$it"))
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

sourceSets["main"].allJava.srcDir("src/main/javadoc")

tasks {
    javadoc {
        enabled = true

        options {
            require(this is StandardJavadocDocletOptions)

            links("https://hub.spigotmc.org/javadocs/spigot/")
            links("https://javadoc.io/doc/org.jetbrains/annotations-java5/23.0.0/")
        }
    }
}