package com.surfnetwork.survival;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.entity.Player;

public class ClaimProtectListener implements Listener {
    private final Main plugin;
    public ClaimProtectListener(Main plugin) { this.plugin = plugin; }

    private boolean canPlayerInteract(Player p, org.bukkit.Location loc) {
        Chunk c = loc.getChunk();
        String key = loc.getWorld().getName() + "_" + c.getX() + "_" + c.getZ();
        if (!plugin.getData().isClaimed(key)) return true;
        String owner = plugin.getData().getClaimOwner(key);
        if (owner.equals(p.getUniqueId().toString())) return true;
        if (plugin.getData().getTrusted(key).contains(p.getUniqueId().toString())) return true;
        return false;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!canPlayerInteract(p, e.getBlock().getLocation())) {
            p.sendMessage("This area is claimed.");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        Player p = e.getPlayer();
        if (!canPlayerInteract(p, e.getClickedBlock().getLocation())) {
            p.sendMessage("This area is claimed.");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvOpen(InventoryOpenEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        Player p = (Player) e.getPlayer();
        org.bukkit.Location loc = e.getInventory().getLocation();
        if (loc == null) return;
        if (!canPlayerInteract(p, loc)) {
            p.sendMessage("This chest is in a claimed area.");
            e.setCancelled(true);
        }
    }
}
