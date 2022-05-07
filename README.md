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

- **Small**: MobChip is currently below 200KB, and we will try to optimize and keep it as small as possible.
- **Simple**: MobChip has documentation, API usage, and other utilities to help ease the experience of working with Entity AI.
- **Flexible**: With normal NMS, you're often limited with what fields you are able to pick, and obfuscated methods make knowing what to pick even harder. Even with the addition of Mojang Mappings, there's still a lot of things that both new and experienced developers don't know. MobChip uses Reflection to help 
- **Transparent**: Most classes have a `getHandle` or a similar method to represent the NMS class, if you want to do some editing yourself. We also have ways of converting NMS Classes to MobChip Wrappers, for easy functionality.

## Installation
![GitHub branch checks state](https://img.shields.io/github/checks-status/GamerCoder215/MobChip/master)
[![](https://jitpack.io/v/GamerCoder215/MobChip.svg)](https://jitpack.io/#GamerCoder215/MobChip)
[![](https://jitci.com/gh/GamerCoder215/MobChip/svg)](https://jitci.com/gh/GamerCoder215/MobChip)

<details>
    <summary>Maven</summary>

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<!-- Base Module - Used for Base Classes -->

<dependencies>
    <dependency>
        <groupId>com.github.GamerCoder215.MobChip</groupId>
        <artifactId>mobchip-base</artifactId>
        <version>[VERSION]</version>
    </dependency>
    
    <!-- Bukkit Module - Used for Development & Events - Contains Base -->
    <dependency>
        <groupId>com.github.GamerCoder215.MobChip</groupId>
        <artifactId>mobchip-bukkit</artifactId>
        <version>[VERSION]</version> <!-- Example: 1.0.0 -->
    </dependency>
</dependencies>
```
</details>

<details>
    <summary>Gradle</summary>

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.GamerCoder215.MobChip:mobchip-base:[VERSION]'
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

@SuppressWarnings("ALL")
public class MyPlugin extends JavaPlugin {

    public Pathfinder getPathfinder(Mob m) {

        EntityBrain brain = BukkitBrain.getBrain(m);

        // Goal Selector
        EntityAI goal = brain.getGoalAI();

        // EntityAI Interface extends Map<Integer, Pathfinder>, so you can use map keywords
        // Pathfinders each have a priority, and will go through each one from least priority to most priority

        // Gets Pathfinder with the priority of 0

        Pathfinder p = goal.get(0);
        return p;

        // Gets Pathfinder with the priority of 2
        Pathfinder p2 = goal.get(2);
        return p2;

        // Gets Pathfinder from the Target Selector with the priority of 5
        Pathfinder p3 = brian.getTargetAI().get(5);
        return p3;
    }

}
```

#### Adding

Here's an example of how to add the Pathfinder that makes one entity avoid another entity (PathfinderAvoidEntity):

```java
import me.gamercoder215.mobchip.ai.goal.PathfinderAvoidEntity;
import me.gamercoder215.mobchip.ai.EntityAI;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.bukkit.BukkitBrain;

// Bukkit Imports...

@SuppressWarnings("ALL")
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
import me.gamercoder215.mobchip.ai.goal.CustomPathfinder;
import me.gamercoder215.mobchip.ai.goal.CustomPathfinder.PathfinderFlag;
import me.gamercoder215.mobchip.bukkit.BukkitBrain;

// Bukkit Imports...

@SuppressWarnings("ALL")
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

    // There's also canInterrupt(), canContinueToUse(), and updateEveryTick() boolean methods you can override.

    // It is not recommended to override updateEveryTick().

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

@SuppressWarnings("ALL")
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
A NavigationPath is a specific type of Path that contains many NavigationNodes, which are points of Locations that don't require a world.

Here's how to create a path:

```java
import me.gamercoder215.mobchip.bukkit.BukkitBrain;
import me.gamercoder215.mobchip.EntityBrain;
import me.gamercoder215.mobchip.ai.navigation.NavigationNode;
import me.gamercoder215.mobchip.ai.navigation.NavigationPath;
import me.gamercoder215.mobchip.ai.navigation.EntityNavigation;

@SuppressWarnings("ALL")
public class MyPlugin extends JavaPlugin {

    public NavigationPath createPath(Mob m) {
        EntityBrain brain = BukkitBrain.getBrain(m);

        // Creates a new EntityNavigation
        EntityNavigation navigation = brain.createNavigation();

        // Creates and adds a new Node at (100, 64, 128)
        NavigationNode point1 = new NavigationNode(100, 64, 128);
        navigation.addPoint(point1);

        // Creates and adds a new Node at (-162, 65, -278)
        NavigationNode point2 = new NavigationNode(-162, 65, -278);
        navigation.addPoint(point2);

        // Creates and adds the final Node at (-178, 77, -300)
        // Final Points need to be explicitly specified
        NavigationNode point3 = new NavigationNode(-178, 77, -300);
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

// Bukkit imports...

@SuppressWarnings("ALL")
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
