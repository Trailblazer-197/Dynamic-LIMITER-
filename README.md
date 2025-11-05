# ⚙️ Dynamic LIMITER+
[![Download on Modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_64h.png)](https://modrinth.com/plugin/dynamic-limiter+)

**For Minecraft 1.21.x**

Dynamic LIMITER+ is a next-generation performance and resource management plugin designed to control how much of anything players or worlds can use — from hoppers and mobs to redstone components — without compromising performance.

Unlike traditional limiters that rely on laggy loops or entity purges, LIMITER+ runs asynchronously, dynamically adjusting limits based on server conditions like TPS, world load, or player count.

It’s not just a “cap” plugin — it’s an adaptive resource manager that keeps your server balanced, efficient, and lag-free.

## 🧩 Core Highlights

- Entity & Block Limiting
Define per-player, per-world, or global caps for entities and blocks like mobs, hoppers, pistons, observers, villagers, armor stands, and more.

- Dynamic Auto-Adjustment
Limits automatically scale down during lag spikes — e.g., if TPS < 18, thresholds drop to 75% until recovery.

- Async Scanning Engine
Scans and monitors entities off the main thread — zero TPS impact, zero lag spikes.

- Configurable Actions
Choose how excess entities or blocks are handled:

 - Prevent placement/spawn

 - Remove automatically

 - Warn players

 - Log events for admins

- Clean Admin Tools

**/limiter check <player>**   →  View entity/block counts  
**/limiter scan <world>**     →  Run a manual async scan  
**/limiter reload**           →  Reload configuration  
**/limiter info**             →  View live TPS and scaling status


- Adaptive Caching System
Maintains real-time counts with minimal impact by caching results between scans.

- Automatic World Scans
Set custom intervals for automatic async scans to keep limits accurate.

- Lightweight Configuration
Simple, readable YAML setup — no scripts, dependencies, or performance trade-offs.

## 🧠 How It Works

1. LIMITER+ asynchronously scans all worlds for entity and block counts.

2. Cached data is used for real-time event checks (spawn, placement, etc.).

3. When limits are exceeded, configured actions are applied instantly.

4. During lag spikes, adaptive mode temporarily lowers caps to stabilize TPS.

## 🌟 Why LIMITER+

**_Because lag prevention shouldn’t be about deleting — it should be about managing intelligently.
LIMITER+ brings modern, dynamic control to server performance — no clutter, no overkill, just precision._**
