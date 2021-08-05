package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.common.APIReference;
import org.json.simple.JSONObject;

import java.util.Objects;

public class CountedReference {

    private final long count;
    private final APIReference of;

    public CountedReference(long count, APIReference of) {
        this.count = count;
        this.of = of;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof CountedReference && ((CountedReference) object).getReference().equals(this.of) && ((CountedReference) object).getCount() == this.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), this.of, this.count);
    }

    // Getter methods
    public APIReference getReference() {
        return this.of;
    }

    public long getCount() {
        return this.count;
    }

    // Static Methods
    public static CountedReference fromJSON(JSONObject json) {
        if (json.containsKey("equipment"))
            return new CountedReference((long) json.get("quantity"), new APIReference((JSONObject) json.get("equipment")));
        else
            return new CountedReference(1, new APIReference(json));
    }

}
