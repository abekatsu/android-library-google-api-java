package com.damburisoft.android.lib.gdataapi.model;

import java.util.ArrayList;

import com.google.api.client.util.Key;

public class CellBatchEntry extends CellEntry {
    
    
    
    public CellBatchEntry() {
        super();
    }

    public CellBatchEntry(String _batch_id, String _operation_type, String title,
            String _id, Link editLink, Cell update_cell) {
        super();
        
        this.batch_id = _batch_id;
        this.operation = new BatchOperation(_operation_type);
        this.title = new Title("text", title);

        this.links = new ArrayList<Link>();
        this.links.add(editLink);
        
        this.id = _id;
        
        this.cell = update_cell;
    }

    /*
     * <entry>
     *   <batch:id>A1</batch:id>
     *   <batch:operation type="update"/>
     *   <id>https://spreadsheets.google.com/feeds/cells/key/worksheetId/private/full/R2C4</id>
     *   <link rel="edit" type="application/atom+xml"
     *      href="https://spreadsheets.google.com/feeds/cells/key/worksheetId/private/full/R2C4/version"/>
     *   <gs:cell row="2" col="4" inputValue="newData"/>
     * </entry>
     */
    @Key("batch:id")
    public String batch_id;
    
    @Key("batch:operation")
    public BatchOperation operation;



}
