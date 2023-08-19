# MobChip
> Minecraft Entity AI and Bosses Library

## Background
<details>
    <summary>Click to Expand</summary>
    
Accessing Entity AI has been widely thought and used over the life of SpigotMC. 
With many improvements, from Mojang Mappings to tutorials, there were many options. This library aims to help
improve the hassle of playing around with NMS and learning how to import and use it with a simple wrapper.
</details>

## Why?

- **Small**: MobChip is currently below 2MB, and we will try to optimize and keep it as small as possible.
- **Simple**: MobChip has documentation, API usage, and other utilities to help ease the experience of working with Entity AI.
- **Flexible**: With normal NMS, you're often limited with what fields you are able to pick, and obfuscated methods make knowing what to pick even harder. Even with the addition of Mojang Mappings, there's still a lot of things that both new and experienced developers don't know. MobChip uses Reflection and Abstraction to help solve this issue, as well as providing an easy-to-read API and JavaDocs to access important fields and methods. 
- **Transparent**: Instead of the getHandle() methods in previous versions, we have open wrappers that allow you to switch between MobChip and NMS for your MC Version. Depend on one of the wrapper modules according to your MC Version.

## Installation
![GitHub](https://img.shields.io/github/license/GamerCoder215/MobChip)
[![GitHub branch checks state](https://github.com/GamerCoder215/MobChip/actions/workflows/build.yml/badge.svg)](https://github.com/GamerCoder215/MobChip/actions/workflows/build.yml)
[![](https://jitpack.io/v/GamerCoder215/MobChip.svg)](https://jitpack.io/#GamerCoder215/MobChip)
[![](https://jitci.com/gh/GamerCoder215/MobChip/svg)](https://jitci.com/gh/GamerCoder215/MobChip)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/GamerCoder215/MobChip?style=plastic)

<details>
    <summary>Maven</summary>

```xml
<project>
    
    <!-- Import CodeMC Repo -->
    
    <repositories>
        <repository>
            <id>codemc-snapshots</id>
            <url>https://repo.codemc.io/repository/maven-snapshots/</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>me.gamercoder215</groupId>
            <artifactId>mobchip-bukkit</artifactId>
            <version>[VERSION]</version>

            <!-- Use something like 1.7.0-SNAPSHOT for a stable release -->
        </dependency>
    </dependencies>
    
</project>
```
</details>

<details>
    <summary>Gradle (Groovy)</summary>

```gradle
repositories {
    maven { url 'https://repo.codemc.io/repository/maven-snapshots/' }
}

dependencies {
    // Use something like 1.9.1-SNAPSHOT for a stable release
    implementation 'me.gamercoder215:mobchip-bukkit:[VERSION]'
}
```
</details>

<details>
    <summary>Gradle (Kotlin DSL)</summary>

```kotlin
repositories {
    maven(url = "https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    // Use something like 1.9.1-SNAPSHOT for a stable release
    implementation('me.gamercoder215:mobchip-bukkit:[VERSION]')
}
```
</details>
