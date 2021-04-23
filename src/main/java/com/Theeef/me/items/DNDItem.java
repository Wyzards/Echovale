package com.Theeef.me.items;

import com.Theeef.me.characters.classes.equipment.EquipmentChoice;
import com.Theeef.me.characters.classes.equipment.IEquipmentChoice;
import com.Theeef.me.items.containers.DNDContainerItem;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DNDItem implements Cloneable, IEquipmentChoice {

    private String ID;
    private String name;
    private ItemStack item;
    private String description;
    private double weight;
    private MoneyAmount cost;
    private int amount;
    private ItemType[] type;

    public DNDItem(String ID, String name, ItemStack item, int amount, String description, MoneyAmount cost, double weight, ItemType... type) {
        this.ID = ID;
        this.name = name;
        this.item = item;
        this.amount = amount;
        this.description = description;
        this.cost = cost;
        this.weight = weight;
        this.type = type;

        if (this instanceof DNDContainerItem && !Lists.newArrayList(type).contains(ItemType.CONTAINER)) {
            List<ItemType> list = Lists.newArrayList(this.type);
            list.add(ItemType.CONTAINER);
            this.type = (ItemType[]) list.toArray();
        }
    }

    public DNDItem(String ID, String name, Material material, int amount, String description, MoneyAmount cost, double weight, ItemType... type) {
        this(ID, name, new ItemStack(material, amount), amount, description, cost, weight, type);
    }

    public DNDItem(String ID, Material material, String description, int gold, double weight, ItemType... type) {
        this(ID, Util.cleanEnumName(ID), material, 1, description, MoneyAmount.fromGold(gold), weight, type);
    }

    public ItemStack getItem() {
        ItemStack item = this.item.clone();
        ItemMeta meta = item.getItemMeta();
        item.setAmount(this.amount);
        meta.setDisplayName(ChatColor.RESET + name);
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Cost: " + ChatColor.WHITE + cost.amountString());
        lore.add(ChatColor.GRAY + "Weight: " + ChatColor.WHITE + weight + " pounds");

        if (description != null) {
            lore.add("");
            lore.addAll(Util.fitForLore(description));
        }

        for (int i = 0; i < lore.size(); i++)
            lore.set(i, ChatColor.GRAY + lore.get(i));

        if (Lists.newArrayList(type).contains(ItemType.AMMUNITION)) {
            List<String> lore2 = Util.fitForLore(ChatColor.WHITE + "Ammunition: " + ChatColor.GRAY + "You can use a weapon that has the Ammunition property to make a ranged Attack only if you have Ammunition to fire from the weapon. Each time you Attack with the weapon, you expend one piece of Ammunition. Drawing the Ammunition from a Quiver, case, or other container is part of the Attack. At the end of the battle, you can recover half your expended Ammunition by taking a minute to Search the battlefield.");
            for (int i = 1; i < lore2.size(); i++)
                lore2.set(i, ChatColor.GRAY + lore2.get(i));

            lore.add("");
            lore.addAll(lore2);
        }

        if (Lists.newArrayList(type).contains(ItemType.ARCANE_FOCUS)) {
            List<String> lore2 = Util.fitForLore(ChatColor.WHITE + "Arcane Focus: " + ChatColor.GRAY + "An arcane focus is a Special item designed to channel the power of arcane Spells. A Sorcerer, Warlock, or Wizard can use such an item as a Spellcasting Focus, using it in place of any material component which does not list a cost.");
            for (int i = 1; i < lore2.size(); i++)
                lore2.set(i, ChatColor.GRAY + lore2.get(i));

            lore.add("");
            lore.addAll(lore2);
        }

        if (Lists.newArrayList(type).contains(ItemType.DRUIDIC_FOCUS)) {
            List<String> lore2 = Util.fitForLore(ChatColor.WHITE + "Druidic Focus: " + ChatColor.GRAY + "A Druidic focus might be a Sprig of mistletoe or holly, a wand or scepter made of yew or another Special wood, a staff drawn whole out of a living tree, or a totem object incorporating feathers, fur, bones, and teeth from sacred animals. A druid can use such an object as a Spellcasting Focus.");
            for (int i = 1; i < lore2.size(); i++)
                lore2.set(i, ChatColor.GRAY + lore2.get(i));

            lore.add("");
            lore.addAll(lore2);
        }

        if (Lists.newArrayList(type).contains(ItemType.HOLY_SYMBOL)) {
            List<String> lore2 = Util.fitForLore(ChatColor.WHITE + "Holy Symbol: " + ChatColor.GRAY + "A holy symbol is a representation of a god or pantheon. A cleric or paladin can use a holy symbol as a spellcasting focus. To use the symbol in this way, the caster must hold it in hand, wear it visibly, or bear it on a shield.");

            for (int i = 1; i < lore2.size(); i++)
                lore2.set(i, ChatColor.GRAY + lore2.get(i));

            lore.add("");
            lore.addAll(lore2);
        }

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return NBTHandler.addString(item, "itemID", ID);
    }

    /**
     * Gets the same item type with a different amount
     *
     * @param amount the amount
     * @return the new item
     */
    public DNDItem getAmount(int amount) {
        try {
            DNDItem item = (DNDItem) this.clone();
            item.amount = amount;

            return item;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        throw new NullPointerException("Was not able to modify DNDItem's amount");
    }

    @Override
    public List<DNDItem> getChoice() {
        return Lists.newArrayList(this);
    }

    public int getAmount() {
        return this.amount;
    }

    public double getWeight() {
        return weight;
    }

    public String getID() {
        return ID;
    }

    public MoneyAmount getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return item.getType();
    }

    public enum ItemType {
        ARMOR, WEAPON, AMMUNITION, ARCANE_FOCUS, DRUIDIC_FOCUS, HOLY_SYMBOL, ADVENTURING_GEAR, CONTAINER, TOOL, ARTISANS_TOOLS, GAMING_SET, MUSICAL_INSTRUMENT, MOUNTS, DRAWN_VEHICLES, SADDLES, WATER_VEHICLES, FOOD_AND_DRINK, CONSUMABLE;
    }
}
