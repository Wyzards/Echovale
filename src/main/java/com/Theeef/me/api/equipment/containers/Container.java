package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.api.equipment.Cost;
import com.Theeef.me.api.equipment.Equipment;
import com.Theeef.me.api.equipment.Gear;
import com.Theeef.me.util.NBTHandler;
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
    private final Cost cost;

    public Container(String containerEquipmentUrl, String name, Cost cost, List<ContainerEquipment> contents) {
        super(containerEquipmentUrl);

        setName(name);
        this.contents = contents;
        this.cost = cost;

        if (this.contents.size() > getSlots())
            throw new IllegalArgumentException("Contents exceeded maximum slots of container");
        else if (contents.size() < getSlots())
            for (int i = 0; i < getSlots() - contents.size(); i++)
                this.contents.add(null);
    }

    public Container(String containerEquipmentUrl, String name, List<ContainerEquipment> contents) {
        this(containerEquipmentUrl, name, null, contents);
    }

    public Container(ItemStack item) {
        this(NBTHandler.getString(item, "url"), NBTHandler.getString(item, "name"), Cost.getFromContainer(item), getItemContents(item));
    }

    private static List<ContainerEquipment> getItemContents(ItemStack item) {
        List<ContainerEquipment> list = new ArrayList<>();
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
        lore.add(ChatColor.GRAY + "Cost: " + getCost().maximize(false, false).amountString());
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
                lore.add(ChatColor.WHITE + "- and " + (countContents() - 8) + " more...");
        }

        lore.add("");
        lore.add(ChatColor.AQUA + "Hold + Right Click" + ChatColor.GRAY + " to view contents");

        // NBT Data
        if (this.cost != null)
            for (Cost.MoneyUnit moneyUnit : Cost.MoneyUnit.values())
                NBTHandler.addString(item, "containerCost_" + moneyUnit.name().toLowerCase(), String.valueOf(this.cost.getQuantity(moneyUnit)));

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
                return false;
        return true;
    }

    public Cost getCost() {
        if (this.cost != null)
            return this.cost;
        else
            return this.getTotalCost();
    }

    public Cost getTotalCost() {
        Cost cost = super.getCost();

        for (ContainerEquipment content : this.contents)
            if (content != null)
                cost.add(content.getCost());

        return cost;
    }

    public double getContentsWeight() {
        double weight = 0;

        for (ContainerEquipment content : this.contents)
            if (content != null)
                weight += content.getWeight();

        return weight;
    }
}
