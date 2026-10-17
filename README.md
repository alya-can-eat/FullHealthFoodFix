# FullHealthFoodFix
CB1060 Plugin that prevents food from being eaten when health is full.

---

> [!WARNING]
> This plugin was vibecoded but seems to work well and the code looks fine.

---

Fixes food being wasted at full health in Minecraft Beta 1.7.3 on Project Poseidon servers.

In vanilla, right-clicking food always eats it, even when you already have full health. This plugin blocks eating as long as your health is full.

## Requirements

- Project Poseidon (or CB1060, which Poseidon is a fork of)
  - https://github.com/retromcorg/Project-Poseidon
- Java 8

Only tested with Project Poseidon. It should work on CraftBukkit 1060, but that is untested.

## Installation

1. Put `FullHealthFoodFix*.jar` into the `plugins/` folder. Get from here: https://github.com/alya-can-eat/FullHealthFoodFix/releases
2. Start the server. That's it, there are no files to set up.

## Building from source

Compile against the Project Poseidon server jar (Java 8 target):

```bash
javac --release 8 -cp poseidon.jar -d build src/ch/alya/foodfix/*.java
cp plugin.yml build/
jar cf FullHealthFoodFix.jar -C build .
```
