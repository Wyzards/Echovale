package com.Theeef.me.api.equipment;

import com.Theeef.me.APIRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class DamageType {

    public static DamageType ACID = new DamageType("/api/damage-types/acid");
    public static DamageType BLUDGEONING = new DamageType("/api/damage-types/bludgeoning");
    public static DamageType COLD = new DamageType("/api/damage-types/cold");
    public static DamageType FIRE = new DamageType("/api/damage-types/fire");
    public static DamageType FORCE = new DamageType("/api/damage-types/force");
    public static DamageType LIGHTNING = new DamageType("/api/damage-types/lightning");
    public static DamageType NECROTIC = new DamageType("/api/damage-types/necrotic");
    public static DamageType PIERCING = new DamageType("/api/damage-types/piercing");
    public static DamageType POISON = new DamageType("/api/damage-types/poison");
    public static DamageType PSYCHIC = new DamageType("/api/damage-types/psychic");
    public static DamageType RADIANT = new DamageType("/api/damage-types/radiant");
    public static DamageType SLASHING = new DamageType("/api/damage-types/slashing");
    public static DamageType THUNDER = new DamageType("/api/damage-types/thunder");

    private final String index;
    private final String name;
    private final String url;

    public DamageType(String url) {
        JSONObject json = null;
        try {
            json = APIRequest.request(url);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        this.index = (String) json.get("index");
        this.name = (String) json.get("name");
        this.url = url;
    }

    public String getName() {
        return this.name;
    }
}
