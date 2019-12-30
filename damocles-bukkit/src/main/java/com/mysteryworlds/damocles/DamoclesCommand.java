package com.mysteryworlds.damocles;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DamoclesCommand implements CommandExecutor, TabCompleter {
  private DamoclesCommand() {
  }

  public static DamoclesCommand create() {
    return new DamoclesCommand();
  }

  @Override
  public boolean onCommand(
    CommandSender sender,
    Command command,
    String label,
    String[] args
  ) {
    if (args.length == 1) {
      if (args[0].equalsIgnoreCase("info")) {
        return onCommandInfo(sender, command);
      } else if (args[0].equalsIgnoreCase("give")) {
        return onCommandGive(sender, command);
      }
    }
    return false;
  }

  private boolean onCommandInfo(CommandSender sender, Command command) {
    if (!(sender instanceof Player)) {
      return false;
    }
    var player = (Player) sender;
    var itemInHand = player.getItemInHand();
    var damoclesItem = DamoclesItem.fromItemStack(itemInHand);
    sender.sendMessage("You got the " + damoclesItem.id() + " (" + damoclesItem.rarity() + "): " + damoclesItem.description());
    return true;
  }

  private boolean onCommandGive(CommandSender sender, Command command) {
    if (!(sender instanceof Player)) {
      return false;
    }
    var item = DamoclesItem.create(
      UUID.randomUUID(),
      new ItemStack(Material.WOODEN_SWORD),
      "A mystical weapon to bend the knee",
      ItemRarity.MYTHICAL
    );
    var player = (Player) sender;
    player.getInventory().addItem(item.itemStack());
    return true;
  }

  @Override
  public List<String> onTabComplete(
    CommandSender sender,
    Command command,
    String alias,
    String[] args
  ) {
    return Lists.newArrayList();
  }
}
