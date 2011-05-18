package com.damburisoft.android.lib.gdataapi.model;

import com.google.api.client.util.Key;

public class Category {

    @Key("@scheme")
    public String scheme;
    
    @Key("@term")
    public String term;

    public Category() {
        super();
    }

    public Category(String scheme, String term) {
        super();
        this.scheme = scheme;
        this.term = term;
    }


}
