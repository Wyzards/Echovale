package com.Theeef.me.api.backgrounds;

import com.Theeef.me.api.chardata.Alignment;
import com.Theeef.me.api.common.APIReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Ideal {

    private final String desc;
    private final List<APIReference> alignments;

    public Ideal(String desc, List<APIReference> alignments) {
        this.desc = desc;
        this.alignments = alignments;
    }

    public static Ideal fromJSON(JSONObject json) {
        List<APIReference> alignments = new ArrayList<>();

        for (Object alignment : (JSONArray) json.get("alignments"))
            alignments.add(new APIReference((JSONObject) alignment));

        return new Ideal((String) json.get("desc"), alignments);
    }

    // Getter methods
    public String getDescription() {
        return this.desc;
    }

    public List<Alignment> getAlignments() {
        List<Alignment> list = new ArrayList<>();

        for (APIReference alignment : this.alignments)
            list.add(new Alignment(alignment));

        return list;
    }

}
