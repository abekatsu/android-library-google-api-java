package com.damburisoft.android.lib.gdataapi.model;

import java.util.List;

import com.damburisoft.android.lib.gdataapi.Utils;

import com.google.api.client.http.xml.atom.AtomFeedContent;
import com.google.api.client.util.Key;
import com.google.api.client.xml.XmlNamespaceDictionary;
import com.google.api.client.xml.atom.Atom;

public class GoogleDocFeed {



    public GoogleDocFeed() {
        super();
    }

    @Key("@gd:etag")
    public String etag;
    
    @Key
    public String id;
    
    @Key
    public String published;
    
    @Key
    public String updated;
    
    @Key("app:edited")
    public String edited;
    
    @Key("category")
    public List<Category> categories;
    
    @Key("title")
    public Title title;

    @Key
    public Content content;
    
    @Key("link")
    public List<Link> links;
    
    @Key
    public Author author;
    
    @Key("gd:resourceId")
    public String resourceId;
    
    @Key("gd:lastModifiedBy")
    public ModifiedBy lastModifiedBy;
    
    @Key("gd:lastViewed")
    public String lastViewed;
    
    @Key("gd:quotaBytesUsed")
    public int quotaBytesUsed;
    
    @Key("docs:writersCanInvite")
    public CanInvite writersCanInvite;
    
    @Key("gd:feedLink")
    public List<Link> feedLinks;
    
    @Key("openSearch:totalResults")
    public int totalResults;
    
    @Key("openSearch:startIndex")
    public int startIndex;
    
    @Key("openSearch:itemsPerPage")
    public int itemsPerPage;
    
    public String getEditLink() {
        return getLinkRel("edit");
    }

    public String getSelfLink() {
        return getLinkRel("self");
    }
    
    public String getWorkSheetFeedLink() {
        /*
         * <link href="https://spreadsheets.google.com/feeds/worksheets/0AlhbkPlilHYidEJXeTlJelFUeHEyVmRCTk9vRmlpQmc/private/full" 
         *       rel="http://schemas.google.com/spreadsheets/2006#worksheetsfeed" 
         *       type="application/atom+xml" />
         */
        return getLinkRel("http://schemas.google.com/spreadsheets/2006#worksheetsfeed");
    }

    
    /**
     * @param rel
     * @return
     */
    public String getLinkRel(String rel) {
        if (links == null) {
            return null;
        }
        
        for (Link link : links) {
            if (link.rel != null && link.rel.equalsIgnoreCase(rel)) {
                return link.href;
            }
        }
        return null;
    }

    
    public void writeLog(String tag) {
        AtomFeedContent debug_feed = new AtomFeedContent();
        debug_feed.feed = this;
        debug_feed.namespaceDictionary = getNamespaceDictionary();
        
        Utils.writeLog(tag, debug_feed);
    }
    
    
    public XmlNamespaceDictionary getNamespaceDictionary() {
        XmlNamespaceDictionary namespaceDictionary = new XmlNamespaceDictionary();
        namespaceDictionary.set("", Atom.ATOM_NAMESPACE);
        namespaceDictionary.set("openSearch", "http://a9.com/-/spec/opensearch/1.1/");
        namespaceDictionary.set("docs", "http://schemas.google.com/docs/2007");
        namespaceDictionary.set("gd", "http://schemas.google.com/g/2005");
        namespaceDictionary.set("app", "http://www.w3.org/2007/app");
        return namespaceDictionary;
    }





}
