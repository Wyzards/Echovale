package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class MagicItem implements Equipment {

    private String name;
    private EquipmentCategory equipmentCategory;
    private String[] desc;

    public MagicItem(String url) {
        if (!url.substring("/api/magic-items/".length()).equals("/api/magic-items/"))
            throw new IllegalArgumentException("Non-magic item URL used for MagicItem constructor");

        JSONObject json = null;

        try {
            json = APIRequest.request(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (json == null)
            return;

        this.name = (String) json.get("name");
        this.equipmentCategory = new EquipmentCategory((String) ((JSONObject) json.get("equipment_category")).get("url"));
        this.desc = (String[]) json.get("desc");
    }

    @Override
    public ItemStack getItemStack() {
        return null;
    }
}
