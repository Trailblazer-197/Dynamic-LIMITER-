package com.trailblazer19705135.limiterplus;

import org.bukkit.Bukkit;

public class TPSMonitor {

    private final LimiterPlus plugin;
    private double tps = 20.0;

    public TPSMonitor(LimiterPlus plugin) {
        this.plugin = plugin;
        // Simple TPS calculation (use a library for better accuracy)
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            tps = Math.min(20.0, Bukkit.getTPS()[0]);
        }, 0L, 20L);
    }

    public double getTPS() {
        return tps;
    }
}