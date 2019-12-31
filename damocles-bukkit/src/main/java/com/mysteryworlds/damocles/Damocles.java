package com.mysteryworlds.damocles;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

public final class Damocles extends JavaPlugin {
  private ItemRegistry itemRegistry;

  @Override
  public void onEnable() {
    itemRegistry = new ItemRegistry();
    registerCommands();
    registerListeners();
    loadItems();
  }

  private void loadItems() {
    var item = DamoclesItem.newBuilder()
      .withMaterial(Material.GOLDEN_SWORD)
      .withDescription("The sword of the old man Damocles")
      .withDisplayName("Damocle's sword")
      .withType(ItemType.WEAPON)
      .withRarity(ItemRarity.RARE)
      .withBehavior(ExplosionItemBehaviour.create())
      .addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(
        UUID.randomUUID(),
        "damage",
        2,
        Operation.ADD_SCALAR,
        EquipmentSlot.HAND
      ))
      .create();
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
