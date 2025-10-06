package com.surfnetwork.survival;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class NPCClickListener implements Listener {
    private final Main plugin;
    public NPCClickListener(Main plugin) { this.plugin = plugin; }

    @EventHandler
    public void onEntityClick(PlayerInteractAtEntityEvent e) {
        Entity ent = e.getRightClicked();
        if (ent.getCustomName() == null) return;
        if (ent.getCustomName().equalsIgnoreCase("Survival NPC")) {
            Player p = e.getPlayer();
            if (plugin.getServer().getWorld("SurvivalWorld") != null) {
                Location spawn = plugin.getServer().getWorld("SurvivalWorld").getSpawnLocation();
                p.teleport(spawn);
                p.sendMessage("Teleported to SurvivalWorld.");
            } else {
                p.sendMessage("Survival world not found.");
            }
        }
    }
}
