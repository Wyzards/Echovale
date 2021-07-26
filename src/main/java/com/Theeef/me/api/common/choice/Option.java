package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Option {

    public enum OptionType {
        SINGLE, MULTIPLE, CHOICE;
    }

    private final OptionType option_type;
    private final List<Prerequisite> prerequisites;

    public Option(OptionType option_type, List<Prerequisite> prerequisites) {
        this.option_type = option_type;
        this.prerequisites = prerequisites;
    }

    // Static Methods
    public static Option fromJSON(JSONObject json) {
        // APIReference
        // 0, 1, 2
        // Choice Option (equipment_option only)

        if (json.containsKey("0")) {
            List<Option> options = new ArrayList<>();

            for (Object numKey : json.keySet())
                options.add(Option.fromJSON((JSONObject) json.get(numKey)));

            return new MultipleOption(new ArrayList<>(), options);
        } else if (json.containsKey("equipment_option"))
            return new ChoiceOption(new ArrayList<>(), new Choice((JSONObject) json.get("equipment_option")));
        else
            return new SingleOption(new ArrayList<>(), CountedReference.fromJSON(json));
    }

}
