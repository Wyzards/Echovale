package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.api.equipment.Cost;
import com.Theeef.me.api.equipment.Gear;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Represents any ingame equipment item that has other items inside it.
 * Ex: backpack, pouch, basket, etc.
 */
public class Container extends Gear {

    public static HashMap<Container, Inventory> openInventories = new HashMap<Container, Inventory>();

    private final UUID uuid;
    private final List<ContainerEquipment> contents;
    private final Cost cost;
    private final List<ItemStack> containerItems;

    public Container(String containerEquipmentUrl, UUID uuid, String name, Cost cost, List<ContainerEquipment> contents) {
        super(containerEquipmentUrl);

        setName(name);
        this.contents = contents;
        this.cost = cost;
        this.uuid = uuid;
        this.containerItems = new ArrayList<>();

        if (this.contents.size() > getSlots())
            throw new IllegalArgumentException("Contents exceeded maximum slots of container");
    }

    public Container(String containerEquipmentUrl, String name, Cost cost, List<ContainerEquipment> contents) {
        this(containerEquipmentUrl, UUID.randomUUID(), name, cost, contents);
    }

    public Container(String containerEquipmentUrl, UUID uuid, String name, List<ContainerEquipment> contents) {
        this(containerEquipmentUrl, uuid, name, null, contents);
    }

    public Container(ItemStack item) {
        this(NBTHandler.getString(item, "url"), UUID.fromString(NBTHandler.getString(item, "uuid")), NBTHandler.getString(item, "name"), Cost.getFromContainer(item), getItemContents(item));
    }

    public static boolean isContainer(ItemStack item) {
        return item != null && NBTHandler.hasString(item, "contents_0");
    }

    private static List<ContainerEquipment> getItemContents(ItemStack item) {
        List<ContainerEquipment> list = new ArrayList<>();
        int content = 0;


        while (NBTHandler.hasString(item, "contents_" + content)) {
            if (NBTHandler.getString(item, "contents_" + content).equals("null"))
                list.add(null);
            else
                list.add(new ContainerEquipment(NBTHandler.getString(item, "contents_" + content)));

            content++;
        }

        return list;
    }

    public void setContents(ItemStack[] items) {
        // Get list of ContainerEquipment
        // Get Equipment from Item
        // Get quantity from item (fix this earlier)

        List<ContainerEquipment> equipment = new ArrayList<>();

        for (ItemStack item : items)
            if (item == null)
                equipment.add(null);
            else {
                if (NBTHandler.hasString(item, "url"))
                    equipment.add(new ContainerEquipment(NBTHandler.getString(item, "url"), item.getAmount()));
                else
                    throw new IllegalArgumentException("ItemStack in contents did not have url NBT tag");
            }

        this.contents.clear();
        this.contents.addAll(equipment);
    }

    public void open(Player player, ItemStack item) {
        this.containerItems.add(item);

        Container container = getOpenContainer(this);

        if (container != null) {
            player.openInventory(Container.openInventories.get(container));
        } else {
            Inventory inventory = Bukkit.getServer().createInventory(null, getSlots(), getName());

            for (int i = 0; i < this.contents.size(); i++)
                inventory.setItem(i, this.contents.get(i) == null ? null : this.contents.get(i).getItemStack());

            Container.openInventories.put(this, inventory);
            player.openInventory(inventory);
        }

        // TODO: Make it so you can't exceed costs
        // TODO: MAKE CLOTHES NOT CANCER
        // TODO: MAKE KITS NOT CANCER
        // TODO: MAKE IT SO YOU CANT PUT THE CONTAINER INSIDE ITSELF
        // TODO: MAKE CODE NOT GARBAGE
        // TODO: FIX EQUIPMENT QUANTITIES
    }


    public Container getOpenContainer(Container container) {
        for (Container openContainer : openInventories.keySet())
            if (container.equals(openContainer))
                return openContainer;

        return null;
    }

    public List<ItemStack> getContainerItems() {
        return this.containerItems;
    }

    public List<ContainerEquipment> getContents() {
        return this.contents;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (!(obj instanceof Container))
            return false;
        else
            return this.uuid.equals(((Container) obj).getUUID());
    }

    public UUID getUUID() {
        return this.uuid;
    }

    // TODO: Proper capacity and volume
    public double getCapacity() {
        return 100;
    }

    private int getMaxVolume() {
        return 36;
    }

    private int getSlots() {
        return 36;
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
        lore.add(ChatColor.GRAY + "Total Weight: " + ChatColor.WHITE + (getWeight() + getTotalWeight()) + " pounds");
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
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        // NBT Data
        NBTHandler.addString(item, "uuid", this.uuid.toString());

        if (this.cost != null)
            for (Cost.MoneyUnit moneyUnit : Cost.MoneyUnit.values())
                NBTHandler.addString(item, "containerCost_" + moneyUnit.name().toLowerCase(), String.valueOf(this.cost.getQuantity(moneyUnit)));

        for (int i = 0; i < this.contents.size(); i++)
            NBTHandler.addString(item, "contents_" + i, this.contents.get(i) == null ? "null" : this.contents.get(i).toString());

        return item;
    }

    private int countContents() {
        int count = 0;

        for (ContainerEquipment equipment : this.contents)
            if (equipment != null)
                count++;
        return count;
    }

    public boolean isEmpty() {
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

    public double getTotalWeight() {
        double weight = getWeight();

        for (ContainerEquipment content : this.contents)
            if (content != null)
                weight += content.getWeight();

        return weight;
    }
}
