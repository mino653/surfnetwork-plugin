package com.surfnetwork.survival;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {
    private final Main plugin;
    public SetHomeCommand(Main plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (args.length != 1) { p.sendMessage("Usage: /sethome <name>"); return true; }
        String name = args[0];
        if (plugin.getData().getHomeCount(p) >= 3 && plugin.getData().getHome(p, name) == null) {
            p.sendMessage("You have reached the maximum number of homes (3)."); return true;
        }
        plugin.getData().setHome(p, name, p.getWorld().getName(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
        p.sendMessage("Home '" + name + "' set.");
        return true;
    }
}
