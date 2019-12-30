package com.mysteryworlds.damocles;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.UUID;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class DamoclesItem {
  static final NamespacedKey KEY_ID = NamespacedKey.minecraft("damocles_item_id");
  private static final NamespacedKey KEY_RARITY = NamespacedKey.minecraft("damocles_item_rarity");
  private static final NamespacedKey KEY_DESCRIPTION = NamespacedKey.minecraft("damocles_item_description");

  private final UUID id;
  private final String displayName;
  private final ItemStack itemStack;
  private final String description;
  private final ItemRarity rarity;
  private final DamoclesItemBehaviour behaviour;

  private DamoclesItem(
    UUID id,
    String displayName,
    ItemStack itemStack,
    String description,
    ItemRarity rarity,
    DamoclesItemBehaviour behaviour
  ) {
    this.id = id;
    this.displayName = displayName;
    this.itemStack = itemStack;
    this.description = description;
    this.rarity = rarity;
    this.behaviour = behaviour;
  }

  public static DamoclesItem create(
    UUID id,
    String displayName,
    ItemStack itemStack,
    String description,
    ItemRarity rarity,
    DamoclesItemBehaviour behaviour
  ) {
    Preconditions.checkNotNull(id);
    Preconditions.checkNotNull(displayName);
    Preconditions.checkNotNull(itemStack);
    Preconditions.checkNotNull(description);
    Preconditions.checkNotNull(rarity);
    Preconditions.checkNotNull(behaviour);
    return new DamoclesItem(
      id,
      displayName,
      itemStack.clone(),
      description,
      rarity,
      behaviour
    );
  }


  public ItemStack asItemStack() {
    var itemStack = this.itemStack.clone();
    var itemMeta = setupItemMeta(itemStack);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  private ItemMeta setupItemMeta(ItemStack itemStack) {
    var itemMeta = itemStack.getItemMeta();
    setupPersistentData(itemMeta);
    itemMeta.setUnbreakable(true);
    itemMeta.setLore(List.of(description));
    itemMeta.setDisplayName(displayName);
    return itemMeta;
  }

  private void setupPersistentData(ItemMeta itemMeta) {
    var dataContainer = itemMeta.getPersistentDataContainer();
    dataContainer.set(KEY_ID, PersistentDataType.STRING, id.toString());
    dataContainer.set(KEY_DESCRIPTION, PersistentDataType.STRING, description);
    dataContainer.set(KEY_RARITY, PersistentDataType.STRING, rarity.name());
  }

  public ItemRarity rarity() {
    return rarity;
  }

  public String description() {
    return description;
  }

  public UUID id() {
    return id;
  }

  public void triggerBehaviour(PlayerInteractEvent interaction) {
    Preconditions.checkNotNull(interaction);
    behaviour.interact(interaction);
  }
}
