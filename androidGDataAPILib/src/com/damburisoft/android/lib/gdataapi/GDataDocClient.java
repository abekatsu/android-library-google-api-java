package com.damburisoft.android.lib.gdataapi;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import android.util.Log;

import com.damburisoft.android.lib.gdataapi.model.Category;
import com.damburisoft.android.lib.gdataapi.model.GoogleDocEntryContent;
import com.damburisoft.android.lib.gdataapi.model.GoogleDocFeed;
import com.damburisoft.android.lib.gdataapi.model.Title;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.GoogleUrl;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.xml.atom.AtomContent;
import com.google.api.client.http.xml.atom.AtomParser;
import com.google.api.client.xml.XmlNamespaceDictionary;
import com.google.api.client.xml.atom.Atom;

public class GDataDocClient extends GDataAuthClient {

    private static final String SCOPE = "https://docs.google.com/feeds/";
    private static final String TAG = "GDataDocClient";
    private static boolean debug = true;
    
    public static final String gDataVersion = "3";
    
    public GDataDocClient(String _appname, String _gdataVersion, String _token,
            String _tokenSecret) {
        super(_appname, _gdataVersion, _token, _tokenSecret);
        this.scope = SCOPE;
    }

    public GDataDocClient(String _appname, String _token, String _tokenSecret) {
        super(_appname, gDataVersion, _token, _tokenSecret);
        this.scope = SCOPE;
    }

    public void setUpClient(final String _appname, final String _gdataVersion, 
            final String _token, final String _tokenSecret) {
        appname = _appname;
        requestFactory = transport.createRequestFactory(new HttpRequestInitializer() {
            
            @Override
            public void initialize(HttpRequest request) throws IOException {
                
                request.addParser(createAtomParser());
                
                GoogleHeaders headers = new GoogleHeaders();
                headers.setApplicationName(appname);
                headers.gdataVersion = _gdataVersion;
                request.headers = headers;
                request.interceptor = new HttpExecuteInterceptor() {

                    @Override
                    public void intercept(HttpRequest request) throws IOException {
                        
                        OAuthParameters parameters = createOAuthParameters(_token, _tokenSecret);
                        if (parameters != null) {
                            parameters.intercept(request);
                        }
                    }
                    
                };
            }
        });
    }

    @Override
    protected XmlNamespaceDictionary createXmlNamespaceDictionary() {
            
        XmlNamespaceDictionary dictionary = new XmlNamespaceDictionary();
        dictionary.set("", Atom.ATOM_NAMESPACE);
        dictionary.set("docs", "http://schemas.google.com/docs/2007");
        dictionary.set("batch", "http://schemas.google.com/gdata/batch");
        dictionary.set("gd", "http://schemas.google.com/g/2005");
        dictionary.set("openSearch", "http://a9.com/-/spec/opensearch/1.1/");
        dictionary.set("app", "http://www.w3.org/2007/app");

        return dictionary;
    }

    @Override
    protected AtomParser createAtomParser() {
        AtomParser parser = new AtomParser();
        parser.namespaceDictionary = createXmlNamespaceDictionary();
        return parser;
    }

    /**
     * Create empty spreadsheet with the title as given.
     * @param title
     * @return String feed.id
     */
    public String createEmptySpreadSheets(String title) {
        String ret = null;
        GenericUrl url = new GoogleUrl("https://docs.google.com/feeds/default/private/full");
        try {
            HttpRequest request = requestFactory.buildPostRequest(url, createAtomContent(title, "spreadsheet"));
            GoogleDocFeed feed = request.execute().parseAs(GoogleDocFeed.class);
            if (debug) {
                feed.writeLog(TAG);
            }
            ret = feed.getWorkSheetFeedLink();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "" + e.getMessage());
            e.printStackTrace();
        }
        
        return ret;
    }

    private GoogleDocEntryContent createEmptyDocEntry(String title, String type) {
        GoogleDocEntryContent content = new GoogleDocEntryContent();
        content.category = creatgeCategory(type);
        content.title = new Title("text", title);
        return content;
    }


    private AtomContent createAtomContent(String title, String type) {
        AtomContent content = new AtomContent();
        content.namespaceDictionary = createXmlNamespaceDictionary();
        content.entry = createEmptyDocEntry(title, type);
        return content;
    }
    
    private Category creatgeCategory(String type) {
        
        if (isSupportedType(type) == false) {
            throw new IllegalArgumentException("type: " + type + ", is not supported");
        }
        
        Category category = new Category();
        category.scheme = "http://schemas.google.com/g/2005#kind";
        category.term = "http://schemas.google.com/docs/2007#" + type;
        return category;
    }
    
    private boolean isSupportedType(String type) {
        String[] supportType = {"document", "drawing", "folder", "pdf", "presentation", "spreadsheet", "form"};
        List<String> supportTypeList = Arrays.asList(supportType);
        
        return supportTypeList.contains(type);
    }

}
