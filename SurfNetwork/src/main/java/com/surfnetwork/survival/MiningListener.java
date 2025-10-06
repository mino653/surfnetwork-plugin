package com.surfnetwork.survival;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.entity.Player;

public class MiningListener implements Listener {
    private final Main plugin;
    public MiningListener(Main plugin) { this.plugin = plugin; }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Material m = e.getBlock().getType();
        double reward = 0;
        switch (m) {
            case DIAMOND_ORE: reward = 50; break;
            case GOLD_ORE: reward = 20; break;
            case IRON_ORE: reward = 5; break;
            default: break;
        }
        if (reward > 0) {
            plugin.getEconomy().depositPlayer(p, reward);
            p.sendMessage("You received $" + (int)reward + " for mining " + m.name() + ".");
        }
    }
}
