package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import com.Theeef.me.util.Util;
import com.google.common.collect.Sets;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MagicItem extends Equipment {

    private final List<String> desc;

    public MagicItem(String url) {
        super(url);

        JSONObject json = APIRequest.request(url);

        this.desc = new ArrayList<>();

        assert json != null;
        for (Object arrayObj : ((JSONArray) json.get("desc")))
            this.desc.add((String) arrayObj);
    }

    public ItemStack getItemStack() {
        ItemStack item = super.getItemStack();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        List<String> lore = new ArrayList<>();

        for (String string : this.desc)
            lore.addAll(Util.fitForLore(string));

        meta.setLore(lore);
        item.setItemMeta(meta);

        // TODO: Potentially add description NBT data

        return item;
    }

    public static Set<MagicItem> values() {
        Set<MagicItem> set = Sets.newHashSet();
        JSONObject json = APIRequest.request("/api/magic-items/");
        JSONArray results = (JSONArray) json.get("results");

        for (Object object : results)
            set.add(new MagicItem(((String) ((JSONObject) object).get("url"))));

        return set;
    }
}
