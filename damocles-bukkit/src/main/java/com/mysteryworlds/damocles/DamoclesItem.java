package com.mysteryworlds.damocles;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class DamoclesItem {
  static final NamespacedKey KEY_ID = NamespacedKey.minecraft("damocles_item_id");
  private static final NamespacedKey KEY_RARITY = NamespacedKey.minecraft("damocles_item_rarity");
  private static final NamespacedKey KEY_DESCRIPTION = NamespacedKey.minecraft("damocles_item_description");
  private static final NamespacedKey KEY_ITEM_TYPE = NamespacedKey.minecraft("damocles_item_type");

  private final UUID id;
  private final String displayName;
  private final Material material;
  private final String description;
  private final ItemRarity rarity;
  private final ItemType type;
  private final ItemBehaviour behaviour;

  private DamoclesItem(
    UUID id,
    String displayName,
    Material material,
    String description,
    ItemRarity rarity,
    ItemType type,
    ItemBehaviour behaviour
  ) {
    this.id = id;
    this.displayName = displayName;
    this.material = material;
    this.description = description;
    this.rarity = rarity;
    this.type = type;
    this.behaviour = behaviour;
  }

  public ItemStack asItemStack() {
    var itemStack = createItemStack();
    var itemMeta = createItemMeta();
    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  private ItemStack createItemStack() {
    return new ItemStack(material);
  }

  private ItemMeta createItemMeta() {
    var itemMeta = Bukkit.getItemFactory().getItemMeta(material);
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
    dataContainer.set(KEY_ITEM_TYPE, PersistentDataType.STRING, type.name());
  }

  public UUID id() {
    return id;
  }

  public ItemRarity rarity() {
    return rarity;
  }

  public String description() {
    return description;
  }

  public ItemType type() {
    return type;
  }

  public String displayName() {
    return displayName;
  }

  public void triggerBehaviour(PlayerInteractEvent interaction) {
    Preconditions.checkNotNull(interaction);
    behaviour.interact(interaction);
  }

  public static Builder newBuilder() {
    return new Builder(UUID.randomUUID(), ItemRarity.COMMON);
  }

  public static final class Builder {
    private UUID id;
    private String displayName;
    private Material material;
    private String description;
    private ItemRarity rarity;
    private ItemType type;
    private ItemBehaviour behaviour;

    private Builder(UUID id, ItemRarity rarity) {
      this.id = id;
      this.rarity = rarity;
    }

    public Builder withId(UUID id) {
      Preconditions.checkNotNull(id);
      this.id = id;
      return this;
    }

    public Builder withDisplayName(String displayName) {
      Preconditions.checkNotNull(displayName);
      this.displayName = displayName;
      return this;
    }

    public Builder withMaterial(Material material) {
      Preconditions.checkNotNull(material);
      this.material = material;
      return this;
    }

    public Builder withDescription(String description) {
      Preconditions.checkNotNull(description);
      this.description = description;
      return this;
    }

    public Builder withRarity(ItemRarity rarity) {
      Preconditions.checkNotNull(rarity);
      this.rarity = rarity;
      return this;
    }

    public Builder withType(ItemType type) {
      Preconditions.checkNotNull(type);
      this.type = type;
      return this;
    }

    public Builder withBehavior(ItemBehaviour behavior) {
      Preconditions.checkNotNull(behavior);
      this.behaviour = behavior;
      return this;
    }

    public DamoclesItem create() {
      Preconditions.checkNotNull(id);
      Preconditions.checkNotNull(displayName);
      Preconditions.checkNotNull(material);
      Preconditions.checkNotNull(description);
      Preconditions.checkNotNull(rarity);
      Preconditions.checkNotNull(type);
      Preconditions.checkNotNull(behaviour);
      return new DamoclesItem(id, displayName, material, description, rarity, type, behaviour);
    }
  }
}
