package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import com.Theeef.me.Echovale;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.choice.CountedReference;
import com.Theeef.me.api.equipment.armor.Armor;
import com.Theeef.me.api.equipment.containers.Container;
import com.Theeef.me.api.equipment.containers.Pack;
import com.Theeef.me.api.equipment.weapons.Weapon;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;

public abstract class Equipment {

    protected static final Echovale plugin = Echovale.getPlugin(Echovale.class);

    private final String index;
    private String name;
    private final APIReference equipment_category;
    private final String url;

    public Equipment(String url) {
        JSONObject json = APIRequest.request(url);

        assert json != null;
        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.equipment_category = new APIReference((JSONObject) json.get("equipment_category"));
        this.url = url;
    }

    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(retrieveMaterial(), 1);
        ItemMeta meta = item.getItemMeta();

        if (!plugin.getConfigManager().getEquipmentConfig().contains("maxStackSize." + item.getType().name())) {
            plugin.getConfigManager().getEquipmentConfig().set("maxStackSize." + item.getType().name(), 64);
            plugin.getConfigManager().saveEquipmentConfig();
        }

        if (meta instanceof PotionMeta)
            ((PotionMeta) meta).setColor(retrievePotionColor());

        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + getName());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        NBTHandler.addString(item, "equipmentUrl", this.url);

        return item;
    }

    public abstract Cost getCost();

    public abstract double getWeight();

    // Helper methods
    protected String getDataPath() {
        return this.equipment_category.getUrl() + "." + getIndex();
    }

    private Color retrievePotionColor() {
        if (plugin.getConfigManager().getEquipmentConfig().contains(getDataPath() + ".potionColor"))
            return Color.fromRGB(plugin.getConfigManager().getEquipmentConfig().getInt(getDataPath() + ".potionColor.R"), plugin.getConfigManager().getEquipmentConfig().getInt(this.equipment_category.getUrl() + "." + this.index + ".potionColor.G"), plugin.getConfigManager().getEquipmentConfig().getInt(this.equipment_category.getUrl() + "." + this.index + ".potionColor.B"));
        else {
            plugin.getConfigManager().getEquipmentConfig().set(getDataPath() + ".potionColor.R", 0);
            plugin.getConfigManager().getEquipmentConfig().set(getDataPath() + ".potionColor.G", 0);
            plugin.getConfigManager().getEquipmentConfig().set(getDataPath() + ".potionColor.B", 0);
            plugin.getConfigManager().saveEquipmentConfig();
        }

        return Color.fromRGB(0, 0, 0);
    }

    private Material retrieveMaterial() {
        if (plugin.getConfigManager().getEquipmentConfig().contains(getDataPath() + ".material"))
            return Material.valueOf(plugin.getConfigManager().getEquipmentConfig().getString(getDataPath() + ".material"));
        else {
            plugin.getConfigManager().getEquipmentConfig().set(getDataPath() + ".material", "BARRIER");
            plugin.getConfigManager().saveEquipmentConfig();
        }

        return Material.BARRIER;
    }

    // Setter method
    public void setName(String name) {
        this.name = name;
    }

    // Getter methods
    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public EquipmentCategory getEquipmentCategory() {
        return new EquipmentCategory(this.equipment_category);
    }

    public String getUrl() {
        return this.url;
    }

    // Static methods
    public static Equipment fromItem(ItemStack item) {
        return Equipment.fromString(Equipment.getItemUrl(item));
    }

    public static String getItemUrl(ItemStack item) {
        return NBTHandler.getString(item, "equipmentUrl");
    }

    public static double parseWeight(JSONObject json) {
        if (json.containsKey("weight")) {
            if (json.get("weight") instanceof Long)
                return (double) (long) json.get("weight");
            else
                return (double) json.get("weight");
        } else
            return 0;
    }

    public static double weighItem(ItemStack item, boolean weighWholeStack) {
        Equipment equipment = Equipment.fromItem(item);

        return equipment.getWeight() * (weighWholeStack ? item.getAmount() : 1);
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
                else if (plugin.getConfigManager().getEquipmentConfig().getStringList("containers").contains(url))
                    return new Container(url);
                else
                    return new Gear(url);
            } else if (equipmentCategory.equals("armor"))
                return new Armor(url);
            else if (equipmentCategory.equals("tools"))
                return new Tool(url);
        }

        throw new IllegalArgumentException("Could not find proper Equipment Type for url: " + url);
    }

    public static void changeMaxStackSize() {
        if (plugin.getConfigManager().getEquipmentConfig().contains("maxStackSize"))
            for (String materialName : plugin.getConfigManager().getEquipmentConfig().getConfigurationSection("maxStackSize").getKeys(false)) {
                net.minecraft.server.v1_16_R3.ItemStack item = CraftItemStack.asNMSCopy(new ItemStack(Material.valueOf(materialName)));

                try {
                    Field maxStackSize = net.minecraft.server.v1_16_R3.Item.class.getDeclaredField("maxStackSize");
                    maxStackSize.setAccessible(true);
                    maxStackSize.set(item.getItem(), plugin.getConfigManager().getEquipmentConfig().getInt("maxStackSize." + materialName));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    System.out.println(materialName + ", CLASS: " + item.getItem().getClass().getName());
                }

            }
    }
}
