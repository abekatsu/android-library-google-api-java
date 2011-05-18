package com.damburisoft.android.lib.gdataapi.model;

import com.google.api.client.util.Key;

public class Content {
    
    
    public Content() {
        super();
    }


    public Content(String type, String src, String lang, String value) {
        super();
        this.type = type;
        this.src = src;
        this.lang = lang;
        this.value = value;
    }


    @Key("@type")
    public String type;


    @Key("@src")
    public String src;


    @Key("@xml:lang")
    public String lang;

    
    @Key("text()")
    public String value;

}
