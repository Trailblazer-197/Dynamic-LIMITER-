package com.trailblazer19705135.limiterplus;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.block.Block;
import org.bukkit.Material;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AsyncScanner {

    private final LimiterPlus plugin;
    private final ConfigManager configManager;
    private final CacheManager cacheManager;
    private final TPSMonitor tpsMonitor;
    private int taskId = -1;

    public AsyncScanner(LimiterPlus plugin, ConfigManager configManager, CacheManager cacheManager, TPSMonitor tpsMonitor) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.cacheManager = cacheManager;
        this.tpsMonitor = tpsMonitor;
    }

    public void startScanning() {
        long intervalTicks = configManager.getConfig().getLong("scan.interval", 6000L); // 5m default
        taskId = Bukkit.getScheduler().runTaskTimer(plugin, this::scanWorlds, 0L, intervalTicks).getTaskId();
    }

    public void stopScanning() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }

    private void scanWorlds() {
        for (World world : Bukkit.getWorlds()) {
            scanWorld(world);
        }
    }

    public void scanWorld(World world) {
        Map<String, Map<String, Integer>> worldCounts = new ConcurrentHashMap<>();
        for (Entity entity : world.getEntities()) {
            String owner = getOwner(entity);
            worldCounts.computeIfAbsent(owner, k -> new ConcurrentHashMap<>()).merge(entity.getType().name(), 1, Integer::sum);
        }
        // Add block scanning logic here (e.g., hoppers, pistons)
        cacheManager.updateCounts(world.getName(), worldCounts);
    }

    private String getOwner(Entity entity) {
        // Placeholder: Implement owner detection (e.g., from metadata or UUID)
        return entity.getUniqueId().toString(); // Simplified
    }
}
