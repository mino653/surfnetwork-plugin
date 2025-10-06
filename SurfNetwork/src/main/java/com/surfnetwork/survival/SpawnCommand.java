package com.surfnetwork.survival;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnCommand implements CommandExecutor {
    private final Main plugin;
    public SpawnCommand(Main plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        Location spawn = plugin.getServer().getWorlds().get(0).getSpawnLocation();
        p.sendMessage("Teleporting to spawn in 3 seconds...");
        new BukkitRunnable() {
            @Override
            public void run() { p.teleport(spawn); p.sendMessage("Teleported to spawn."); }
        }.runTaskLater(plugin, 60L);
        return true;
    }
}
