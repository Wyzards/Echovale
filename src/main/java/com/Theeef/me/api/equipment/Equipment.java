package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import com.Theeef.me.Echovale;
import com.Theeef.me.api.equipment.containers.Container;
import com.Theeef.me.api.equipment.containers.Pack;
import com.Theeef.me.api.equipment.weapons.Weapon;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.json.simple.JSONObject;

public class Equipment {

    private static final Echovale plugin = Echovale.getPlugin(Echovale.class);

    private final String index;
    private String name;
    private final String equipment_category_url;
    private final String url;

    public Equipment(String url) {
        JSONObject json = APIRequest.request(url);

        assert json != null;
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.equipment_category_url = (String) ((JSONObject) json.get("equipment_category")).get("url");
        this.url = url;
    }

    public static Equipment fromItem(ItemStack item) {
        if (NBTHandler.hasString(item, "url"))
            return Equipment.fromString(NBTHandler.getString(item, "url"));
        else
            throw new IllegalArgumentException("Specified item does not have an equipment URL");
    }

    public static double weighItem(ItemStack item) {
        // TODO: Could cause issue when trying to weigh a container that is currently open
        if (Container.isContainer(item)) {
            return new Container(item).getTotalWeight();
        } else if (NBTHandler.hasString(item, "weight"))
            return Double.parseDouble(NBTHandler.getString(item, "weight"));
        else
            throw new IllegalArgumentException("ItemStack \"" + item + "\" did not have the weight NBT data.");
    }

    public static Equipment fromString(String url) {
        if (url.startsWith("/api/magic-items/"))
            return new MagicItem(url);

        JSONObject object = APIRequest.request(url);

        if (object.containsKey("equipment_category")) {
            String equipmentCategory = (String) ((JSONObject) object.get("equipment_category")).get("index");

            if (equipmentCategory.equals("weapon"))
                return new Weapon(url);
            else if (equipmentCategory.equals("adventuring-gear")) {
                String gearCategory = (String) ((JSONObject) object.get("gear_category")).get("index");

                if (gearCategory.equals("equipment-packs"))
                    return new Pack(url);
                else
                    return new Gear(url);
            }
        }

        throw new IllegalArgumentException("Could not find proper Equipment Type for url: " + url);
    }

    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(retrieveMaterial(), retrieveQuantity());
        ItemMeta meta = item.getItemMeta();

        if (meta instanceof PotionMeta)
            ((PotionMeta) meta).setColor(retrievePotionColor());

        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + this.name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "index", this.index);
        NBTHandler.addString(item, "name", this.name);
        NBTHandler.addString(item, "equipment_category_url", this.equipment_category_url);
        NBTHandler.addString(item, "url", this.url);

        return item;
    }

    private Color retrievePotionColor() {
        if (plugin.getConfigManager().getEquipmentConfig().contains(this.equipment_category_url + "." + this.index + ".potionColor"))
            return Color.fromRGB(plugin.getConfigManager().getEquipmentConfig().getInt(this.equipment_category_url + "." + this.index + ".potionColor.R"), plugin.getConfigManager().getEquipmentConfig().getInt(this.equipment_category_url + "." + this.index + ".potionColor.G"), plugin.getConfigManager().getEquipmentConfig().getInt(this.equipment_category_url + "." + this.index + ".potionColor.B"));
        else {
            plugin.getConfigManager().getEquipmentConfig().set(this.equipment_category_url + "." + this.index + ".potionColor.R", 0);
            plugin.getConfigManager().getEquipmentConfig().set(this.equipment_category_url + "." + this.index + ".potionColor.G", 0);
            plugin.getConfigManager().getEquipmentConfig().set(this.equipment_category_url + "." + this.index + ".potionColor.B", 0);
            plugin.getConfigManager().saveEquipmentConfig();
        }

        return Color.fromRGB(0, 0, 0);
    }

    private int retrieveQuantity() {
        if (plugin.getConfigManager().getEquipmentConfig().contains(this.equipment_category_url + "." + getIndex() + ".quantity"))
            return plugin.getConfigManager().getEquipmentConfig().getInt(this.equipment_category_url + "." + getIndex() + ".quantity");

        return 1;
    }

    private Material retrieveMaterial() {
        if (plugin.getConfigManager().getEquipmentConfig().contains(this.equipment_category_url + "." + this.index + ".material"))
            return Material.valueOf(plugin.getConfigManager().getEquipmentConfig().getString(this.equipment_category_url + "." + this.index + ".material"));
        else {
            plugin.getConfigManager().getEquipmentConfig().set(this.equipment_category_url + "." + this.index + ".material", "BARRIER");
            plugin.getConfigManager().saveEquipmentConfig();
        }

        return Material.BARRIER;
    }

    public EquipmentCategory getEquipmentCategory() {
        return new EquipmentCategory(this.equipment_category_url);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }
}
