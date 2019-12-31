package com.mysteryworlds.damocles;

import com.google.common.collect.Lists;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public final class DamoclesCommand implements CommandExecutor, TabCompleter {
  private final ItemRegistry itemRegistry;

  public DamoclesCommand(
    ItemRegistry itemRegistry
  ) {
    this.itemRegistry = itemRegistry;
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
    var damoclesItem = itemRegistry.findByItemStack(itemInHand);
    damoclesItem.ifPresentOrElse(
      item -> sender.sendMessage("You got the " + item.id() + " (" + item
        .rarity() + "): " + item.description()),
      () -> sender.sendMessage("That's not a damocles item"));
    return true;
  }

  private boolean onCommandGive(CommandSender sender, Command command) {
    if (!(sender instanceof Player)) {
      return false;
    }
    var player = (Player) sender;
    var item = itemRegistry.findAll().get(0);
    player.getInventory().addItem(item.asItemStack());
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
