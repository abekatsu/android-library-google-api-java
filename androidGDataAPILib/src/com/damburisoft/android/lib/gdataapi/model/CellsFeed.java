package com.damburisoft.android.lib.gdataapi.model;

import java.util.List;

import com.damburisoft.android.lib.gdataapi.Utils;
import com.google.api.client.http.xml.atom.AtomFeedContent;
import com.google.api.client.util.Key;
import com.google.api.client.xml.XmlNamespaceDictionary;

public class CellsFeed extends GoogleDocFeed {
    
    @Key("entry")
    public List<CellEntry> cells;
    
    @Key("gs:colCount")
    public int colCount;
    
    @Key("gs:rowCount")
    public int rowCount;

    public CellsFeed() {
        super();
    }

    public CellsFeed(List<CellEntry> cells) {
        super();
        this.cells = cells;
    }
    
    @Override
    public void writeLog(String tag) {
        AtomFeedContent debug_feed = new AtomFeedContent();
        debug_feed.feed = this;
        debug_feed.namespaceDictionary = getNamespaceDictionary();
        Utils.writeLog(tag, debug_feed);
    }
    
    @Override
    public XmlNamespaceDictionary getNamespaceDictionary() {
        XmlNamespaceDictionary namespaceDictionary = super.getNamespaceDictionary();
        namespaceDictionary.set("gs", "http://schemas.google.com/spreadsheets/2006");
        namespaceDictionary.set("batch", "http://schemas.google.com/gdata/batch");
        return namespaceDictionary;
    }



    public CellEntry getEnrty(int row_idx, int col_idx) {
        if (this.cells != null) {
            for(CellEntry entry : this.cells) {
                if (entry.cell.row == row_idx && entry.cell.col == col_idx) {
                    return entry;
                }
            }
        }
        return null;
    }
    

}
