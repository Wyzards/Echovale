package com.Theeef.me.api.classes.equipment;

import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.equipment.containers.ItemQuantity;
import org.json.simple.JSONObject;

public class EquipmentQuantity extends ItemQuantity {

    public EquipmentQuantity(JSONObject json) {
        super(new APIReference((JSONObject) json.get("equipment")), (long) json.get("quantity"));
    }

}
