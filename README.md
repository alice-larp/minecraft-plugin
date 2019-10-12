# Minecraft plugin for demos

## Requirements

* IntelliJ IDEA (tested with 2019.2.3 version).
* [Spigot](https://www.spigotmc.org/) v1.3.1.
* Minecraft client (v1.3.1).
* Web server accepting PUT requests at http://raspberrypi:1880/bulb/color.

## Building

* Open project in IntelliJ IDEA.
* In the "Maven" panel on the top right choose "package"

## Running
* Copy `target/AlicePlugin-1.0-SNAPSHOT.jar` to the Spigot's `plugins` folder and run the server.

## Behaviours

* On right-clicking block of wool, will send RGB color of the wool by `PUT` to `http://raspberrypi:1880/bulb/color` with body of
`{"rgb_color": [10, 200, 50]}` kind.
* On pulling any lever up, will `PUT` to `http://raspberrypi:1880/bulb/color` with body `{"rgb_color": [255, 0, 0]}`.
* On pulling any lever down, will `PUT` to `http://raspberrypi:1880/bulb/color` with body `{"rgb_color": [0, 255, 0]}`.
* On `POST` to `localhost:4567/command` with body `{"command": "<some minecraft command>"}` will execute it. Can be used to e.g. place
and remove blocks (e.g. `{"command": "setblock 39 16 204 minecraft:redstone_torch"}` and  
`{"command": "setblock 39 16 204 air"}`.
