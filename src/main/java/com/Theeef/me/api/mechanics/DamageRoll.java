package com.Theeef.me.api.mechanics;

public class DamageRoll {

    private int min;
    private int max;

    public DamageRoll(String dice) {
        this.min = 0;
        this.max = 0;

        if (!dice.equals("MOD"))
            if (!dice.contains(" + ")) {
                if (dice.contains("d")) {
                    this.min = Integer.parseInt(dice.substring(0, dice.indexOf("d")));
                    this.max = Integer.parseInt(dice.substring(0, dice.indexOf("d"))) * Integer.parseInt(dice.substring(dice.indexOf("d") + 1, dice.contains(" ") ? dice.indexOf(" ") : dice.length()));
                } else {
                    this.min = Integer.parseInt(dice);
                    this.max = Integer.parseInt(dice);
                }
            } else
                for (String diceSegment : dice.split(" \\+ "))
                    add(new DamageRoll(diceSegment));
    }

    // Helper methods
    private void add(DamageRoll roll) {
        this.min += roll.getMin();
        this.max += roll.getMax();
    }

    // Getter methods
    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }
}
