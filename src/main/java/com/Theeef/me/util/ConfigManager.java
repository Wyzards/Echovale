package com.Theeef.me.util;

import com.Theeef.me.Echovale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final Echovale plugin = Echovale.getPlugin(Echovale.class);

    // Files & File Configs Here
    private FileConfiguration charactersConfig;
    private File charactersFile;
    private FileConfiguration equipmentConfig;
    private File equipmentFile;
    // --------------------------

    public void setup() {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();

        charactersFile = new File(plugin.getDataFolder(), "characters.yml");

        if (!charactersFile.exists()) {
            try {
                if (charactersFile.createNewFile())
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The characters.yml file has been created");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender()
                        .sendMessage(ChatColor.RED + "Could not create the characters.yml file");
            }
        }

        charactersConfig = YamlConfiguration.loadConfiguration(charactersFile);

        equipmentFile = new File(plugin.getDataFolder(), "equipment.yml");

        if (!equipmentFile.exists()) {
            try {
                if (equipmentFile.createNewFile())
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The equipment.yml file has been created");
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender()
                        .sendMessage(ChatColor.RED + "Could not create the equipment.yml file");
            }
        }

        equipmentConfig = YamlConfiguration.loadConfiguration(equipmentFile);
    }

    public FileConfiguration getCharacters() {
        return charactersConfig;
    }

    public void saveCharacters() {
        try {
            charactersConfig.save(charactersFile);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "The characters.yml file has been saved");
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the characters.yml file");
        }
    }

    public void reloadCharacters() {
        charactersConfig = YamlConfiguration.loadConfiguration(charactersFile);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The characters.yml file has been reload");
    }

    public FileConfiguration getEquipmentConfig() {
        return equipmentConfig;
    }

    public void saveEquipmentConfig() {
        try {
            equipmentConfig.save(equipmentFile);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "The equipment.yml file has been saved");
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the equipment.yml file");
        }
    }

    public void reloadEquipmentConfig() {
        equipmentConfig = YamlConfiguration.loadConfiguration(equipmentFile);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The equipment.yml file has been reload");
    }
}
