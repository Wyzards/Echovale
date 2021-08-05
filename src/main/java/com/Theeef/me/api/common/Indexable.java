package com.Theeef.me.api.common;

public abstract class Indexable {

    public abstract Indexable fromUrl(String url);

    public abstract Indexable fromIndex(String index);

    public Indexable fromReference(APIReference reference) {
        return fromUrl(reference.getUrl());
    }

}
