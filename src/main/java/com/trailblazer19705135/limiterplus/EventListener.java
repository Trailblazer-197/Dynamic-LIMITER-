package com.trailblazer19705135.limiterplus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class EventListener implements Listener {

    private final LimiterPlus plugin;
    private final ConfigManager configManager;
    private final CacheManager cacheManager;
    private final TPSMonitor tpsMonitor;

    public EventListener(LimiterPlus plugin, ConfigManager configManager, CacheManager cacheManager, TPSMonitor tpsMonitor) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.cacheManager = cacheManager;
        this.tpsMonitor = tpsMonitor;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        // Check block limits dynamically
        String blockType = event.getBlock().getType().name();
        if (configManager.getConfig().contains("limits." + blockType)) {
            int limit = getAdjustedLimit(blockType, tpsMonitor.getTPS());
            int current = cacheManager.getCount(event.getBlock().getWorld().getName(), event.getPlayer().getUniqueId().toString(), blockType);
            if (current >= limit) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(blockType + " limit exceeded (" + current + "/" + limit + ") — placement blocked.");
                plugin.getLogger().info("Player " + event.getPlayer().getName() + " exceeded " + blockType + " limit (" + current + "/" + limit + ") — placement blocked.");
            } else {
                cacheManager.incrementCount(event.getBlock().getWorld().getName(), event.getPlayer().getUniqueId().toString(), blockType);
            }
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        // Check entity limits (e.g., mobs)
        EntityType type = event.getEntityType();
        int limit = getAdjustedLimit(type.name(), tpsMonitor.getTPS());
        int current = cacheManager.getCount(event.getEntity().getWorld().getName(), getOwner(event.getEntity()), type.name());
        if (current >= limit) {
            event.setCancelled(true);
            plugin.getLogger().info("Entity limit exceeded for " + type.name() + " (" + current + "/" + limit + ") — spawn cancelled.");
        } else {
            cacheManager.incrementCount(event.getEntity().getWorld().getName(), getOwner(event.getEntity()), type.name());
        }
    }

    private int getAdjustedLimit(String type, double tps) {
        int baseLimit = configManager.getConfig().getInt("limits." + type, 32);
        if (configManager.getConfig().getBoolean("adaptive.enabled", false) && tps < configManager.getConfig().getDouble("adaptive.tps-threshold", 18.0)) {
            return (int) (baseLimit * configManager.getConfig().getDouble("adaptive.scale-factor", 0.75));
        }
        return baseLimit;
    }

    private String getOwner(org.bukkit.entity.Entity entity) {
        return entity.getUniqueId().toString(); // Simplified
    }
}