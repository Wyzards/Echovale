package com.Theeef.me.api.equipment.armor;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.equipment.Cost;
import com.Theeef.me.api.equipment.Equipment;
import com.Theeef.me.util.NBTHandler;
import com.Theeef.me.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Armor extends Equipment {

    private final String armor_category;
    private final ArmorClass armor_class;
    private final long str_minimum;
    private final boolean stealth_disadvantage;
    private final double weight;
    private final Cost cost;
    private final List<ArmorPiece> pieces;
    private ArmorPiece piece; // Used to focus on certain data path, etc.

    public Armor(String url, ArmorPiece piece) {
        super(url);

        JSONObject json = APIRequest.request(url);

        this.armor_category = (String) json.get("armor_category");
        this.armor_class = new ArmorClass((JSONObject) json.get("armor_class"));
        this.str_minimum = (long) json.get("str_minimum");
        this.stealth_disadvantage = (boolean) json.get("stealth_disadvantage");
        this.weight = Equipment.parseWeight(json);
        this.cost = new Cost((JSONObject) json.get("cost"));
        this.pieces = retrieveArmorPieces();
        this.piece = piece;
    }

    public Armor(String url) {
        this(url, null);
    }

    public ItemStack getItemStack(ArmorPiece piece) {
        setPiece(piece); // Used to edit data path, weight, etc.

        ItemStack item = super.getItemStack();
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + retrieveName(getName(), piece));
        item.setItemMeta(meta);

        lore(item);
        NBTHandler.addString(item, "armorPiece", piece.name());

        setPiece(null);

        return item;
    }

    @Override
    public double getWeight() {
        if (getPiece() == null)
            return this.weight;
        else
            return Math.round(this.weight * getPiece().getPercentage(this.pieces));
    }

    @Override
    public Cost getCost() {
        if (getPiece() == null)
            return this.cost.clone();
        else
            return this.cost.clone().multiply(getPiece().getPercentage(this.pieces));
    }

    // Helper methods
    private void lore(ItemStack item) {
        List<String> lore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();
        double multiplier = piece.getPercentage(this.pieces);

        lore.add(ChatColor.GRAY + this.getArmorCategory() + " (" + this.getEquipmentCategory().getName() + ")");
        lore.add("");
        lore.add(ChatColor.GRAY + "Cost: " + getCost().amountString());
        lore.add(ChatColor.GRAY + "Weight: " + ChatColor.WHITE + getWeight() + " pounds");
        lore.add("");

        if (piece == ArmorPiece.SHIELD) {
            lore.add(ChatColor.GRAY + "Armor Class: " + ChatColor.WHITE + "+" + this.armor_class.getBase() + " while holding");
        } else {
            lore.add(ChatColor.GRAY + "Armor Class: " + ChatColor.WHITE + this.armor_class.getBase() + (this.armor_class.hasDexBonus() ? " + DEX Modifier" + (this.armor_class.getMaxDexBonus() > 0 ? " (Max of " + this.armor_class.getMaxDexBonus() + ")" : "") : "") + " with full set equipped");
            if (this.str_minimum > 0)
                lore.add(ChatColor.GRAY + "Strength Requirement: " + ChatColor.WHITE + this.str_minimum);

            if (this.stealth_disadvantage) {
                lore.add("");
                lore.add(ChatColor.RED + "Wearing this item gives disadvantage on Stealth");
            }

            lore.add("");
            lore.add(ChatColor.GRAY + "Set Includes:");

            for (ArmorPiece setPiece : this.pieces)
                lore.add(ChatColor.WHITE + "- " + retrieveName(getName(), setPiece));
        }

        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    private String retrieveName(String setName, ArmorPiece piece) {
        if (plugin.getConfigManager().getEquipmentConfig().contains(getDataPath() + ".name"))
            return plugin.getConfigManager().getEquipmentConfig().getString(getDataPath() + ".name");
        return setName + " " + Util.cleanEnumName(piece.name());
    }

    private List<ArmorPiece> retrieveArmorPieces() {
        List<ArmorPiece> pieces = new ArrayList<>();

        if (plugin.getConfigManager().getEquipmentConfig().contains(getDataPath() + ".pieces"))
            for (String string : plugin.getConfigManager().getEquipmentConfig().getStringList(getDataPath() + ".pieces"))
                pieces.add(ArmorPiece.valueOf(string));
        else {
            plugin.getConfigManager().getEquipmentConfig().set(getDataPath() + ".pieces", new ArrayList<String>());
            plugin.getConfigManager().saveEquipmentConfig();
        }

        return pieces;
    }

    // Setter methods
    public void setPiece(ArmorPiece piece) {
        this.piece = piece;
    }

    // Getter methods
    public String getArmorCategory() {
        return this.armor_category;
    }

    public ArmorClass getArmorClass() {
        return this.armor_class;
    }

    public long getStrengthRequirement() {
        return this.str_minimum;
    }

    public boolean givesStealthDisadvantage() {
        return this.stealth_disadvantage;
    }

    public List<ArmorPiece> getPieces() {
        return this.pieces;
    }

    public ArmorPiece getPiece() {
        return this.piece;
    }

    @Override
    public String getDataPath() {
        if (this.piece == null)
            return super.getDataPath();
        else
            return super.getDataPath() + "." + this.piece.name();
    }

    public static List<Armor> values() {
        List<Armor> list = new ArrayList<>();
        JSONArray armor = (JSONArray) APIRequest.request("/api/equipment-categories/armor").get("equipment");

        for (Object armorSetObj : armor)
            if (!((String) ((JSONObject) armorSetObj).get("url")).startsWith("/api/magic-items/"))
                list.add((Armor) Equipment.fromString((String) ((JSONObject) armorSetObj).get("url")));

        return list;
    }
}
