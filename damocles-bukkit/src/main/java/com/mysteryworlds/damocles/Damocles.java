package com.mysteryworlds.damocles;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Damocles extends JavaPlugin {
  private DamoclesItemRegistry itemRegistry;

  @Override
  public void onEnable() {
    itemRegistry = new DamoclesItemRegistry();
    registerCommands();
    registerListeners();
    loadItems();
  }

  private void loadItems() {
    var item = DamoclesItem.create(
      UUID.randomUUID(),
      "Lucifers stick",
      new ItemStack(Material.WOODEN_SWORD),
      "A mystical weapon to bend the knee",
      ItemRarity.MYTHICAL,
      ExplosionItemBehaviour.create()
    );
    itemRegistry.register(item);
  }

  private void registerListeners() {
    var behaviourTrigger = new ItemBehaviourTrigger(itemRegistry);
    Bukkit.getPluginManager().registerEvents(behaviourTrigger, this);
  }

  private void registerCommands() {
    var damoclesCommand = new DamoclesCommand(itemRegistry);
    var damoclesPluginCommand = getCommand("damocles");
    damoclesPluginCommand.setExecutor(damoclesCommand);
    damoclesPluginCommand.setTabCompleter(damoclesCommand);
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }
}
