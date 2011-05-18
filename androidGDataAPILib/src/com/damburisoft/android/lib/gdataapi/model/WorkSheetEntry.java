package com.damburisoft.android.lib.gdataapi.model;

import java.io.IOException;

import com.damburisoft.android.lib.gdataapi.GDataSpreadSheetClient;
import com.damburisoft.android.lib.gdataapi.Utils;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.xml.atom.AtomContent;
import com.google.api.client.util.Key;
import com.google.api.client.xml.XmlNamespaceDictionary;

public class WorkSheetEntry extends GoogleDocEntryContent {
    
    private static final boolean debug = true;

    private static final String TAG = "WorkSheetEntry";

    @Key("gs:rowCount")
    public int rowCount;
    
    @Key("gs:colCount")
    public int colCount;

    public WorkSheetEntry renameWorkSheetTitle(GDataSpreadSheetClient client, String new_title) {
        
        // setup Content for PUT request.
        AtomContent content = new AtomContent();
        WorkSheetEntry new_entry = this;
        if (new_entry.title == null) {
            new_entry.title = new Title();
        }
        new_entry.title.title = new_title;
        content.entry = new_entry;
        content.namespaceDictionary = new_entry.getNamespaceDictionary();
        
        if (debug) {
            new_entry.writeLog(TAG);
        }
        

        HttpRequest request;
        try {
            GenericUrl url = new SpreadSheetUrl(getEditLink());
            request = client.requestFactory.buildPutRequest(url, content);
            HttpResponse response = request.execute();
            WorkSheetEntry modifiedEntry = response.parseAs(WorkSheetEntry.class);
            if (debug) {
                modifiedEntry.writeLog(TAG);
            }
            return modifiedEntry;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
    
    public String getCellsFeedUrl() {
        return getLinkRel("http://schemas.google.com/spreadsheets/2006#cellsfeed");
    }

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
        return dictionary;

    }
    
    
}
