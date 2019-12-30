package com.mysteryworlds.damocles;

import org.bukkit.plugin.java.JavaPlugin;

public final class Damocles extends JavaPlugin {
  @Override
  public void onEnable() {
    registerCommands();
  }

  private void registerCommands() {
    var damoclesCommand = DamoclesCommand.create();
    var damoclesPluginCommand = getCommand("damocles");
    damoclesPluginCommand.setExecutor(damoclesCommand);
    damoclesPluginCommand.setTabCompleter(damoclesCommand);
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }
}
