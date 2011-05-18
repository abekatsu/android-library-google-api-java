package com.damburisoft.android.lib.gdataapi.model;

import com.google.api.client.util.Key;

public class Title {

    public Title(String _type, String _title) {
        super();
        this.title = _title;
        this.type = _type;
    }
    
    public Title() {
        super();
    }

    @Key("text()")
    public String title;
    
    @Key("@type")
    public String type;

}
