package com.Theeef.me;

import com.Theeef.me.api.equipment.Equipment;
import com.Theeef.me.api.equipment.Gear;
import com.Theeef.me.api.equipment.MagicItem;
import com.Theeef.me.api.equipment.armor.Armor;
import com.Theeef.me.api.equipment.armor.ArmorPiece;
import com.Theeef.me.api.equipment.containers.ContainerEvents;
import com.Theeef.me.api.equipment.containers.Pack;
import com.Theeef.me.api.equipment.weapons.Weapon;
import com.Theeef.me.api.spells.Spell;
import com.Theeef.me.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Echovale extends JavaPlugin implements Listener {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        loadConfig();

        Equipment.changeMaxStackSize();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ContainerEvents(), this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Echovale Enabled");
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        Bukkit.getScheduler().runTaskLater(this, () -> {
            ((Player) event.getWhoClicked()).updateInventory();
        }, 1L);
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
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Gear");

            for (Armor armor : Armor.values())
                for (ArmorPiece piece : armor.getPieces())
                    if (inventory.firstEmpty() != -1)
                        inventory.addItem(armor.getItemStack(piece));

            Bukkit.getScheduler().runTask(this, () -> event.getPlayer().openInventory(inventory));
        } else if (event.getMessage().equalsIgnoreCase("gear")) {
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Gear");

            for (Gear gear : Gear.values())
                if (inventory.firstEmpty() != -1)
                    inventory.addItem(gear.getItemStack());

            Bukkit.getScheduler().runTask(this, () -> event.getPlayer().openInventory(inventory));
        } else if (event.getMessage().equalsIgnoreCase("pack")) {
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Pack");

            for (Pack pack : Pack.packValues())
                if (inventory.firstEmpty() != -1)
                    inventory.addItem(pack.getItemStack());

            Bukkit.getScheduler().runTask(this, () -> event.getPlayer().openInventory(inventory));
        } else if (event.getMessage().equalsIgnoreCase("magic")) {
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Magic Items");

            for (MagicItem magicItem : MagicItem.values())
                if (inventory.firstEmpty() != -1)
                    inventory.addItem(magicItem.getItemStack());

            Bukkit.getScheduler().runTask(this, () -> event.getPlayer().openInventory(inventory));
        } else if (event.getMessage().equalsIgnoreCase("spells")) {
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Spells");

            //          for(Spell spell : Spell.values())
            //            if(inventory.firstEmpty() != -1)
            //              inventory.addItem(spell.itemRep());

            Bukkit.getScheduler().runTask(this, () -> event.getPlayer().openInventory(inventory));
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}