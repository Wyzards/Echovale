package com.Theeef.me.equipment.armor;

import com.Theeef.me.APIRequest;
import com.Theeef.me.equipment.CommonEquipment;
import com.Theeef.me.equipment.Equipment;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Armor extends CommonEquipment {

    private final String armor_category;
    private final ArmorClass armor_class;
    private final long str_minimum;
    private final boolean stealth_disadvantage;
    private final List<ArmorPiece> pieces;
    private String dataPath;

    public Armor(String url) {
        super(url);

        JSONObject json = APIRequest.request(url);

        this.armor_category = (String) json.get("armor_category");
        this.armor_class = new ArmorClass((JSONObject) json.get("armor_class"));
        this.str_minimum = (long) json.get("str_minimum");
        this.stealth_disadvantage = (boolean) json.get("stealth_disadvantage");

        this.pieces = retrieveArmorPieces();
    }

    public static List<Armor> values() {
        List<Armor> list = new ArrayList<>();
        JSONArray armor = (JSONArray) APIRequest.request("/api/equipment-categories/armor").get("equipment");

        for (Object armorSetObj : armor)
            if (!((String) ((JSONObject) armorSetObj).get("url")).startsWith("/api/magic-items/"))
                list.add((Armor) Equipment.fromString((String) ((JSONObject) armorSetObj).get("url")));

        return list;
    }

    public ItemStack getItemStack(ArmorPiece piece) {
        setDataPath(piece);

        ItemStack item = super.getItemStack();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        double multiplier = piece.getPercentage(this.pieces);

        lore.add(ChatColor.GRAY + "Cost: " + getCost().multiply(multiplier, true).amountString());
        lore.add(ChatColor.GRAY + "Weight: " + ChatColor.WHITE + (getWeight() * multiplier) + " pounds");
        lore.add("");
        lore.add(ChatColor.GRAY + "Armor Class: " + ChatColor.WHITE + this.armor_class.getBase() + (this.armor_class.hasDexBonus() ? " + DEX" + (this.armor_class.getMaxDexBonus() > 0 ? " (Max of " + this.armor_class.getMaxDexBonus() + ")" : "") : ""));
        lore.add(ChatColor.GRAY + "Strength Requirement: " + ChatColor.WHITE + this.str_minimum);
        lore.add(ChatColor.GRAY + "Stealth Disadvantage: " + ChatColor.WHITE + (this.stealth_disadvantage ? "Yes" : "No"));
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "armor_category", this.armor_category);
        NBTHandler.addString(item, "armor_class_base", Long.toString(this.armor_class.getBase()));
        NBTHandler.addString(item, "armor_class_has_dex_bonus", Boolean.toString(this.armor_class.hasDexBonus()));
        NBTHandler.addString(item, "armor_class_max_dex_bonus", Long.toString(this.armor_class.getMaxDexBonus()));
        NBTHandler.addString(item, "str_minimum", Long.toString(this.str_minimum));
        NBTHandler.addString(item, "stealth_disadvantage", Boolean.toString(this.stealth_disadvantage));
        NBTHandler.addString(item, "armorPiece", piece.name());

        return item;
    }

    private void setDataPath(ArmorPiece piece) {
        if (piece == null)
            this.dataPath = null;
        else
            this.dataPath = super.getDataPath() + "." + piece.name();
    }

    private List<ArmorPiece> retrieveArmorPieces() {
        setDataPath(null);
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

    @Override
    public String getDataPath() {
        if (this.dataPath == null)
            return super.getDataPath();
        else
            return this.dataPath;
    }

    public List<ArmorPiece> getPieces() {
        return this.pieces;
    }

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
}
