package com.surfnetwork.survival;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataManager {
    private final Main plugin;
    private File homesFile;
    private File claimsFile;
    private FileConfiguration homesCfg;
    private FileConfiguration claimsCfg;

    public DataManager(Main plugin) {
        this.plugin = plugin;
        homesFile = new File(plugin.getDataFolder(), "homes.yml");
        claimsFile = new File(plugin.getDataFolder(), "claims.yml");
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
    }

    public void load() {
        try {
            if (!homesFile.exists()) homesFile.createNewFile();
            if (!claimsFile.exists()) claimsFile.createNewFile();
        } catch (IOException ignored) {}
        homesCfg = YamlConfiguration.loadConfiguration(homesFile);
        claimsCfg = YamlConfiguration.loadConfiguration(claimsFile);
    }

    public void save() {
        try { homesCfg.save(homesFile); } catch (IOException ignored) {}
        try { claimsCfg.save(claimsFile); } catch (IOException ignored) {}
    }

    // Homes
    public void setHome(Player p, String name, String world, double x, double y, double z, float yaw, float pitch) {
        String base = p.getUniqueId().toString() + "." + name;
        homesCfg.set(base + ".world", world);
        homesCfg.set(base + ".x", x);
        homesCfg.set(base + ".y", y);
        homesCfg.set(base + ".z", z);
        homesCfg.set(base + ".yaw", yaw);
        homesCfg.set(base + ".pitch", pitch);
        save();
    }

    public Map<String, Object> getHome(Player p, String name) {
        String base = p.getUniqueId().toString() + "." + name;
        if (!homesCfg.contains(base + ".world")) return null;
        Map<String,Object> m = new HashMap<>();
        m.put("world", homesCfg.getString(base + ".world"));
        m.put("x", homesCfg.getDouble(base + ".x"));
        m.put("y", homesCfg.getDouble(base + ".y"));
        m.put("z", homesCfg.getDouble(base + ".z"));
        m.put("yaw", (float)homesCfg.getDouble(base + ".yaw"));
        m.put("pitch", (float)homesCfg.getDouble(base + ".pitch"));
        return m;
    }

    public int getHomeCount(Player p) {
        String root = p.getUniqueId().toString();
        if (!homesCfg.contains(root)) return 0;
        return homesCfg.getConfigurationSection(root).getKeys(false).size();
    }

    // Claims: stored by chunk key "world_x_z"
    public boolean isClaimed(String chunkKey) { return claimsCfg.contains(chunkKey + ".owner"); }
    public String getClaimOwner(String chunkKey) { return claimsCfg.getString(chunkKey + ".owner"); }
    public List<String> getTrusted(String chunkKey) {
        if (!claimsCfg.contains(chunkKey + ".trusted")) return new ArrayList<>();
        return claimsCfg.getStringList(chunkKey + ".trusted");
    }
    public void createClaim(String chunkKey, String owner) {
        claimsCfg.set(chunkKey + ".owner", owner);
        claimsCfg.set(chunkKey + ".trusted", new ArrayList<String>());
        save();
    }
    public void unclaim(String chunkKey) { claimsCfg.set(chunkKey, null); save(); }
    public void addTrust(String chunkKey, String player) {
        List<String> t = getTrusted(chunkKey);
        if (!t.contains(player)) { t.add(player); claimsCfg.set(chunkKey + ".trusted", t); save(); }
    }
}
