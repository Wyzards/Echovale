package com.Theeef.me.api.classes;

import org.json.simple.JSONObject;

public class SpellcastingLevel {

    private final long cantrips_known;
    private final long spell_slots_level_1;
    private final long spell_slots_level_2;
    private final long spell_slots_level_3;
    private final long spell_slots_level_4;
    private final long spell_slots_level_5;
    private final long spell_slots_level_6;
    private final long spell_slots_level_7;
    private final long spell_slots_level_8;
    private final long spell_slots_level_9;

    public SpellcastingLevel(JSONObject json) {
        this.cantrips_known = json.containsKey("cantrips_known") ? (long) json.get("cantrips_known") : 0;
        this.spell_slots_level_1 = json.containsKey("spell_slots_level_1") ? (long) json.get("spell_slots_level_1") : 0;
        this.spell_slots_level_2 = json.containsKey("spell_slots_level_2") ? (long) json.get("spell_slots_level_2") : 0;
        this.spell_slots_level_3 = json.containsKey("spell_slots_level_3") ? (long) json.get("spell_slots_level_3") : 0;
        this.spell_slots_level_4 = json.containsKey("spell_slots_level_4") ? (long) json.get("spell_slots_level_4") : 0;
        this.spell_slots_level_5 = json.containsKey("spell_slots_level_5") ? (long) json.get("spell_slots_level_5") : 0;
        this.spell_slots_level_6 = json.containsKey("spell_slots_level_6") ? (long) json.get("spell_slots_level_6") : 0;
        this.spell_slots_level_7 = json.containsKey("spell_slots_level_7") ? (long) json.get("spell_slots_level_7") : 0;
        this.spell_slots_level_8 = json.containsKey("spell_slots_level_8") ? (long) json.get("spell_slots_level_8") : 0;
        this.spell_slots_level_9 = json.containsKey("spell_slots_level_9") ? (long) json.get("spell_slots_level_9") : 0;
    }

    // Getter methods
    public long getCantripsKnown() {
        return this.cantrips_known;
    }

    public long getSpellSlotsAtLevel(int level) {
        switch (level) {
            case 1:
                return this.spell_slots_level_1;
            case 2:
                return this.spell_slots_level_2;
            case 3:
                return this.spell_slots_level_3;
            case 4:
                return this.spell_slots_level_4;
            case 5:
                return this.spell_slots_level_5;
            case 6:
                return this.spell_slots_level_6;
            case 7:
                return this.spell_slots_level_7;
            case 8:
                return this.spell_slots_level_8;
            case 9:
                return this.spell_slots_level_9;
            default:
                return 0;
        }
    }

}
