package com.Theeef.me;

import com.Theeef.me.api.equipment.Weapon;
import com.Theeef.me.api.equipment.WeaponProperty;
import com.Theeef.me.items.DNDItem;
import com.Theeef.me.items.weapons.DNDWeapon;
import com.Theeef.me.items.equipment.AdventuringGear;
import com.Theeef.me.items.armor.Armor;
import com.Theeef.me.items.weapons.Weapons;
import com.Theeef.me.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Echovale extends JavaPlugin implements Listener {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        loadConfig();

        getServer().getPluginManager().registerEvents(this, this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Echovale Enabled");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.configManager = new ConfigManager();
        this.configManager.setup();

        // Setting up Characters config
        this.configManager.getCharacters().options().copyDefaults(true);
        this.configManager.saveCharacters();

        // Setting up Equipment config
        this.configManager.getEquipmentConfig().options().copyDefaults(true);
        this.configManager.saveEquipmentConfig();
    }

    @EventHandler
    public void weapons(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase("weapons")) {
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Weapons");

            for (Weapon weapon : Weapon.values())
                inventory.addItem(weapon.getItemStack());

            Bukkit.getScheduler().runTask(this, new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().openInventory(inventory);
                }
            });
        } else if (event.getMessage().equalsIgnoreCase("armor")) {
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Armor");

            for (DNDItem item : Armor.values())
                inventory.addItem(item.getItem());

            Bukkit.getScheduler().runTask(this, new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().openInventory(inventory);
                }
            });
        } else if (event.getMessage().equalsIgnoreCase("adventure")) {
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Adventure");

            for (DNDItem item : AdventuringGear.values())
                inventory.addItem(item.getItem());

            Bukkit.getScheduler().runTask(this, new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().openInventory(inventory);
                }
            });
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}