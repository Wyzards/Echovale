package com.Theeef.me.items.containers;

import com.Theeef.me.items.DNDItem;
import com.Theeef.me.items.MoneyAmount;
import com.Theeef.me.util.Util;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class ContainerType {

    /**
     * Represents the type of a container, storing information about this container type's max volume,
     * weight, and appearance
     */

    public static ContainerType POUCH = new ContainerType("POUCH", Material.FLOWER_POT, 1, 6, 1.0 / 5.0, MoneyAmount.fromSilver(5), DNDItem.ItemType.CONTAINER);
    public static ContainerType BACKPACK = new ContainerType("BACKPACK", Material.TRAPPED_CHEST, 5, 30, 1, MoneyAmount.fromGold(2), DNDItem.ItemType.CONTAINER, DNDItem.ItemType.ADVENTURING_GEAR);
    public static ContainerType BASKET = new ContainerType("BASKET", Material.COMPOSTER, 2, 40, 2, MoneyAmount.fromSilver(4), DNDItem.ItemType.CONTAINER, DNDItem.ItemType.ADVENTURING_GEAR);

    private final String ID;
    private final String name;
    private final ItemStack appearance;
    private final int weight;
    private final double maxWeight;
    private final double maxVolume; // Maximum volume in feet cubed
    private final MoneyAmount cost; // Cost of this ContainerType by itself
    private final DNDItem.ItemType[] itemType;

    public ContainerType(String ID, String name, ItemStack appearance, int weight, double maxWeight, double maxVolume, MoneyAmount cost, DNDItem.ItemType... itemType) {
        this.ID = ID;
        this.name = name;
        this.appearance = appearance;
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.cost = cost;
        this.itemType = itemType;
    }

    public ContainerType(String ID, String name, Material appearance, int weight, double maxWeight, double maxVolume, MoneyAmount cost, DNDItem.ItemType... itemType) {
        this(ID, name, new ItemStack(appearance), weight, maxWeight, maxVolume, cost, itemType);
    }

    public ContainerType(String ID, Material appearance, int weight, double maxWeight, double maxVolume, MoneyAmount cost, DNDItem.ItemType... itemType) {
        this(ID, Util.cleanEnumName(ID), appearance, weight, maxWeight, maxVolume, cost, itemType);
    }

    /**
     * Gets number of slots a container of this type can have, based on maxVolume
     *
     * @return inventory slot number
     */
    public int getSlots() {
        if (this.maxVolume == 1.0 / 5.0)
            return 5;
        if (this.maxVolume == 1)
            return 18;

        return 0;
    }

    /**
     * Gets the InventoryType to use for this ContainerType
     *
     * @return an inventoryType
     */
    public InventoryType getInventoryType() {
        if (getSlots() == 5)
            return InventoryType.HOPPER;
        else if (getSlots() == 9)
            return InventoryType.DISPENSER;
        else if (getSlots() % 9 == 0)
            return InventoryType.CHEST;

        throw new IllegalArgumentException("ContainerType has slots not multiple of 9, and not 5");
    }

    public String getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    public double getMaxWeight() {
        return this.maxWeight;
    }

    public ItemStack getAppearance() {
        return this.appearance;
    }

    public MoneyAmount getCost() {
        return this.cost;
    }

    public DNDItem.ItemType[] getItemType() {
        return this.itemType;
    }
}
