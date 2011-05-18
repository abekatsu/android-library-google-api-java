package com.damburisoft.android.lib.gdataapi;

import java.io.IOException;
import java.util.List;

import android.util.Log;

import com.damburisoft.android.lib.gdataapi.model.CellEntry;
import com.damburisoft.android.lib.gdataapi.model.CellsFeed;
import com.damburisoft.android.lib.gdataapi.model.SpreadSheetEntry;
import com.damburisoft.android.lib.gdataapi.model.SpreadSheetFeed;
import com.damburisoft.android.lib.gdataapi.model.SpreadSheetUrl;
import com.damburisoft.android.lib.gdataapi.model.Title;
import com.damburisoft.android.lib.gdataapi.model.WorkSheetEntry;
import com.damburisoft.android.lib.gdataapi.model.WorkSheetFeed;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.xml.atom.AtomContent;
import com.google.api.client.http.xml.atom.AtomFeedContent;
import com.google.api.client.xml.XmlNamespaceDictionary;

/**
 * SpreadSheetClient
 * 
 * @author abekatsu
 *
 */
public class GDataSpreadSheetClient extends GDataDocClient {

    private static final String SCOPE = "https://docs.google.com/feeds/ https://spreadsheets.google.com/feeds/";
    // "https://docs.google.com/feeds/
    // https://spreadsheets.google.com/feeds/ https://docs.googleusercontent.com");
    private static final String TAG = "GDataSpreadSheetClient";
    private static final boolean debug = true;
    
    public GDataSpreadSheetClient(String _appname, String _gdataVersion, String _token,
            String _tokenSecret) {
        super(_appname, _gdataVersion, _token, _tokenSecret);
        this.scope = SCOPE;
    }

    public GDataSpreadSheetClient(String _appname, String _token, String _tokenSecret) {
        super(_appname, gDataVersion, _token, _tokenSecret);
        this.scope = SCOPE;
    }
    
    
    
    @Override
    protected XmlNamespaceDictionary createXmlNamespaceDictionary() {
        XmlNamespaceDictionary dictionary = super.createXmlNamespaceDictionary();
        dictionary.set("gs", "http://schemas.google.com/spreadsheets/2006");
        return dictionary;
    }
    
    
    /**
     * Get list of SpreadSheetEntries
     * @return List<SpreadSheetEntry>
     */
    public List<SpreadSheetEntry> getSpreadSheets() {
        GenericUrl url = new SpreadSheetUrl("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
        HttpRequest request;
        try {
            request = requestFactory.buildGetRequest(url);
            SpreadSheetFeed sheetFeed = request.execute().parseAs(SpreadSheetFeed.class);
            if (sheetFeed != null) {
                return sheetFeed.sheets;
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    
    /**
     * Get WorkSheetFeed 
     * @param url_str
     * @return WorkSheetFeed
     */
    public WorkSheetFeed getWorkSheetFeed(String url_str) {
        GenericUrl url = new SpreadSheetUrl(url_str);
        try {
            HttpRequest request = requestFactory.buildGetRequest(url);
            WorkSheetFeed feed = request.execute().parseAs(WorkSheetFeed.class);
            if (debug) {
                feed.writeLog(TAG);
            }
            return feed;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    /**
     * Get CellsFeed 
     * @param url of cellsfeed in link
     * @return CellsFeed feed
     */
    public CellsFeed getCellsFeed(String cellsfeed_link) {
        CellsFeed feed = null;
        GenericUrl url = new SpreadSheetUrl(cellsfeed_link);
        
        try {
            HttpRequest request = requestFactory.buildGetRequest(url);
            feed = request.execute().parseAs(CellsFeed.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feed;
    }

    /**
     * get CellsFeed under max-row and max-col are specified.
     * 
     * @param cellsfeed_url
     * @param max_row
     * @param max_col
     * @return CellsFeed
     */
    public CellsFeed getCellsFeed(String cellsfeed_url, int max_row, int max_col) {
        /*
         * GET https://spreadsheets.google.com/feeds/cells/key/worksheetId/private/full?min-row=2&min-col=4&max-col=4
         */
        String new_cellsfeed_url = cellsfeed_url + "?max-row=" + max_row + "&max-col=" + max_col;
        return getCellsFeed(new_cellsfeed_url);
    }
    
    
    
    
    /**
     * Put new CellEntry to spreadsheet.
     *  
     * @param url_str 
     * @param entry
     * @return updated CellEntry
     */
    public CellEntry putNewCellEntry(String url_str, CellEntry entry) {
        CellEntry updated_entry = null;
        GenericUrl url = new SpreadSheetUrl(url_str);
        AtomContent content = new AtomContent();
        content.entry = entry;
        content.namespaceDictionary = entry.getNamespaceDictionary();
        
        HttpRequest request = null;
        try {
            request = requestFactory.buildPutRequest(url, content);
            // TODO this is workaround to deal with GAE bug, which cannot handle etag rightly. 
            request.headers.set("If-Match", "*");
            updated_entry = request.execute().parseAs(CellEntry.class);
            if (debug) {
                updated_entry.writeLog(TAG);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // TODO this is workaround to deal with GAE bug, which cannot handle etag rightly. 
            if (request != null && request.headers != null) {
                request.headers.set("If-Match", null);
            }
        }
        return updated_entry;
        
    }

    
    
    /**
     * Batch-Update Cell Entries
     * @param url_str
     * @param feed
     * @return updated CellsFeed
     */
    public CellsFeed updateCellBatchFeed(String url_str, CellsFeed feed) {
        CellsFeed update_feed = null;
        GenericUrl url = new SpreadSheetUrl(url_str);
        AtomFeedContent content = new AtomFeedContent();
        content.feed = feed;
        content.namespaceDictionary = feed.getNamespaceDictionary();
        
        HttpRequest request = null;
        try {
            request = requestFactory.buildPostRequest(url, content);
            // TODO this is workaround to deal with GAE bug, which cannot handle etag rightly. 
            request.headers.set("If-Match", "*");
            update_feed = request.execute().parseAs(CellsFeed.class);
            if (debug) {
                update_feed.writeLog(TAG);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // TODO this is workaround to deal with GAE bug, which cannot handle etag rightly. 
            if (request != null && request.headers != null) {
                request.headers.set("If-Match", null);
            }
        }
        return update_feed;
    }
    
    
    /**
     * Create new WorkSheetEntry to specified spreadsheet.
     * @param url
     * @param title
     * @param rowCount
     * @param colCount
     * @return created WorkSheetEntry
     */
    public WorkSheetEntry createWorkSheetEntry(String url, String title, int rowCount, int colCount) {
        WorkSheetEntry newEntry, retEntry = null;

        newEntry = new WorkSheetEntry();
        newEntry.title = new Title("text", title);
        newEntry.rowCount = rowCount;
        newEntry.colCount = colCount;
        
        AtomContent content = new AtomContent();
        content.entry = newEntry;
        content.namespaceDictionary = createXmlNamespaceDictionary();

        HttpRequest request = null;
        try {
            request = requestFactory.buildPostRequest(new SpreadSheetUrl(url), content);
            // TODO this is workaround to deal with GAE bug, which cannot handle etag rightly. 
            request.headers.set("If-Match", "*");
            retEntry = request.execute().parseAs(WorkSheetEntry.class);
            if (debug) {
                retEntry.writeLog(TAG);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // TODO this is workaround to deal with GAE bug, which cannot handle etag rightly. 
            if (request != null && request.headers != null) {
                request.headers.set("If-Match", null);
            }
        }
        return retEntry;

    }

    /**
     * Create new WorkSheetEntry, which has 100 rows and 10 columns, to specified spreadsheet. 
     * @param url
     * @param title
     * @return created WorkSheetEntry
     */
    public WorkSheetEntry createWorkSheetEntry(String url, String title) {
        return createWorkSheetEntry(url, title, 100, 10);
    }
}
