# CustomRankJoinMessages PowerNukkitX

A PowerNukkitX plugin that allows server administrators to set custom join messages for players based on their rank. Create a personalized experience for players when they join your server!


## Features

- **Custom Join Messages**: Configure different join messages based on player rank.
- **Flexible Configurations**: Easily customizable settings via a YAML configuration file.
- **Support for Multiple Ranks**: Define custom messages for various ranks on your server.
- **Easy Installation**: Simply add the plugin to your server and configure it.

## Configuration

The plugin uses a simple YAML configuration to define join messages based on player rank. Example configuration:

```yaml
messages:
  default: "Welcome to the server, {player}!"
  member: "Hello, {player}, enjoy your stay!"
  admin: "Welcome back, {player}! You have admin rights."
  vip: "Welcome VIP {player}, have fun!"
```
