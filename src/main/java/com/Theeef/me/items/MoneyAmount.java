package com.Theeef.me.items;

public class MoneyAmount {

    private int copper;
    private int silver;
    private int electrum;
    private int gold;
    private int platinum;

    public MoneyAmount(int copper, int silver, int electrum, int gold, int platinum) {
        this.copper = copper;
        this.silver = silver;
        this.electrum = electrum;
        this.gold = gold;
        this.platinum = platinum;
    }

    public static MoneyAmount fromGold(int gold) {
        return new MoneyAmount(0, 0, 0, gold, 0);
    }

    public static MoneyAmount fromSilver(int silver) {
        return new MoneyAmount(0, silver, 0, 0, 0);
    }

    public int copperValue() {
        return copper + silver * 10 + electrum * 50 + gold * 100 + platinum * 1000;
    }

}
