package com.Theeef.me.api.equipment;

import org.bukkit.ChatColor;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.HashMap;

public class Cost {

    public enum MoneyUnit {
        CP, SP, GP, PP, EP;
    }

    // Represents how much a specific item costs, or how much it can sell for
    private final HashMap<MoneyUnit, Long> cost = new HashMap<MoneyUnit, Long>();

    public Cost(JSONObject json) {
        long quantity = (long) json.get("quantity");
        MoneyUnit unit = MoneyUnit.valueOf(((String) json.get("unit")).toUpperCase());

        cost.put(unit, quantity);
    }

    public String amountString() {
        return (cost.containsKey(MoneyUnit.PP) ? ChatColor.WHITE + Long.toString(cost.get(MoneyUnit.PP)) + "pp " : "") + (cost.containsKey(MoneyUnit.GP) ? ChatColor.GOLD + Long.toString(cost.get(MoneyUnit.GP)) + "gp " : "") + (cost.containsKey(MoneyUnit.EP) ? ChatColor.YELLOW + Long.toString(cost.get(MoneyUnit.EP)) + "ep " : "") + (cost.containsKey(MoneyUnit.SP) ? ChatColor.WHITE + Long.toString(cost.get(MoneyUnit.SP)) + "sp " : "") + (cost.containsKey(MoneyUnit.CP) ? net.md_5.bungee.api.ChatColor.of(new Color(184, 115, 51)) + Long.toString(cost.get(MoneyUnit.CP)) + "cp" :
                "");
    }
}
