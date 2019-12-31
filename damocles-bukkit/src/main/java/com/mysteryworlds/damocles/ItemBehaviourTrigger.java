package com.mysteryworlds.damocles;

import com.google.common.base.Preconditions;
import java.util.Optional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public final class ItemBehaviourTrigger implements Listener {
  private final ItemRegistry itemRegistry;

  public ItemBehaviourTrigger(ItemRegistry itemRegistry) {
    this.itemRegistry = itemRegistry;
  }

  @EventHandler
  public void interactWithEntity(PlayerInteractEvent interaction) {
    Preconditions.checkNotNull(interaction);
    var itemStack = interaction.getItem();
    if (itemStack == null) {
      return;
    }
    Optional<DamoclesItem> item = itemRegistry.findByItemStack(itemStack);
    item.ifPresent(damoclesItem -> damoclesItem.triggerBehaviour(interaction));
  }
}
