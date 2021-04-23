package com.Theeef.me.items.containers;

import com.Theeef.me.items.DNDItem;
import com.Theeef.me.items.MoneyAmount;
import com.Theeef.me.items.containers.DNDContainerItem;
import com.Theeef.me.util.NBTHandler;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DNDContainerPouch extends DNDContainerItem {
    public DNDContainerPouch(int amount, String containerLabel, DNDItem... item) {
        super("POUCH", "Pouch", Material.FLOWER_POT, amount, null, MoneyAmount.fromSilver(5), 1, 6, 1 / 5., containerLabel, item);
    }
}
