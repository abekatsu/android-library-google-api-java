package com.damburisoft.android.lib.gdataapi.model;

import com.google.api.client.util.Key;

public class Author {

    @Key
    public String name;
    
    @Key
    public String email;

    @Key
    public String uri;

    public Author() {
        super();
    }

    public Author(String name, String email, String uri) {
        super();
        this.name = name;
        this.email = email;
        this.uri = uri;
    }
    

}
