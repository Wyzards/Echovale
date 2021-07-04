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

    public Ideal(JSONObject json) {
        this.desc = (String) json.get("desc");
        this.alignments = new ArrayList<>();

        for (Object alignment : (JSONArray) json.get("alignments"))
            this.alignments.add(new APIReference((JSONObject) alignment));
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
