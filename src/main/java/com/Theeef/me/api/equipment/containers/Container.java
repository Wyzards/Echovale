package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.api.equipment.Cost;
import com.Theeef.me.api.equipment.Equipment;
import com.Theeef.me.api.equipment.Gear;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Represents any ingame equipment item that has other items inside it.
 * Ex: backpack, pouch, basket, etc.
 */
public class Container extends Gear {

    public static HashMap<Container, Inventory> openInventories = new HashMap<Container, Inventory>();

    private final UUID uuid;
    private final List<ItemQuantity> contents;
    private final Set<ItemStack> containerItems;

    protected Container(String url, UUID uuid, List<ItemQuantity> contents) {
        super(url);

        this.uuid = uuid;
        this.contents = contents;
        this.containerItems = new HashSet<>();
    }

    // Create new, empty container
    public Container(String url) {
        this(url, UUID.randomUUID(), new ArrayList<>());
    }

    public ItemStack getItemStack() {
        ItemStack item = super.getItemStack();

        lore(item);
        nbt(item);

        return item;
    }

    public void setContents(List<ItemQuantity> contents) {
        this.contents.clear();
        this.contents.addAll(contents);
    }

    public void setContents(ItemStack[] items) {
        List<ItemQuantity> equipment = new ArrayList<>();

        for (ItemStack item : items)
            if (item == null)
                equipment.add(null);
            else
                equipment.add(new ItemQuantity(Equipment.getItemUrl(item), item.getAmount()));

        this.contents.clear();
        this.contents.addAll(equipment);

        updateContainerItems();
    }

    public void open(Player player, ItemStack item) {
        Container container = getOpenContainer(getUUID());

        if (container != null) {
            container.containerItems.add(item);
            player.openInventory(Container.openInventories.get(container));
        } else {
            Inventory inventory = getBaseInventory();

            for (int i = 0; i < this.contents.size(); i++)
                inventory.setItem(i, this.contents.get(i) == null ? null : this.contents.get(i).getItemStack());

            Container.openInventories.put(this, inventory);
            player.openInventory(inventory);
        }
    }

    public Container getOpenContainer(UUID containerUUID) {
        for (Container openContainer : openInventories.keySet())
            if (openContainer.getUUID().equals(containerUUID))
                return openContainer;

        return null;
    }

    public double getCapacity() {
        if (plugin.getConfigManager().getEquipmentConfig().contains(getEquipmentCategory().getUrl() + "." + getIndex() + ".weightCapacity"))
            return plugin.getConfigManager().getEquipmentConfig().getDouble(getEquipmentCategory().getUrl() + "." + getIndex() + ".weightCapacity");
        else {
            plugin.getConfigManager().getEquipmentConfig().set(getEquipmentCategory().getUrl() + "." + getIndex() + ".weightCapacity", 0);
            plugin.getConfigManager().saveEquipmentConfig();

            return getCapacity();
        }
    }

    public boolean hasContents() {
        for (ItemQuantity equipment : this.contents)
            if (equipment != null)
                return true;
        return false;
    }

    @Override
    public double getWeight() {
        double weight = super.getWeight();

        for (ItemQuantity content : this.contents)
            if (content != null)
                weight += content.getWeight();

        return weight;
    }

    public Cost getCost() {
        Cost cost = super.getCost();

        for (ItemQuantity content : this.contents)
            if (content != null)
                cost.add(content.getCost());

        return cost;
    }

    // Helper methods
    private void updateContainerItems() {
        for (ItemStack item : getContainerItems())
            item.setItemMeta(this.getItemStack().getItemMeta());
    }

    private Inventory getBaseInventory() {
        switch (getSlots()) {
            case 5:
                return Bukkit.createInventory(null, InventoryType.HOPPER, getName());
            case 9:
                return Bukkit.createInventory(null, InventoryType.DISPENSER, getName());
            default:
                return Bukkit.createInventory(null, getSlots(), getName());
        }
    }

    private double getMaxVolume() {
        if (plugin.getConfigManager().getEquipmentConfig().contains(getEquipmentCategory().getUrl() + "." + getIndex() + ".volumeCapacity"))
            return plugin.getConfigManager().getEquipmentConfig().getDouble(getEquipmentCategory().getUrl() + "." + getIndex() + ".volumeCapacity");
        else {
            plugin.getConfigManager().getEquipmentConfig().set(getEquipmentCategory().getUrl() + "." + getIndex() + ".volumeCapacity", 0);
            plugin.getConfigManager().saveEquipmentConfig();

            return getCapacity();
        }
    }

    private int getSlots() {
        double volume = getMaxVolume();

        if (volume == 0.2)
            return 5;
        else if (volume == 2)
            return 27;
        else if (volume == 1)
            return 18;
        else if (volume == 12)
            return 54;
        else if (volume == 4)
            return 36;
        else if (volume == 0.5)
            return 9;

        return 9;
    }

    private void lore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + getGearCategory().getName() + " (" + getEquipmentCategory().getName() + ")");
        lore.add("");
        lore.add(ChatColor.GRAY + "Total Cost: " + getCost().maximize(false, false).amountString());
        lore.add(ChatColor.GRAY + "Total Weight: " + ChatColor.WHITE + getWeight() + " pounds");
        lore.add("");

        if (hasContents()) {
            lore.add(ChatColor.GRAY + "Contents");

            int count = 0;

            for (ItemQuantity equipment : this.contents)
                if (equipment != null && count <= 8) {
                    lore.add(ChatColor.GRAY + "- " + ChatColor.WHITE + equipment.getItem().getName() + " x" + equipment.getQuantity());
                    count++;
                }

            if (count == 9)
                lore.add(ChatColor.WHITE + "- and " + (countContents() - 8) + " more...");
        }

        lore.add("");
        lore.add(ChatColor.AQUA + "Hold + Right Click" + ChatColor.GRAY + " to view contents");

        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    private void nbt(ItemStack item) {
        NBTHandler.addString(item, "containerUUID", this.uuid.toString());

        for (int i = 0; i < this.contents.size(); i++)
            NBTHandler.addString(item, "contents_" + i, this.contents.get(i) == null ? "null" : this.contents.get(i).toString());
    }

    private int countContents() {
        int count = 0;

        for (ItemQuantity equipment : this.contents)
            if (equipment != null)
                count++;
        return count;
    }

    // Getter methods
    public UUID getUUID() {
        return this.uuid;
    }

    public List<ItemQuantity> getContents() {
        return this.contents;
    }

    public Set<ItemStack> getContainerItems() {
        return this.containerItems;
    }

    // Static methods
    public static boolean isContainer(ItemStack item) {
        return NBTHandler.hasString(item, "containerUUID");
    }

    public static UUID getContainerUUID(ItemStack item) {
        return UUID.fromString(NBTHandler.getString(item, "containerUUID"));
    }

    protected static List<ItemQuantity> getItemContents(ItemStack item) {
        List<ItemQuantity> list = new ArrayList<>();
        int content = 0;


        while (NBTHandler.hasString(item, "contents_" + content)) {
            if (NBTHandler.getString(item, "contents_" + content).equals("null"))
                list.add(null);
            else
                list.add(new ItemQuantity(NBTHandler.getString(item, "contents_" + content)));

            content++;
        }

        return list;
    }

    public static Container getContainerFromItem(ItemStack item) {
        if (Pack.isPack(item))
            return Pack.getPackFromItem(item);
        else {
            UUID uuid = Container.getContainerUUID(item);
            String url = Equipment.getItemUrl(item);
            List<ItemQuantity> contents = getItemContents(item);

            return new Container(url, uuid, contents);
        }
    }
}
