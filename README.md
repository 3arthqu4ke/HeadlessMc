<!--suppress HtmlDeprecatedAttribute -->
<h1 align="center" style="font-weight: normal;"><b>HeadlessMc</b></h1>
<p align="center">A command line launcher for Minecraft Java Edition.</p>
<p align="center"><img src="headlessmc-web/page/logo.png" alt="logo" style="width:200px;"></p>
<p align="center"><a href="https://github.com/3arthqu4ke/mc-runtime-test">Mc-Runtime-Test</a> | HMC | <a href="https://github.com/3arthqu4ke/hmc-specifics">HMC-Specifics</a> | <a href="https://github.com/3arthqu4ke/hmc-optimizations">HMC-Optimizations</a></p>

<div align="center">

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/6a86b3e62d3b47909de670b09737f8fd)](https://app.codacy.com/gh/3arthqu4ke/headlessmc/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![GitHub All Releases](https://img.shields.io/github/downloads/3arthqu4ke/HeadlessMc/total.svg)](https://github.com/3arthqu4ke/HeadlessMc/releases)
![](https://github.com/3arthqu4ke/HeadlessMc/actions/workflows/gradle-publish.yml/badge.svg)
![GitHub](https://img.shields.io/github/license/3arthqu4ke/HeadlessMc)
[![Docker Image Size](https://badgen.net/docker/size/3arthqu4ke/headlessmc?icon=docker&label=image%20size)](https://hub.docker.com/r/3arthqu4ke/headlessmc/)
![Github last-commit](https://img.shields.io/github/last-commit/3arthqu4ke/HeadlessMc)

</div>

> [!WARNING]
> NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.
> 
> HeadlessMc will not allow you to play without having bought Minecraft! 
> Accounts will always be validated.
> Offline accounts can only be used to run the game headlessly in CI/CD pipelines.

HeadlessMc (HMC) allows you to launch Minecraft Java Edition from the command line.
It can also modify the game while and before running it.
This feature can be used to make the Minecraft client run in headless mode, 
without displaying a UI, controlled by the command line.
HeadlessMc also patches the Log4J vulnerability 
and can be used
to test the game in your CI/CD pipeline with [mc-runtime-test](https://github.com/3arthqu4ke/mc-runtime-test).

## How to use

1. Download the `headlessmc-launcher.jar` from the releases tab and install a Java version &geq; 8.
    - If you want additional features such as plugins and launching the game inside the same JVM, use the `headlessmc-launcher-wrapper.jar` instead.
2. Run the launcher with `java -jar headlessmc-launcher.jar` in your terminal. 
You can also use the `hmc.sh/bat` scripts for convenience.
3. You need to specify which Java installations HeadlessMc can use to run the game.
Open the file `HeadlessMC/config.properties` and add a key called `hmc.java.versions`, 
with a `;` seperated list of java versions HeadlessMc can use, like this:
    ```properties
    hmc.java.versions=C:/Program Files/Java/jre-<version>/bin/java;C:/Program Files/Java/jdk-<version>/bin/java
    ```
    On Windows Java versions in `Program Files/Java` will already get detected automatically.
4. Execute `config -refresh` and then `java -refresh`, HeadlessMc should now know which Java versions to use.
5. HeadlessMc will generally not allow you to start the game without an account.
Login to your Minecraft account by executing the `login` command and follow the instructions.
6. You can download Minecraft Vanilla versions with the download command, e.g. `download 1.21`.
7. After downloading a Vanilla version you can also install modloaders
with the `forge`, `fabric`, and `neoforge` commands, e.g. `fabric 1.21`.
8. With `versions` you can list your downloaded Minecraft versions.
9. With `help` you can list other available commands.
10. If you are ready to launch the game execute `launch <version>`.
If you want to start the game in headless mode add the `-lwjgl` flag.

### HeadlessMc-Specifics

The [hmc-specifics](https://github.com/3arthqu4ke/hmc-specifics) are mods
that you can place inside your .minecraft/mods folder.
Together with HeadlessMc they allow you to control the game via the command line, e.g.
by sending chat messages and commands with `msg "<message>"`,
visualizing the menus displayed by Minecraft via `gui` and clicking through menus via `click`.

### Docker 

A preconfigured [docker image](https://hub.docker.com/r/3arthqu4ke/headlessmc/) exists,
which comes with Java 8, 17 and 21 installed.
Pull it via `docker pull 3arthqu4ke/headlessmc:latest`
and run it with `docker run -it 3arthqu4ke/headlessmc:latest`.
Inside the container you can use the `hmc` command anywhere.

### Android

HeadlessMc can run inside Termux.
* Download Termux from F-Droid, NOT from the PlayStore.
* Install Java: `apt update && apt upgrade $ apt install openjdk-<version>`
* Download the headlessmc-launcher-wrapper.jar into Termux.
* Disable JLine, as we could not get it to work on Termux for now,
  by adding `hmc.jline.enabled=false` to the HeadlessMC/config.properties.
* Now you can use HeadlessMc like you would on Desktop or Docker.

### Web

HeadlessMc can run inside the browser, kinda.
First, there is CheerpJ, a WebAssembly JVM,
but it does not support all features we need to launch the game.
The CheerpJ instance can be tried out [here](https://3arthqu4ke.github.io/headlessmc/).
Secondly, there is [container2wasm](https://github.com/headlesshq/hmc-container2wasm),
which can translate the HeadlessMc Docker container
to WebAssembly and the run it inside the browser, but this is extremely slow.

### Optimizations 

HeadlessMc achieves headless mode by patching the LWJGL library: 
every of its functions is rewritten to do nothing, or to return stub values
(you can read more about this [here](headlessmc-lwjgl/README.md)).
This has the advantage of being independent of Minecraft versions,
but comes with some overhead.
A Minecraft version dependent approach are the [hmc-optimizations](https://github.com/3arthqu4ke/hmc-optimizations),
another set of mods which patch Minecraft itself to skip all rendering code.
Additionally HeadlessMc also comes with the `hmc.assets.dummy` property,
which replaces all assets with small dummy textures and sounds,
which allows for a smaller memory footprint and much less downloads before launch.
You can also achieve headless mode without patching lwjgl by running headlessmc with a virtual framebuffer like
[Xvfb](https://www.x.org/releases/X11R7.6/doc/man/man1/Xvfb.1.xhtml).

### Configuring HeadlessMc

HeadlessMc can be configured using properties. These can be passed as SystemProperties from the command line or via the
`HeadlessMc/config.properties` file.
For available properties see the [HmcProperties](headlessmc-api/src/main/java/me/earth/headlessmc/api/config/HmcProperties.java), the
[LauncherProperties](headlessmc-launcher/src/main/java/me/earth/headlessmc/launcher/LauncherProperties.java), the
[JLineProperties](headlessmc-jline/src/main/java/me/earth/headlessmc/jline/JLineProperties.java), the
[LoggingProperties](headlessmc-logging/src/main/java/me/earth/headlessmc/logging/LoggingProperties.java), the
[RuntimeProperties](headlessmc-runtime/src/main/java/me/earth/headlessmc/runtime/RuntimeProperties.java) or the
[LwjglProperties](headlessmc-lwjgl/src/main/java/me/earth/headlessmc/lwjgl/LwjglProperties.java).

You can e.g. set `hmc.gamedir` to run the game inside another directory.

### A Note on command arguments

Arguments passed to commands have to be separated using spaces. If you want to pass an Argument which contains spaces
you need to escape it using quotation marks, like this:
`"argument with spaces"`.
Quotation marks and backslashes can be escaped by using a backslash.
So `msg "A text with spaces"` will send the chat message `A text with spaces`,
while `msg "\"A text with spaces\"" additional space`
will send the chat message `"A text with spaces"` and the argument `additional space` will be dropped.

## Building, Developing and Contributing

Simply run `./gradlew build` or import the [build.gradle](build.gradle)
into an IDE of your choice, and you should be good to go.

In order to keep compatibility with older Java and Minecraft versions
HeadlessMc uses Java language level 8. It can be
built with any JDK &geq; 8, but language features > 8 can't be used. 
HeadlessMc uses [project lombok](https://github.com/projectlombok/lombok)
to eliminate Java boilerplate.

The (sparse) javadoc can be found [here](https://3arthqu4ke.github.io/headlessmc/javadoc/).

Contributions are welcome!

### Plugins

You can also write Plugins for HeadlessMc.
Plugins can run through the `headlessmc-launcher-wrapper`,
which launches the `headlessmc-launcher` on another classloader.
You can find a small example [here](headlessmc-launcher-wrapper/src/testPlugin).

## License and Libraries

Some cool libraries we use:

*   [MinecraftAuth by RaphiMC](https://github.com/RaphiMC/MinecraftAuth)
*   [Deencapsulation by xxDark](https://github.com/xxDark/deencapsulation)
*   [Forge-CLI by TeamKun](https://github.com/TeamKun/ForgeCLI), which we [customized](https://github.com/3arthqu4ke/ForgeCLI).

HeadlessMc is licensed under the [MIT License](LICENSE).
