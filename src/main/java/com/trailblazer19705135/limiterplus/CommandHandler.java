package com.trailblazer19705135.limiterplus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    private final LimiterPlus plugin;
    private final ConfigManager configManager;
    private final AsyncScanner asyncScanner;
    private final TPSMonitor tpsMonitor;

    public CommandHandler(LimiterPlus plugin, ConfigManager configManager, AsyncScanner asyncScanner, TPSMonitor tpsMonitor) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.asyncScanner = asyncScanner;
        this.tpsMonitor = tpsMonitor;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;

        switch (args[0].toLowerCase()) {
            case "check":
                if (args.length > 1) {
                    // Implement check player counts
                    sender.sendMessage("Checking player: " + args[1]);
                }
                break;
            case "scan":
                if (args.length > 1) {
                    // Implement manual scan
                    sender.sendMessage("Scanning world: " + args[1]);
                }
                break;
            case "reload":
                configManager.reload();
                sender.sendMessage("Config reloaded!");
                break;
            case "info":
                double tps = tpsMonitor.getTPS();
                sender.sendMessage("Current TPS: " + tps + ", Scaling: " + (tps < 18.0 ? "Active" : "Inactive"));
                break;
            default:
                return false;
        }
        return true;
    }
}