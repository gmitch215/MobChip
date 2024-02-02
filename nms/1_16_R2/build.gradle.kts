val mcVersion = "1.16.3"

dependencies {
    api(project(":mobchip-base"))
    api(project(":mobchip-abstraction"))
    api(project(":mobchip-1_14_R1"))

    compileOnly("org.spigotmc:spigot-api:$mcVersion-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
    testImplementation("org.spigotmc:spigot-api:$mcVersion-R0.1-SNAPSHOT")
    testImplementation("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
}