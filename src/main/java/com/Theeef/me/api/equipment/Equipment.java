package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import com.Theeef.me.Echovale;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public abstract class Equipment {

    private static final Echovale plugin = Echovale.getPlugin(Echovale.class);
    private final String index;
    private final String equipment_category_url;
    private final String url;

    public Equipment(String url) {
        JSONObject json = null;

        try {
            json = APIRequest.request(url);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        assert json != null;
        this.index = (String) json.get("index");
        this.equipment_category_url = (String) ((JSONObject) json.get("equipment_category")).get("url");
        this.url = url;
    }

    public abstract ItemStack getItemStack();

    public Material retrieveMaterial() {
        if (plugin.getConfigManager().getEquipmentConfig().contains(this.equipment_category_url + "." + this.index + ".material"))
            return Material.valueOf(plugin.getConfigManager().getEquipmentConfig().getString(this.equipment_category_url + "." + this.index + ".material"));
        else {
            plugin.getConfigManager().getEquipmentConfig().set(this.equipment_category_url + "." + this.index + ".material", "BARRIER");
            plugin.getConfigManager().saveEquipmentConfig();
        }

        return Material.BARRIER;
    }

    public String getEquipmentCategoryUrl() {
        return this.equipment_category_url;
    }

    public String getIndex() {
        return this.index;
    }
}
