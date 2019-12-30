package com.mysteryworlds.damocles;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class ItemBehaviourTrigger implements Listener {
  private final DamoclesItemRegistry itemRegistry;

  public ItemBehaviourTrigger(
    DamoclesItemRegistry itemRegistry
  ) {
    this.itemRegistry = itemRegistry;
  }

  @EventHandler
  public void interactWithEntity(PlayerInteractEvent interaction) {
    Preconditions.checkNotNull(interaction);
    var itemStack = interaction.getItem();
    if (itemStack == null) {
      return;
    }
    var item = itemRegistry.findByItemStack(itemStack);
    item.ifPresent(damoclesItem -> damoclesItem.triggerBehaviour(interaction));
  }
}
