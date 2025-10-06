package com.surfnetwork.survival;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class TopCommand implements CommandExecutor {
    private final Main plugin;
    public TopCommand(Main plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        List<Player> players = new ArrayList<>(plugin.getServer().getOnlinePlayers());
        players.sort(Comparator.comparingDouble(p -> -plugin.getEconomy().getBalance(p)));
        sender.sendMessage("----[ Richest Players ]----");
        int i = 1;
        for (Player p : players) {
            if (i > 10) break;
            sender.sendMessage("#" + i + " " + p.getName() + " - $" + String.format("%.2f", plugin.getEconomy().getBalance(p)));
            i++;
        }
        return true;
    }
}
