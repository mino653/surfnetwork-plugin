package com.surfnetwork.survival;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrustCommand implements CommandExecutor {
    private final Main plugin;
    public TrustCommand(Main plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (args.length != 1) { p.sendMessage("Usage: /trust <player>"); return true; }
        Player target = p.getServer().getPlayer(args[0]);
        if (target == null) { p.sendMessage("Player not online."); return true; }
        Chunk c = p.getLocation().getChunk();
        String key = c.getWorld().getName() + "_" + c.getX() + "_" + c.getZ();
        if (!plugin.getData().isClaimed(key)) { p.sendMessage("This chunk is not claimed."); return true; }
        if (!plugin.getData().getClaimOwner(key).equals(p.getUniqueId().toString())) { p.sendMessage("You do not own this claim."); return true; }
        plugin.getData().addTrust(key, target.getUniqueId().toString());
        p.sendMessage("Trusted " + target.getName() + " in this chunk.");
        return true;
    }
}
