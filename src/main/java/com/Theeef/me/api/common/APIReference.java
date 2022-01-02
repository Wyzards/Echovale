package com.Theeef.me.api.common;

import org.json.simple.JSONObject;

import java.util.Objects;

public class APIReference {

    private final String index;
    private final String name;
    private final String url;

    public APIReference(String index, String name, String url) {
        this.index = index;
        this.name = name;
        this.url = url;
    }

    public APIReference(JSONObject json) {
        this((String) json.get("index"), (String) json.get("name"), (String) json.get("url"));
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof APIReference))
            return false;

        APIReference reference = (APIReference) object;

        return reference.getIndex().equals(this.index) && reference.getName().equals(this.name) && reference.getUrl().equals(this.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass(), this.index, this.name, this.url);
    }

    @Override
    public String toString() {
        return index + ", " + name + ", " + url;
    }

    // Get methods
    public String getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }
}
