package com.damburisoft.android.lib.gdataapi.model;

import com.google.api.client.util.Key;

public class Link {

    @Key("@href")
    public String href;
    
    @Key("@type")
    public String type;
    
    @Key("@rel")
    public String rel;
    
    public Link(String _rel, String _type, String _href) {
        super();
        this.rel = _rel;
        this.type = _type;
        this.href = _href;
    }
    
    public Link() {
        super();
    }

}
