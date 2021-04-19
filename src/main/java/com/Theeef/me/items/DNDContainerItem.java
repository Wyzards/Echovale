package com.Theeef.me.items;

import org.bukkit.Material;

public class DNDContainerItem extends DNDItem {

    private double weightCapacity;
    private double volume;
    private String containerLabel;
    private DNDItem[] items;

    public DNDContainerItem(String ID, String name, Material material, int amount, String description, MoneyAmount cost, double weight, double weightCapacity, double volume, String containerLabel, DNDItem... items) {
        super(ID, name, material, amount, description, cost, weight);

        this.weightCapacity = weightCapacity;
        this.volume = volume;
        this.containerLabel = containerLabel;
        this.items = items;
    }

    public boolean hasItems() {
        for (DNDItem item : items)
            if (item != null)
                return true;

        return false;
    }

    public double getWeightCapacity() {
        return this.weightCapacity;
    }

    public double getVolume() {
        return this.volume;
    }

    public String getContainerLabel() {
        return this.containerLabel;
    }

    public DNDItem[] getItems() {
        return this.items;
    }
}
