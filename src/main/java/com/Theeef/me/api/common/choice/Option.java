package com.Theeef.me.api.common.choice;

import com.Theeef.me.api.classes.subclasses.Prerequisite;
import com.Theeef.me.api.common.Indexable;
import com.Theeef.me.interaction.character.CharacterCreator;
import org.bukkit.inventory.ItemStack;
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

    public abstract String getDescription();

    @Override
    public abstract boolean equals(Object object);

    @Override
    public abstract int hashCode();

    public ItemStack getOptionItem(ChoiceResult parentResult, CharacterCreator creator) {
        switch (this.option_type) {
            case CHOICE:
                return ((ChoiceOption) this).getOptionItem(parentResult);
            case MULTIPLE:
                return ((MultipleOption) this).getOptionItem(parentResult);
            default:
                return ((SingleOption) this).getOptionItem(parentResult, creator);
        }
    }

    // Getter methods
    public OptionType getOptionType() {
        return this.option_type;
    }

    public List<Prerequisite> getPrerequisites() {
        return this.prerequisites;
    }

    // Static Methods
    public static Option fromJSON(JSONObject json) {
        // APIReference
        // 0, 1, 2
        // Choice Option (equipment_option only)

        if (json.containsKey("0")) {
            List<CountedReference> items = new ArrayList<>();
            List<ChoiceOption> options = new ArrayList<>();

            for (Object numKey : json.keySet())
                if (((JSONObject) json.get(numKey)).containsKey("equipment_option"))
                    options.add(new ChoiceOption(new ArrayList<>(), new Choice((JSONObject) ((JSONObject) json.get(numKey)).get("equipment_option"))));
                else
                    items.add(CountedReference.fromJSON((JSONObject) json.get(numKey)));

            return new MultipleOption(new ArrayList<>(), items, options);
        } else if (json.containsKey("equipment_option"))
            return new ChoiceOption(new ArrayList<>(), new Choice((JSONObject) json.get("equipment_option")));
        else
            return new SingleOption(new ArrayList<>(), CountedReference.fromJSON(json));
    }
}
