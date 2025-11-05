package com.trailblazer19705135.limiterplus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {

    private final LimiterPlus plugin;
    private FileConfiguration config;

    public ConfigManager(LimiterPlus plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveDefaultConfig();
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reload() {
        loadConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }
}