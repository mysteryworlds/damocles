package com.mysteryworlds.damocles;

import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemBehaviour {
  void interact(PlayerInteractEvent interaction);
}
