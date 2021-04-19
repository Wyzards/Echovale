package com.Theeef.me.items;

import org.bukkit.ChatColor;

import java.awt.*;

public class MoneyAmount {

    private int copper;
    private int silver;
    private int electrum;
    private int gold;
    private int platinum;

    public MoneyAmount(double copper, double silver, double electrum, double gold, double platinum) {
        this.platinum = (int) platinum;
        this.gold = (int) (gold + 10 * (platinum - this.platinum));
        this.silver = (int) (silver + 5 * (electrum - this.electrum) + 10 * ((gold + 10 * (platinum - this.platinum)) - this.gold));
        this.electrum = (int) electrum;
        this.copper = (int) (copper + 10 * ((silver + 5 * (electrum - this.electrum) + 10 * ((gold + 10 * (platinum - this.platinum)) - this.gold)) - this.silver));
    }

    public static MoneyAmount fromGold(double gold) {
        return new MoneyAmount(0, 0, 0, gold, 0);
    }

    public static MoneyAmount fromSilver(double silver) {
        return new MoneyAmount(0, silver, 0, 0, 0);
    }

    public static MoneyAmount fromCopper(double copper) {
        return new MoneyAmount(copper, 0, 0, 0, 0);
    }

    public int copperValue() {
        return copper + silver * 10 + electrum * 50 + gold * 100 + platinum * 1000;
    }

    public String amountString() {
        return (platinum > 0 ? ChatColor.WHITE + Integer.toString(platinum) + "pp " : "") + (gold > 0 ? ChatColor.GOLD + Integer.toString(gold) + "gp " : "") + (electrum > 0 ? ChatColor.YELLOW + Integer.toString(electrum) + "ep " : "") + (silver > 0 ? ChatColor.GRAY + Integer.toString(silver) + "sp " : "") + (copper > 0 ? net.md_5.bungee.api.ChatColor.of(new Color(184, 115, 51)) + Integer.toString(copper) + "cp" :
                "");
    }

}
