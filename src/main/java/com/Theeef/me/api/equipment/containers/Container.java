package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.api.equipment.Cost;
import com.Theeef.me.api.equipment.Equipment;
import com.Theeef.me.api.equipment.Gear;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents any ingame equipment item that has other items inside it.
 * Ex: backpack, pouch, basket, etc.
 */
public class Container extends Gear {

    private final List<ContainerEquipment> contents;
    private final int capacity;
    private final int maxVolume;
    private final int slots;

    public Container(String containerEquipmentUrl, String name, List<ContainerEquipment> contents) {
        super(containerEquipmentUrl);

        setName(name);
        this.contents = contents;
        this.capacity = getCapacity();
        this.maxVolume = getMaxVolume();
        this.slots = getSlots();

        if (this.contents.size() > this.slots)
            throw new IllegalArgumentException("Contents exceeded maximum slots of container");
        else if (contents.size() < this.slots)
            for (int i = 0; i < this.slots - contents.size(); i++)
                this.contents.add(null);
    }

    // TODO: Proper capacity and volume
    private int getCapacity() {
        return 100;
    }

    private int getMaxVolume() {
        return 36;
    }

    private int getSlots() {
        return 36;
    }

    public Inventory getInventory() {
        return null;
    }

    public ItemStack getItemStack() {
        ItemStack item = super.getItemStack();
        ItemMeta meta = item.getItemMeta();

        assert meta != null;

        // Overriding Gear lore
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + getGearCategory().getName() + " (" + getEquipmentCategory().getName() + ")");
        lore.add("");
        lore.add(ChatColor.GRAY + "Total Cost: " + getTotalCost().amountString());
        lore.add(ChatColor.GRAY + "Total Weight: " + ChatColor.WHITE + (getWeight() + getContentsWeight()) + " pounds");
        lore.add("");

        if (!isEmpty()) {
            lore.add(ChatColor.GRAY + "Contents");

            int count = 0;

            for (ContainerEquipment equipment : this.contents)
                if (equipment != null && count <= 8) {
                    lore.add(ChatColor.GRAY + "- " + ChatColor.WHITE + equipment.getEquipment().getName() + " x" + equipment.getQuantity());
                    count++;
                }

            if (count == 9)
                lore.add(ChatColor.GRAY + "- and " + (countContents() - 8) + " more...");
        }

        lore.add(ChatColor.AQUA + "Hold + Right Click" + ChatColor.GRAY + " to view contents");

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    private int countContents() {
        int count = 0;

        for (ContainerEquipment equipment : this.contents)
            if (equipment != null)
                count++;
        return count;
    }

    private boolean isEmpty() {
        for (ContainerEquipment equipment : this.contents)
            if (equipment != null)
                return true;
        return false;
    }

    public Cost getTotalCost() {
        Cost cost = new Cost(Cost.MoneyUnit.CP, 0);

        for (ContainerEquipment content : this.contents)
            if (content != null)
                cost.add(content.getCost());

        return cost.maximize(false);
    }

    public double getContentsWeight() {
        double weight = 0;

        for (ContainerEquipment content : this.contents)
            if (content != null)
                weight += content.getWeight();

        return weight;
    }
}
