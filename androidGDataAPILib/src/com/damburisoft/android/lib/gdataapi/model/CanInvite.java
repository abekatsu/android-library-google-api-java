package com.damburisoft.android.lib.gdataapi.model;

import com.google.api.client.util.Key;

public class CanInvite {

    @Key("@value")
    public boolean value;

    public CanInvite() {
        super();
    }

    public CanInvite(boolean value) {
        super();
        this.value = value;
    }

}
