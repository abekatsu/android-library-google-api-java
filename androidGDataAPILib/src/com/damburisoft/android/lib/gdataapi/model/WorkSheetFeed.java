package com.damburisoft.android.lib.gdataapi.model;

import java.util.List;

import com.damburisoft.android.lib.gdataapi.Utils;
import com.google.api.client.util.Key;
import com.google.api.client.xml.XmlNamespaceDictionary;
import com.google.api.client.xml.atom.Atom;
import com.google.api.client.http.xml.atom.AtomFeedContent;

public class WorkSheetFeed extends GoogleDocFeed {

    
    
    public WorkSheetFeed() {
        super();
    }

    @Key("entry")
    public List<WorkSheetEntry> sheets;

    
    public boolean isWorkSheetExist(String title) {
        
        if (sheets == null) {
            return false;
        }
        
        for (WorkSheetEntry entry : sheets) {
            if (entry.title != null &&
                    entry.title.title != null &&
                    (entry.title.title.equals(title))) {
                return true;
            }
        }
        
        return false;
    }
    
    public WorkSheetEntry getWorkSheetEntry(String title) {
        
        if (sheets == null) {
            return null;
        }
        
        for (WorkSheetEntry entry : sheets) {
            if (entry.title != null &&
                    entry.title.title != null &&
                    (entry.title.title.equals(title))) {
                return entry;
            }
        }

        return null;
    }

    @Override
    public void writeLog(String tag) {
        AtomFeedContent debug_feed = new AtomFeedContent();
        
        debug_feed.feed = this;
        debug_feed.namespaceDictionary = new XmlNamespaceDictionary();
        debug_feed.namespaceDictionary.set("", Atom.ATOM_NAMESPACE);
        debug_feed.namespaceDictionary.set("docs", "http://schemas.google.com/docs/2007");
        debug_feed.namespaceDictionary.set("batch", "http://schemas.google.com/gdata/batch");
        debug_feed.namespaceDictionary.set("gd", "http://schemas.google.com/g/2005");
        debug_feed.namespaceDictionary.set("gs", "http://schemas.google.com/spreadsheets/2006");
        debug_feed.namespaceDictionary.set("openSearch", "http://a9.com/-/spec/opensearch/1.1/");
        debug_feed.namespaceDictionary.set("app", "http://www.w3.org/2007/app");
        Utils.writeLog(tag, debug_feed);

    }
    
    

}
