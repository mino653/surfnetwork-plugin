package com.surfnetwork.survival;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinListener implements Listener {
    private final Main plugin;
    public JoinListener(Main plugin) { this.plugin = plugin; }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.getWorld().getName().equalsIgnoreCase("SurvivalWorld")) {
                    p.sendTitle(ChatColor.GREEN + "Welcome to Survival!", ChatColor.GRAY + "Explore, build, and grow your world!", 10, 70, 20);
                }
            }
        }.runTaskLater(plugin, 20L);
    }
}
