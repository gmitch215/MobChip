import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.13-R0.1-SNAPSHOT")
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks {
    javadoc {
        enabled = true

        options {
            require(this is StandardJavadocDocletOptions)

            links("https://hub.spigotmc.org/javadocs/spigot/")
            links("https://javadoc.io/doc/org.jetbrains/annotations-java5/23.0.0/")
        }
    }

    withType<ShadowJar> {
        dependsOn("sourcesJar", "javadocJar")
    }
}