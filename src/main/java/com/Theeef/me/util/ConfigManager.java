package com.Theeef.me.util;

import com.Theeef.me.Echovale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private Echovale plugin = Echovale.getPlugin(Echovale.class);

    // Files & File Configs Here
    public FileConfiguration charactersConfig;
    public File charactersFile;
    // --------------------------

    public void setup() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        charactersFile = new File(plugin.getDataFolder(), "players.yml");

        if (!charactersFile.exists()) {
            try {
                charactersFile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The players.yml file has been created");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender()
                        .sendMessage(ChatColor.RED + "Could not create the players.yml file");
            }
        }

        charactersConfig = YamlConfiguration.loadConfiguration(charactersFile);
    }

    public FileConfiguration getCharacters() {
        return charactersConfig;
    }

    public void saveCharacters() {
        try {
            charactersConfig.save(charactersFile);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "The players.yml file has been saved");

        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the players.yml file");
        }
    }

    public void reloadCharacters() {
        charactersConfig = YamlConfiguration.loadConfiguration(charactersFile);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The players.yml file has been reload");
    }

}
