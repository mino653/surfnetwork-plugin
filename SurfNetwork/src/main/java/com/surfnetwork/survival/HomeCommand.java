package com.surfnetwork.survival;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class HomeCommand implements CommandExecutor {
    private final Main plugin;
    public HomeCommand(Main plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (args.length != 1) { p.sendMessage("Usage: /home <name>"); return true; }
        String name = args[0];
        Map<String,Object> m = plugin.getData().getHome(p, name);
        if (m == null) { p.sendMessage("Home not found."); return true; }
        Location loc = new Location(Bukkit.getWorld((String)m.get("world")), (double)m.get("x"), (double)m.get("y"), (double)m.get("z"));
        loc.setYaw((float)m.get("yaw")); loc.setPitch((float)m.get("pitch"));
        p.teleport(loc);
        p.sendMessage("Teleported to home '" + name + "'.");
        return true;
    }
}
