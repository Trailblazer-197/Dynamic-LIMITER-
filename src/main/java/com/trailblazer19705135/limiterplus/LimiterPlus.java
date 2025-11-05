package com.trailblazer19705135.limiterplus;

import org.bukkit.plugin.java.JavaPlugin;

public class LimiterPlus extends JavaPlugin {

    private ConfigManager configManager;
    private AsyncScanner asyncScanner;
    private TPSMonitor tpsMonitor;
    private CacheManager cacheManager;
    private CommandHandler commandHandler;
    private EventListener eventListener;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        tpsMonitor = new TPSMonitor(this);
        cacheManager = new CacheManager();
        asyncScanner = new AsyncScanner(this, configManager, cacheManager, tpsMonitor);
        commandHandler = new CommandHandler(this, configManager, asyncScanner, tpsMonitor);
        eventListener = new EventListener(this, configManager, cacheManager, tpsMonitor);

        getCommand("limiter").setExecutor(commandHandler);
        getServer().getPluginManager().registerEvents(eventListener, this);

        asyncScanner.startScanning();
        getLogger().info("LIMITER+ enabled!");
    }

    @Override
    public void onDisable() {
        if (asyncScanner != null) asyncScanner.stopScanning();
        getLogger().info("LIMITER+ disabled!");
    }
}