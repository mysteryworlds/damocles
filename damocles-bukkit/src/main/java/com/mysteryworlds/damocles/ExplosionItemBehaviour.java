package com.mysteryworlds.damocles;

import org.bukkit.event.player.PlayerInteractEvent;

public final class ExplosionItemBehaviour implements ItemBehaviour {
  private ExplosionItemBehaviour() {
  }

  public static ExplosionItemBehaviour create() {
    return new ExplosionItemBehaviour();
  }

  @Override
  public void interact(PlayerInteractEvent interaction) {
    var clickedBlock = interaction.getClickedBlock();
    if (clickedBlock == null) {
      return;
    }
    var world = clickedBlock.getWorld();
    world.createExplosion(
      clickedBlock.getLocation(),
      2,
      true,
      true,
      interaction.getPlayer()
    );
  }
}
