package com.Theeef.me.api.monsters;

import com.Theeef.me.api.chardata.AbilityScore;
import com.Theeef.me.api.common.APIReference;
import org.json.simple.JSONObject;

public class DC {

    private final APIReference dc_type;
    private final String success_type;

    public DC(JSONObject json) {
        this.dc_type = new APIReference((JSONObject) json.get("dc_type"));
        this.success_type = (String) json.get("success_type");
    }

    // Getter methods
    public AbilityScore getDCType() {
        return new AbilityScore(this.dc_type);
    }

    public String getSuccessType() {
        return this.success_type;
    }

}
