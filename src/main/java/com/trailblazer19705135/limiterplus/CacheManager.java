package com.trailblazer19705135.limiterplus;

import org.bukkit.Bukkit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {

    private final Map<String, Map<String, Map<String, Integer>>> worldCounts = new ConcurrentHashMap<>();

    public void updateCounts(String world, Map<String, Map<String, Integer>> counts) {
        worldCounts.put(world, counts);
    }

    public int getCount(String world, String owner, String type) {
        return worldCounts.getOrDefault(world, Map.of()).getOrDefault(owner, Map.of()).getOrDefault(type, 0);
    }

    public void incrementCount(String world, String owner, String type) {
        worldCounts.computeIfAbsent(world, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(owner, k -> new ConcurrentHashMap<>())
                .merge(type, 1, Integer::sum);
    }

    public void decrementCount(String world, String owner, String type) {
        Map<String, Map<String, Integer>> worldMap = worldCounts.get(world);
        if (worldMap != null) {
            Map<String, Integer> ownerMap = worldMap.get(owner);
            if (ownerMap != null) {
                ownerMap.compute(type, (k, v) -> v != null && v > 0 ? v - 1 : 0);
            }
        }
    }
}