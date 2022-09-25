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
- **Flexible**: With normal NMS, you're often limited with what fields you are able to pick, and obfuscated methods make knowing what to pick even harder. Even with the addition of Mojang Mappings, there's still a lot of things that both new and experienced developers don't know. MobChip uses Reflection to help 
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
    
    <!-- Import JitPack Repo -->
    
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
            
            <!-- Use master-SNAPSHOT for Experimental Features on master branch -->
            
            <!-- Use something like 1.5.1-SNAPSHOT for a stable release -->
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
    implementation 'me.gamercoder215:mobchip-bukkit:[VERSION]'
    
    // Use "master-SNAPSHOT" as version for Experimental Features on master branch
    // Use something like "1.5.1-SNAPSHOT" as version for a stable release
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
    implementation('me.gamercoder215:mobchip-bukkit:[VERSION]')

    // Use "master-SNAPSHOT" as version for Experimental Features on master branch
    // Use something like "1.5.1-SNAPSHOT" as version for a stable release
}
```
</details>

## Usage


### Pathfinders

Pathfinders are how Mobs/Creatures do regular actions (Floating on water, Attacking, Avoiding Entities, etc). You can create your own (see below) for your own functionality, or use some of the built-in ones. 

#### Fetching

Here's how to fetch a Pathfinder from the Goal Selector:

```java
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import me.gamercoder215.mobchip.ai.goal.WrappedPathfinder;

// Bukkit Imports...

public class MyPlugin extends JavaPlugin {

    public Pathfinder getPathfinder(Mob m) {

        EntityBrain brain = BukkitBrain.getBrain(m);

        // Goal Selector
        EntityAI goal = brain.getGoalAI();

        // EntityAI Interface extends Set<WrappedPathfinder>, so you can use set keywords
        // Pathfinders each have a priority, and will go through each one from least priority to most priority
        // In the past we used to use Maps but then we allowed support for Pathfinders to have the same priority
        
        final Pathfinder pathfinder;
        
        for (WrappedPathfinder p : goal.stream().filter(p -> p.getPriority() == 1).collect(Collections.toSet())) {
            pathfinder = p.getPathfinder();
            break;
        }
        
        return pathfinder;
    }

}
```

#### Adding

Here's an example of how to add the Pathfinder that makes one entity avoid another entity (PathfinderAvoidEntity):

```java
import PathfinderAvoidEntity;
import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.bukkit.BukkitBrain;

// Bukkit Imports...

public class MyPlugin extends JavaPlugin {

    public void addGoal(Mob m) {
        EntityBrain brain = BukkitBrain.getBrain(m);

        // GoalSelector AI
        EntityAI goal = brain.getGoalAI();

        // TargetSelector AI
        EntityAI target = brain.getTargetAI();

        /* We're going to avoid... Creepers.
         * Pathfinders require passing the Entity Instance, and in this case, we need the Creeper class
         * 3rd Argument - Max Distance - Maximum Distnace needed to stop fleeing
         * 4th Argument - Speed Modifier - Speed Modifier while Sprinting/Walking away
         *
         * We'll say we need to be 10 blocks away from creepers, with a speed modifier of 2.
         */
        PathfinderAvoidEntity pathfinder = new PathfinderAvoidEntity<Creeper>(m, Creeper.class, 10, 2);

        // Either selector basically does the same, however it is generally common practice to add targeting goals
        // to the target selector so that other important goals (floating, breathing, etc) don't get overriden.

        // Has highest priority
        target.put(0, pathfinder);
    }

}
```

#### Custom Pathfinders
Say you want to have a custom pathfinder for your own Pet System, or a custom Entity Attack? MobChip contains a well-documented abstract class you can extend that shows you how to set it up.

```java
import CustomPathfinder;
import CustomPathfinder.PathfinderFlag;
import me.gamercoder215.mobchip.bukkit.BukkitBrain;

// Bukkit Imports...

public class MyPathfinder extends CustomPathfinder {

    public MyPathfinder(Mob m) {
        super(m); // Pass Entity to super
    }

    // PathfinderFlags are indicators of what your Pathfinder Does. In this case, it's
    // just going to move and jump the Entity.

    @Override
    public PathfinderFlag[] getFlags() {
        return new PathfinderFlag[]{PathfinderFlag.MOVEMENT, PathfinderFlag.JUMPING};
    }

    // This method will make sure your Pathfinder can start.

    // Entity & NMS Entity Parent Fields are available

    @Override
    public boolean canStart() {
        return entity.getLocation().add(0, 2, 0).getBlock().getType() == Material.AIR;
    }

    // Automatically called when canStart() returns true.
    // In this case, We're using the EntityController (more on that below) to control the entity's movements.

    @Override
    public void start() {
        BukkitBrain.getBrain(entity).getController().jump().moveTo(entity.getLocation().add(3, 0, 3));
    }

    // Automatically called when canStart() returns false.
    // We don't need to put down anything.

    @Override
    public void tick() {
        // do nothing
    }

    // There's also canInterrupt() and canContinueToUse() boolean methods you can override.

    // canContinueToUse() defaults to canUse(), but is useful if you have a different check after the first time canUse() is called.

}
```

### Controllers
Entity Controllers are a simple way of controlling an entity.

Here's an example:

```java
import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.controller.EntityController;

// Bukkit Imports...

public class MyPlugin extends JavaPlugin {

    public void doActions(Mob m) {
        EntityBrain brain = BukkitBrain.getBrain(m);

        EntityController controller = brain.getController();

        // EntityController has chaining methods, so you can call more than one.
        controller.jump().jump().moveTo(target);
    }

}
```

### Navigation
Navigation is a complex way of moving an entity.

#### Why use this over Controllers?
- Navigation uses multiple points (called "nodes") instead of having a chain of methods, which can be neater.
- Navigation focuses on simply movement and is optimized to do so.

#### When use this over Controllers?
- Large-Scale Entity Movement
- Pauses between Movement Points

#### Creating a Path
A NavigationPath is a specific type of Path that contains many Positions, which are points of Locations that don't require a world.

Here's how to create a path:

```java
import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.util.Position;
import me.gamercoder215.mobchip.ai.navigation.NavigationPath;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;

public class MyPlugin extends JavaPlugin {

    public NavigationPath createPath(Mob m) {
        EntityBrain brain = BukkitBrain.getBrain(m);

        // Creates a new EntityNavigation
        EntityNavigation navigation = brain.createNavigation();

        // Creates and adds a new Node at (100, 64, 128)
        Position point1 = new Position(100, 64, 128);
        navigation.addPoint(point1);

        // Creates and adds a new Node at (-162, 65, -278)
        Position point2 = new Position(-162, 65, -278);
        navigation.addPoint(point2);

        // Creates and adds the final Node at (-178, 77, -300)
        // Final Points need to be explicitly specified
        Position point3 = new Position(-178, 77, -300);
        navigation.setFinalPoint(point3);

        // Because we're going over large distances, we need to specify a large range
        navigation.setRange(500F);

        // Builds the final path containing all points
        NavigationPath path = navigation.buildPath();

        // Advances to the first point
        path.advance();

        // Could have a delay, some dialogue, or anything here...

        // Then advance to the next point
        path.advance();
    }

}

```

### Memories
Memories are small bits of information stored in the Entity's Brain. They are used to store things like angry at, nearest items, and more.

#### Getting & Setting Memories
Here's an example on how to get and set memories:

```java
import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.memories.EntityMemory;

// Bukkit Imports...

public class MyPlugin extends JavaPlugin {

    public void setMemories(Mob m) {
        EntityBrain brain = BukkitBrain.getBrain(m);

        // Fetch Memory Value of "HOME"
        Location home = brain.getMemory(EntityMemory.HOME); // EntityMemory types are paramaterized

        // Set Memory value of "HOME"
        brain.setMemory(EntityMemory.HOME, m.getLocation());

        // Set temporary memory value of "HOME" with expiration in ticks
        brain.setMemory(EntityMemory.HOME, m.getLocation(), 20 * 60 * 20); // Will expire in 20 minutes
    }

}


```

### Behaviors
Behaviors are actions that Entities can take based on pre-determined memories. 

Behaviors sometimes depend on some memories being present or absent for a different effect. 

These memories may change per-version, and documentation on what is required may be added in the future.

```java
import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.behavior.EntityBehavior;

// Bukkit Imports...

public class MyPlugin extends JavaPlugin {
    
    public void updateBehavior(Mob m) {
        EntityBrain brain = BukkitBrain.getBrain(m);
        
        EntityBehavior behavior = BukkitBrain.getBehaviors();
        
        // Subclasses are CreatureBehavior, VillagerBehavior, and WardenBehavior (1.19+) for those interfaces
        // Casting is required
        
        behavior.backupIfClose(3, 1); // Backs up if an entity is within 3 blocks with no speed modifier (1)
        
        // Creature c = ...
        brain = BukkitBrain.getBrain(c);
        CreatureBehavior cbehavior = (CreatureBehavior) brain.getBehaviors();
        
        cbehavior.panic(); // Makes the creature panic
        
    }
    
}

```

### Scheduling
Schedules are actions that an Entity Performs every Minecraft Day between 0 and 24,000 ticks.

```java
import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.scheduling.*;

public class MyPlugin extends JavaPlugin {
    
    public void updateSchedule(Mob m) {
        EntityBrain brain = BukkitBrain.getBrain(m);
        
        EntityScheduleManager manager = brain.getScheduleManager();
        manager.setDefaultActivity(Activity.IDLE); // Set the default Activity to IDLE
        
        Schedule simple = DefaultSchedules.SIMPLE; // Default Built-In Schedules are available in the bukkit module
        manager.setSchedule(simple);
        
        // Creates your own Custom Schedule
        Schedule custom = Schedule.builder()
                .addActivity(0, Activity.IDLE) // Idle at 0 ticks
                .addActivity(11000, Activity.PLAY) // Play at 11,000 ticks
                .addActivity(18000, Activity.REST) // Rest at 18,000 ticks
                .build();
        
        // Schedules will be executed at their specified tick and will repeat the action according to the manager until another activity is available on that tick
        manager.setSchedule(custom);
        
        // Add Actions for specified Activities
        manager.put(Activity.PLAY, en -> Bukkit.getLogger().info("Playing!")); // Activities run EVERY TICK until another is available
        
        // Schedules run automatically
        
        // Overrides the current running activity from the schedule
        manager.useDefaultActivity(); // Uses the default activity as the running activity
        manager.setRunningActivity(Activity.REST); // Sets the running activity to REST
        
    }
    
}
```

### NBT Editor
MobChip v1.5.0 introduces a new NBT Editor for editing an entity's NBT Data. The syntax is similar to a bukkit [ConfigurationSection](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/configuration/ConfigurationSection.html) rather than requiring wrappers for setting/fetching values.

#### Fetching and Comparing NBT Data
Fetching and Comparing NBT Data is similar to how you would fetch and compare values in a FileConfiguration. 
```java

import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.nbt.*;

public class MyPlugin extends JavaPlugin {

    public void fetchNBT(Mob m) {
        EntityBrain brain = BukkitBrain.getBrain(m);
        EntityNBT editor = brain.getNBTEditor();
        
        // Fetch the value of the "Health" tag
        double health = editor.getDouble("Health");
        
        // Fetch the value of the "CustomName" tag
        String customName = editor.getString("CustomName", "No Custom Name"); // Specify a default value
        
        // Supports multiple bukkit classes, including NamespacedKeys, Colors, UUIDs, Vectors, Locations, and more!
        NamespacedKey key = editor.getKey("MyEntityID");
        
        // Use this to create and fetch NBT Sections
        NBTSection section = editor.getOrCreateSection("Directions");
        
        // Supports child syntax, similar to ConfigurationSection
        Vector directions;
        if (editor.isVector("Directions.EntityDirections")) {
            directions = editor.getVector("Directions.EntityDirections");
        } else {
            directions = new Vector(0, 0, 0);
        }
        
        Location loc = directions.toLocation(m.getWorld());
    }

}


```

#### Setting NBT Data
Setting NBT Data is also similar to how you would do it in a ConfigurationSection. The NBT will automatically save to the Mob, so there is no need to save anything.
```java

import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.nbt.*;

public class MyPlugin extends JavaPlugin {

    public void editNBT(Mob m) {
        EntityBrain brain = BukkitBrain.getBrain(m);
        EntityNBT editor = brain.getNBTEditor();
        
        if (!editor.isLong("MyPlugin.ExistenceDuration")) {
            editor.set("MyPlugin.ExistenceDuration", 0L);
        }
        
        // Supports ConfigurationSerializable classes
        editor.set("MyObject", new MySerializableObject());
        ConfigurationSerializable serializable = editor.get("MyObject", MySerializableObject.class);
        
        // Serializes as an OfflinePlayer
        editor.set("MyPlugin.PlayerOwner", Bukkit.getPlayer("PlayerName"));
        OfflinePlayer owner = editor.getOfflinePlayer("MyPlugin.PlayerOwner");
    }
```