package com.damburisoft.android.lib.gdataapi.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.google.api.client.util.Key;
import com.google.api.client.xml.XmlNamespaceDictionary;


public class SpreadSheetFeed extends GoogleDocFeed {
    
    private static final String TAG = "SpreadSheetFeed";
    private static final boolean debug = false;

    
    
    public SpreadSheetFeed() {
        super();
    }

    @Key("entry")
    public List<SpreadSheetEntry> sheets;

    public boolean findAlternateLink(String key) {
        if (sheets == null) {
            return false;
        }
        
        for (SpreadSheetEntry sheet : sheets) {
            if (sheet.links != null) {
                for (Link link : sheet.links) {
                    if (link.type.compareToIgnoreCase("text/html") == 0 
                            && link.rel.compareToIgnoreCase("alternate") == 0) {
                        URL url = buildURL(link.href);
                        HashMap<String, String> map = getQueryParametersAndValues(url);
                        if (map.containsKey("key")) {
                            return map.get("key").equals(key);
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private HashMap<String, String> getQueryParametersAndValues(URL url) {
        HashMap<String, String> map = new HashMap<String, String>();
        
        String query = url.getQuery();
        String[] params = query.split("&");
        for (String p : params) {
            String[] pairs = p.split("=");
            if (debug) {
                Log.d(TAG, "key: " + pairs[0] + ", values: " + pairs[1]);
            }
            map.put(pairs[0], pairs[1]);
        }
        return map;
    }

    public SpreadSheetEntry findSpreadSheetEntryByAlternateLink(String key) {
        if (sheets == null) {
            return null;
        }
        
        for (SpreadSheetEntry sheet : sheets) {
            if (sheet.links != null) {
                for (Link link : sheet.links) {
                    if (link.type.compareToIgnoreCase("text/html") == 0 
                            && link.rel.compareToIgnoreCase("alternate") == 0) {
                        URL url = buildURL(link.href);
                        HashMap<String, String> map = getQueryParametersAndValues(url);
                        if (map.containsKey("key")) {
                            return sheet;
                        }
                    }
                }
            }
        }
        return null;
     }
    
    private URL buildURL(String href) {
        URL retUrl = null;
        try {
            retUrl = new URL(href);
        } catch (MalformedURLException e) {
            if (debug) {
                Log.e(TAG, e.getMessage());
            }
            e.printStackTrace();
        }
        
        return retUrl;
    }

    @Override
    public XmlNamespaceDictionary getNamespaceDictionary() {
        XmlNamespaceDictionary namespaceDictionary = super.getNamespaceDictionary();
        namespaceDictionary.set("gs", "http://schemas.google.com/spreadsheets/2006");
        return namespaceDictionary;
    }

    
    
}
