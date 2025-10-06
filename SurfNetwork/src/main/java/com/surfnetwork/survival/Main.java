package com.surfnetwork.survival;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
    private static Main instance;
    private Economy econ;
    private DataManager data;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        if (!setupEconomy()) {
            getLogger().severe("Vault not found or no economy provider. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        data = new DataManager(this);
        data.load();

        // Register commands
        getCommand("sethome").setExecutor(new SetHomeCommand(this));
        getCommand("home").setExecutor(new HomeCommand(this));
        getCommand("claim").setExecutor(new ClaimCommand(this));
        getCommand("trust").setExecutor(new TrustCommand(this));
        getCommand("unclaim").setExecutor(new UnclaimCommand(this));
        getCommand("top").setExecutor(new TopCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new NPCClickListener(this), this);
        getServer().getPluginManager().registerEvents(new ClaimProtectListener(this), this);
        getServer().getPluginManager().registerEvents(new MiningListener(this), this);

        new ScoreboardTask(this).start();

        getLogger().info("SurfNetwork enabled.");
    }

    @Override
    public void onDisable() {
        data.save();
        getLogger().info("SurfNetwork disabled.");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Main getInstance() { return instance; }
    public Economy getEconomy() { return econ; }
    public DataManager getData() { return data; }
}
