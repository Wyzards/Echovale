package com.Theeef.me;

import com.Theeef.me.api.equipment.Gear;
import com.Theeef.me.api.equipment.MagicItem;
import com.Theeef.me.api.equipment.weapons.Weapon;
import com.Theeef.me.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

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

            Bukkit.getScheduler().runTask(this, () -> event.getPlayer().openInventory(inventory));
        } else if (event.getMessage().equalsIgnoreCase("armor")) {
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Armor");


            Bukkit.getScheduler().runTask(this, () -> event.getPlayer().openInventory(inventory));
        } else if (event.getMessage().equalsIgnoreCase("gear")) {
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Gear");

            for (Gear gear : Gear.values())
                if (inventory.firstEmpty() != -1)
                    inventory.addItem(gear.getItemStack());

            Bukkit.getScheduler().runTask(this, () -> event.getPlayer().openInventory(inventory));
        } else if (event.getMessage().equalsIgnoreCase("magic")) {
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Magic Items");

            for (MagicItem magicItem : MagicItem.values())
                if (inventory.firstEmpty() != -1)
                    inventory.addItem(magicItem.getItemStack());

            Bukkit.getScheduler().runTask(this, () -> event.getPlayer().openInventory(inventory));
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}