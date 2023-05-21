val mcVersion = "1.13.2"

dependencies {
    api(project(":mobchip-base"))
    api(project(":mobchip-abstraction"))

    compileOnly("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
    testImplementation("org.spigotmc:spigot:$mcVersion-R0.1-SNAPSHOT")
}