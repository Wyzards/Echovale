package com.Theeef.me.items;

import org.bukkit.Material;

import java.util.List;

public class DNDItem {

    private String ID;
    private String name;
    private Material material;
    private String description;
    private double weight;
    private MoneyAmount cost;

    public DNDItem(String ID, String name, Material material, String description, MoneyAmount cost, double weight) {
        this.ID = ID;
        this.name = name;
        this.material = material;
        this.description = description;
        this.cost = cost;
        this.weight = weight;
    }

}
