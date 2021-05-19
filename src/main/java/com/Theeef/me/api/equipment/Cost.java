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

    public Cost(MoneyUnit unit, long quantity) {
        cost.put(unit, quantity);
    }

    public Cost(JSONObject json) {
        this(MoneyUnit.valueOf(((String) json.get("unit")).toUpperCase()), (long) json.get("quantity"));
    }

    /**
     * Converts this cost into one with the same total but least total pieces
     *
     * @return new cost
     */
    public Cost maximize(boolean useElectrum) {
        long copper = this.cost.containsKey(MoneyUnit.CP) ? this.cost.get(MoneyUnit.CP) : 0;
        long silver = this.cost.containsKey(MoneyUnit.CP) ? this.cost.get(MoneyUnit.CP) : 0; // 1 sp worth 10 cp
        long electrum = this.cost.containsKey(MoneyUnit.CP) ? this.cost.get(MoneyUnit.CP) : 0; // 1 ep worth 5 sp
        long gold = this.cost.containsKey(MoneyUnit.CP) ? this.cost.get(MoneyUnit.CP) : 0; // 1 gp worth 10 sp
        long platinum = this.cost.containsKey(MoneyUnit.CP) ? this.cost.get(MoneyUnit.CP) : 0;

        silver += copper / 10;
        copper = copper % 10;

        if (useElectrum) {
            electrum += silver / 10;
            silver = silver % 10;
        } else {
            silver += electrum * 5;
            electrum = 0;
        }

        gold += silver / 10;
        silver = silver % 10;
        platinum += gold / 10;
        gold = gold % 10;

        this.cost.put(MoneyUnit.CP, copper);
        this.cost.put(MoneyUnit.SP, silver);
        this.cost.put(MoneyUnit.EP, electrum);
        this.cost.put(MoneyUnit.GP, gold);
        this.cost.put(MoneyUnit.PP, platinum);

        return this;
    }

    /**
     * Multiplies this cost by a set multiplier
     *
     * @param multiplier the multiplier
     * @param round      whether or not to round new double values, as opposed to truncating
     *                   // TODO: Make it so leftover gets coverted to lower MoneyUnit Ex -> 1.1 silver -> 1 silver, 1 copper
     * @return the total cost
     */
    public Cost multiply(double multiplier, boolean round) {
        this.cost.replaceAll((u, v) -> round ? Math.round(this.cost.get(u) * multiplier) : (int) (this.cost.get(u) * multiplier));

        return this;
    }

    /**
     * Adds another cost to this one
     *
     * @param cost the cost to add
     * @return the total cost
     */
    public Cost add(Cost cost) {
        HashMap<MoneyUnit, Long> otherCost = cost.getCost();

        for (MoneyUnit unit : otherCost.keySet())
            this.cost.put(unit, this.cost.containsKey(unit) ? this.cost.get(unit) + otherCost.get(unit) : otherCost.get(unit));

        return this;
    }

    public HashMap<MoneyUnit, Long> getCost() {
        return this.cost;
    }

    public String amountString() {
        return (cost.containsKey(MoneyUnit.PP) && cost.get(MoneyUnit.PP) > 0 ? ChatColor.WHITE + Long.toString(cost.get(MoneyUnit.PP)) + "pp " : "") + (cost.containsKey(MoneyUnit.GP) && cost.get(MoneyUnit.GP) > 0 ? ChatColor.GOLD + Long.toString(cost.get(MoneyUnit.GP)) + "gp " : "") + (cost.containsKey(MoneyUnit.EP) && cost.get(MoneyUnit.EP) > 0 ? ChatColor.YELLOW + Long.toString(cost.get(MoneyUnit.EP)) + "ep " : "") + (cost.containsKey(MoneyUnit.SP) && cost.get(MoneyUnit.SP) > 0 ? ChatColor.WHITE + Long.toString(cost.get(MoneyUnit.SP)) + "sp " : "") + (cost.containsKey(MoneyUnit.CP) && cost.get(MoneyUnit.CP) > 0 ? net.md_5.bungee.api.ChatColor.of(new Color(184, 115, 51)) + Long.toString(cost.get(MoneyUnit.CP)) + "cp" :
                "");
    }

    public long getQuantity(MoneyUnit unit) {
        return this.cost.containsKey(unit) ? this.cost.get(unit) : 0;
    }
}
