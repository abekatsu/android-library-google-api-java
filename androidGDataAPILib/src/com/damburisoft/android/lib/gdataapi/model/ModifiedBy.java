package com.damburisoft.android.lib.gdataapi.model;

import com.google.api.client.util.Key;

public class ModifiedBy {
    
    public ModifiedBy() {
        super();
    }

    
    public ModifiedBy(String name, String email) {
        super();
        this.name = name;
        this.email = email;
    }


    @Key
    public String name;
    
    @Key
    public String email;

}
