package com.damburisoft.android.lib.gdataapi.model;

import java.util.List;

import com.damburisoft.android.lib.gdataapi.Utils;
import com.google.api.client.http.xml.atom.AtomContent;
import com.google.api.client.util.Key;
import com.google.api.client.xml.XmlNamespaceDictionary;
import com.google.api.client.xml.atom.Atom;

public class GoogleDocEntryContent {

    public GoogleDocEntryContent() {
        super();
    }

    @Key("@gd:etag")
    public String etag;

    @Key
    public String id;
    
    @Key
    public String updated;
    
    @Key("@app:edited")
    public String edited;
    
    @Key
    public Category category;

    @Key("title")
    public Title title;
    
    @Key
    public Content content;
    
    @Key("link")
    public List<Link> links;

    @Key
    public Author author;

    
    public String getEditLink() {
        return getLinkRel("edit");
    }

    public String getSelfLink() {
        return getLinkRel("self");
    }
    
    protected String getLinkRel(String rel) {
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
        AtomContent debug_content = new AtomContent();
        debug_content.entry = this;
        debug_content.namespaceDictionary = getNamespaceDictionary();
        Utils.writeLog(tag, debug_content);
    }

    public XmlNamespaceDictionary getNamespaceDictionary() {
        XmlNamespaceDictionary dictionary = new XmlNamespaceDictionary();
        dictionary.set("", Atom.ATOM_NAMESPACE);
        dictionary.set("gd", "http://schemas.google.com/g/2005");
        dictionary.set("openSearch", "http://a9.com/-/spec/opensearch/1.1/");
        dictionary.set("app", "http://www.w3.org/2007/app");

        return dictionary;
    }

}
