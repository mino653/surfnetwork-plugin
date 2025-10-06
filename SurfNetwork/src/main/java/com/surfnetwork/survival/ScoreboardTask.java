package com.surfnetwork.survival;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardTask {
    private final Main plugin;
    public ScoreboardTask(Main plugin) { this.plugin = plugin; }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
                    Objective obj = sb.registerNewObjective("surf", "dummy", org.bukkit.ChatColor.GOLD + "SURFNETWORK");
                    obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                    obj.getScore("Name: " + p.getName()).setScore(5);
                    obj.getScore("Money: $" + String.format("%.2f", plugin.getEconomy().getBalance(p))).setScore(4);
                    obj.getScore("World: " + p.getWorld().getName()).setScore(3);
                    obj.getScore("Homes: " + plugin.getData().getHomeCount(p)).setScore(2);
                    obj.getScore("----------------").setScore(1);
                    obj.getScore("play.surfnetwork.xyz").setScore(0);
                    p.setScoreboard(sb);
                }
            }
        }.runTaskTimer(plugin, 0L, 100L);
    }
}
