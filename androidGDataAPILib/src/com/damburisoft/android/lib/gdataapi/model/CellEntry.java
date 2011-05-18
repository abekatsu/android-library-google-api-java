package com.damburisoft.android.lib.gdataapi.model;

import com.damburisoft.android.lib.gdataapi.Utils;
import com.google.api.client.http.xml.atom.AtomContent;
import com.google.api.client.util.Key;
import com.google.api.client.xml.XmlNamespaceDictionary;

public class CellEntry extends GoogleDocEntryContent {

    public CellEntry() {
        super();
    }
    
    

    public CellEntry(Cell cell) {
        super();
        this.cell = cell;
    }



    @Key("gs:cell")
    public Cell cell;

    @Override
    public void writeLog(String tag) {
        AtomContent debug_content = new AtomContent();
        debug_content.entry = this;
        debug_content.namespaceDictionary = getNamespaceDictionary();
        Utils.writeLog(tag, debug_content);
    }

    @Override
    public XmlNamespaceDictionary getNamespaceDictionary() {
        XmlNamespaceDictionary dictionary = super.getNamespaceDictionary();

        dictionary.set("gs", "http://schemas.google.com/spreadsheets/2006");
        dictionary.set("batch", "http://schemas.google.com/gdata/batch");
        
        return dictionary;
    }

}
