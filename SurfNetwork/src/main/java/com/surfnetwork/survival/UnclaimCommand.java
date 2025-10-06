package com.surfnetwork.survival;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnclaimCommand implements CommandExecutor {
    private final Main plugin;
    public UnclaimCommand(Main plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        Chunk c = p.getLocation().getChunk();
        String key = c.getWorld().getName() + "_" + c.getX() + "_" + c.getZ();
        if (!plugin.getData().isClaimed(key)) { p.sendMessage("This chunk is not claimed."); return true; }
        if (!plugin.getData().getClaimOwner(key).equals(p.getUniqueId().toString())) { p.sendMessage("You do not own this claim."); return true; }
        plugin.getData().unclaim(key);
        p.sendMessage("Claim removed.");
        return true;
    }
}
