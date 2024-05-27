# 🚚 MobChip
> Minecraft Entity AI and Bosses Library for 1.13 and above

## Background
<details>
    <summary>Click to Expand</summary>
    
MobChip is an all-in-one Entity AI and Bosses Library for Minecraft 1.13 and above. It allows you to easily implement Minecraft's native entity
AI into your own plugins for simple use.
</details>

## ❓ Why?

- **Simple**: MobChip has documentation, API usage, and other utilities to help ease the experience of working with Entity AI.
- **Flexible**: MobChip uses Reflection and Abstraction to help create flexibility, in order to ensure modern functionality on older versions. We also provide an easy-to-read API and JavaDocs to access important fields and methods. 
- **Compatibility**: MobChip uses Abstraction to create compatibility on multiple versions of Minecraft. We also provide a version checker to ensure that your plugin is running on a compatible version.
- **Transparent**: MobChip is completely open source.

## 🐘 Features

- Bosses Library 
- Native Entity AI Wrappers
  - Pathfinder Goals
  - Behaviors
  - Memories
  - Villager Gossip
  - Ender Dragon Phases
  - Tick Schedules
  - Sensors
- Native Entity Navigation & Controllers 
- Native Entity Animations
- Native Entity Combat Tracking
- Entity NBT Editor
- Custom Entity Attributes


## 📥 Installation
![GitHub](https://img.shields.io/github/license/GamerCoder215/MobChip)
[![GitHub branch checks state](https://github.com/GamerCoder215/MobChip/actions/workflows/build.yml/badge.svg)](https://github.com/GamerCoder215/MobChip/actions/workflows/build.yml)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/GamerCoder215/MobChip)
![GitHub issues](https://img.shields.io/github/issues/GamerCoder215/MobChip)
![Discord](https://img.shields.io/discord/972684412359680040?color=5865F2)

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
