package com.damburisoft.android.lib.gdataapi.model;

import com.google.api.client.util.Key;

public class BatchOperation {
    
    /*
     *   <batch:operation type="update"/>
     */
    
    public BatchOperation() {
        super();
    }
    
    public BatchOperation(String _type) {
        super();
        this.type = _type;
    }

    @Key("@type")
    public String type;


}
