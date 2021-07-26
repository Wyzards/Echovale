package com.Theeef.me.api.common.choice;

import com.Theeef.me.APIRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResourceListOptionSet extends OptionSet {

    private final String url;

    public ResourceListOptionSet(String url) {
        super(OptionSet.OptionSetType.RESOURCE_LIST);

        this.url = url;
    }

    public List<Option> getOptions() {
        List<Option> list = new ArrayList<>();
        JSONObject referenceList = APIRequest.request(this.url);

        if (referenceList.containsKey("equipment"))
            for (Object equipment : (JSONArray) referenceList.get("equipment"))
                list.add(new SingleOption(new ArrayList<>(), CountedReference.fromJSON((JSONObject) equipment)));
        else if (referenceList.containsKey("results"))
            for (Object reference : (JSONArray) referenceList.get("results"))
                list.add(new SingleOption(new ArrayList<>(), CountedReference.fromJSON((JSONObject) reference)));

        return list;
    }

}
