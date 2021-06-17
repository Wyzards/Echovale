package com.Theeef.me.damage;

public class DamageRoll {

    private final int min;
    private final int max;

    public DamageRoll(String dice) {
        this.min = Integer.parseInt(dice.substring(0, dice.indexOf("d")));
        this.max = Integer.parseInt(dice.substring(0, dice.indexOf("d"))) * Integer.parseInt(dice.substring(dice.indexOf("d") + 1));
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

}
