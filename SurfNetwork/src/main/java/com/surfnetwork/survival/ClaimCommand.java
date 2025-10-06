package com.surfnetwork.survival;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimCommand implements CommandExecutor {
    private final Main plugin;
    public ClaimCommand(Main plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        Chunk c = p.getLocation().getChunk();
        String key = c.getWorld().getName() + "_" + c.getX() + "_" + c.getZ();
        if (plugin.getData().isClaimed(key)) { p.sendMessage("Chunk already claimed by " + plugin.getData().getClaimOwner(key)); return true; }
        plugin.getData().createClaim(key, p.getUniqueId().toString());
        p.sendMessage("You have claimed this chunk.");
        return true;
    }
}
