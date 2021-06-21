package com.Theeef.me.api.equipment.weapons;

import org.json.simple.JSONObject;

public class WeaponRange {

    private final long normalRange;
    private final long longRange;

    public WeaponRange(JSONObject range) {
        this.normalRange = range.get("normal") == null ? 0 : (long) range.get("normal");
        this.longRange = range.get("long") == null ? 0 : (long) range.get("long");
    }

    // Getter methods
    public long getNormal() {
        return this.normalRange;
    }

    public long getLong() {
        return this.longRange;
    }
}
