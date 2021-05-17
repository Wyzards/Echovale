package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.equipment.weapons.Weapon;
import com.Theeef.me.util.Util;
import com.google.common.collect.Sets;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MagicItem extends Equipment {

    private final String name;
    private final String desc;

    public MagicItem(String url) {
        super(url);

        JSONObject json = null;
        try {
            json = APIRequest.request(url);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        assert json != null;
        StringBuilder desc = new StringBuilder();

        for (Object arrayObj : ((JSONArray) json.get("desc")))
            desc.append((String) arrayObj);

        this.name = (String) json.get("name");
        this.desc = desc.toString();
    }

    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(retrieveMaterial());
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + this.name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        List<String> lore = Util.fitForLore(this.desc);
        for (int i = 0; i < lore.size(); i++)
            lore.set(i, ChatColor.GRAY + lore.get(i));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static Set<MagicItem> values() {
        Set<MagicItem> set = Sets.newHashSet();

        try {
            JSONObject json = APIRequest.request("/api/magic-items/");
            JSONArray results = (JSONArray) json.get("results");

            for (Object object : results)
                set.add(new MagicItem(((String) ((JSONObject) object).get("url"))));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return set;
    }
}
