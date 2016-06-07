#### Introduction
The team's current preferred IDE is [Intellij IDEA](https://www.jetbrains.com/idea/).
Please note that the project contains quite a few batch scripts to make life easier when you are using Windows to develop on,
but are not at all necessary. These scripts are not very complex and you can easily execute their steps/commands by hand when
developing on a different OS

#### Setting up your environment
1. You should define a Intellij path variable called `SPIGOT_DEVSERVER` and set it's value to the server root of your
Spigot development server.
You can configure Intellij IDEA path variables at `File > Settings > Appearance & Behaviour > Path Variables`

2. You should also define an (system) environment variable, also called `SPIGOT_DEVSERVER` with the same value as the previous one.

3. Your server jar should be called `spigot.jar`

4. You should use the default directory structure for the Spigot server.

5. We use Lombok, when using Intellij IDEA you should install the "Lombok Plugin" for Intellij IDEA.