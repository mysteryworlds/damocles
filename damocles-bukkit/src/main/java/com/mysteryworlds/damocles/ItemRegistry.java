package com.mysteryworlds.damocles;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class ItemRegistry {
  private final Map<UUID, DamoclesItem> items = Maps.newHashMap();

  public Optional<DamoclesItem> find(UUID itemId) {
    Preconditions.checkNotNull(itemId);
    return Optional.ofNullable(items.get(itemId));
  }

  public Optional<DamoclesItem> findByItemStack(ItemStack itemStack) {
    Preconditions.checkNotNull(itemStack);
    return extractItemId(itemStack).map(items::get);
  }

  public List<DamoclesItem> findAll() {
    return List.copyOf(items.values());
  }

  private Optional<UUID> extractItemId(ItemStack itemStack) {
    var itemMeta = itemStack.getItemMeta();
    return Optional.ofNullable(itemMeta)
      .map(ItemMeta::getPersistentDataContainer)
      .map(dataContainer -> dataContainer.get(DamoclesItem.KEY_ID, PersistentDataType.STRING))
      .map(UUID::fromString);
  }

  public void register(DamoclesItem item) {
    items.put(item.id(), item);
  }
}
