package com.Theeef.me;

import com.Theeef.me.util.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Echovale extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        loadConfig();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Echovale Enabled");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        configManager = new ConfigManager();
        configManager.setup();
        configManager.charactersConfig.options().copyDefaults(true);
        configManager.saveCharacters();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}