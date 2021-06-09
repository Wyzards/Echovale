package com.Theeef.me.damage;

import org.json.simple.JSONObject;

public class Damage {

    private final String damage_type_url;
    private final int min;
    private final int max;

    public Damage(JSONObject damage) {
        String damage_dice = (String) damage.get("damage_dice");
        int diceNum = Integer.parseInt(damage_dice.substring(0, damage_dice.indexOf("d")));

        this.min = diceNum;
        this.max = diceNum * Integer.parseInt(damage_dice.substring(damage_dice.indexOf("d") + 1)); // diceNum * maxDiceRoll
        this.damage_type_url = (String) ((JSONObject) damage.get("damage_type")).get("url");
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public DamageType getType() {
        return new DamageType(this.damage_type_url);
    }
}
