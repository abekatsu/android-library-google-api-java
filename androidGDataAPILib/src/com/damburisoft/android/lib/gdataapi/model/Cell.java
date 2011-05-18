package com.damburisoft.android.lib.gdataapi.model;

import com.google.api.client.util.Key;

public class Cell {

    public Cell() {
        super();
    }
    
    public Cell(int row_idx, int col_idx, String value) {
        super();
        this.row = row_idx;
        this.col = col_idx;
        this.value = value;
        this.inputValue = value;
    }

    // <gs:cell row="1" col="2" inputValue="Hours">Hours</gs:cell>
    @Key("@row")
    public int row;
    
    @Key("@col")
    public int col;
    
    @Key("@inputValue")
    public String inputValue;
    
    @Key("text()")
    public String value;

}
