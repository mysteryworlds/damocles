package com.mysteryworlds.damocles;

import com.google.common.base.Preconditions;
import java.util.UUID;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public final class DamoclesItem {
  private static final NamespacedKey KEY_ID = NamespacedKey.minecraft("damocles_item_id");
  private static final NamespacedKey KEY_RARITY = NamespacedKey.minecraft("damocles_item_rarity");
  private static final NamespacedKey KEY_DESCRIPTION = NamespacedKey.minecraft("damocles_item_description");

  private final UUID id;
  private final ItemStack itemStack;
  private final String description;
  private final ItemRarity rarity;

  private DamoclesItem(
    UUID id,
    ItemStack itemStack,
    String description,
    ItemRarity rarity
  ) {
    this.id = id;
    this.itemStack = itemStack;
    this.description = description;
    this.rarity = rarity;
  }

  public static DamoclesItem fromItemStack(ItemStack itemStack) {
    Preconditions.checkNotNull(itemStack);
    var itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) {
      throw new IllegalArgumentException("Argument item stack is not a damocles item");
    }
    var tagContainer = itemMeta.getPersistentDataContainer();
    var itemId = tagContainer.get(KEY_ID, PersistentDataType.STRING);
    var itemDescription = tagContainer.get(KEY_DESCRIPTION, PersistentDataType.STRING);
    var itemRarity = tagContainer.get(KEY_RARITY, PersistentDataType.STRING);
    return new DamoclesItem(UUID.fromString(itemId), itemStack.clone(), itemDescription, ItemRarity.valueOf(itemRarity));
  }

  public static DamoclesItem create(
    UUID id,
    ItemStack itemStack,
    String description,
    ItemRarity rarity
  ) {
    Preconditions.checkNotNull(id);
    Preconditions.checkNotNull(itemStack);
    Preconditions.checkNotNull(description);
    Preconditions.checkNotNull(rarity);
    return new DamoclesItem(id, itemStack.clone(), description, rarity);
  }

  public ItemRarity rarity() {
    return rarity;
  }

  public String description() {
    return description;
  }

  public ItemStack itemStack() {
    var itemStack = this.itemStack.clone();
    var itemMeta = itemStack.getItemMeta();
    var dataContainer = itemMeta.getPersistentDataContainer();
    dataContainer.set(KEY_ID, PersistentDataType.STRING, id.toString());
    dataContainer.set(KEY_DESCRIPTION, PersistentDataType.STRING, description);
    dataContainer.set(KEY_RARITY, PersistentDataType.STRING, rarity.name());
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  public UUID id() {
    return id;
  }
}
