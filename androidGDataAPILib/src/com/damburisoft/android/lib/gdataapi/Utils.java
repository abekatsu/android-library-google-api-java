package com.damburisoft.android.lib.gdataapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.xmlpull.v1.XmlSerializer;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.xml.atom.AtomContent;
import com.google.api.client.http.xml.atom.AtomFeedContent;

import android.util.Log;
import android.util.Xml;

public class Utils {
    
    public static String getResponseContentsString(String TAG, HttpResponse response) {
        // String charset = getCharset(entity);
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader inr = new InputStreamReader(response.getContent(), "UTF-8");
            BufferedReader reader = new BufferedReader(inr, 4096); // TODO 4096 is hard-coded.
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            Log.d(TAG, sb.toString());
            inr.close();
        } catch (UnsupportedEncodingException e) {
            if (e.getMessage() != null) {
                Log.e(TAG, e.getMessage());
            }
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getCurrentDateString() {
        //  <updated>2007-07-30T18:51:30.666Z</updated>
        // 「yyyy-MM-dd'T'HH:mm:ss.SSSZ」
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }
    
    public static void writeLog(String TAG, AtomContent content) {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            serializer.setOutput(writer);
            content.writeTo(serializer);
            Log.d(TAG, writer.toString());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
   
    }

    public static void writeLog(String TAG, AtomFeedContent content) {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
            serializer.setOutput(writer);
            content.writeTo(serializer);
            Log.d(TAG, writer.toString());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
   
    }
    
    public static String getAlphabeticalIndex(int col_idx) {
        int tmp_idx = col_idx;
        StringBuilder sb = new StringBuilder();
        
        while (tmp_idx > 0) {
            char idx = (char) (tmp_idx % 26);
            sb.insert(0, String.valueOf((char)(idx + 'A' - 1)));
            tmp_idx = (tmp_idx / 26);
        }
        
        return sb.toString();
    }
    
    public static String stringToHTMLString(String string) {
        StringBuffer sb = new StringBuffer(string.length());
        // true if last char was blank
        boolean lastWasBlankChar = false;
        int len = string.length();
        char c;

        for (int i = 0; i < len; i++)
            {
            c = string.charAt(i);
            if (c == ' ') {
                // blank gets extra work,
                // this solves the problem you get if you replace all
                // blanks with &nbsp;, if you do that you loss 
                // word breaking
                if (lastWasBlankChar) {
                    lastWasBlankChar = false;
                    sb.append("&nbsp;");
                    }
                else {
                    lastWasBlankChar = true;
                    sb.append(' ');
                    }
                }
            else {
                lastWasBlankChar = false;
                //
                // HTML Special Chars
                if (c == '"')
                    sb.append("&#x22");
                else if (c == '&')
                    sb.append("&amp;");
                else if (c == '<')
                    sb.append("&lt;");
                else if (c == '>')
                    sb.append("&gt;");
                else if (c == '\n')
                    // Handle Newline
                    sb.append("&lt;br/&gt;");
                else {
                    int ci = 0xffff & c;
                    if (ci < 160 )
                        // nothing special only 7 Bit
                        sb.append(c);
                    else {
                        // Not 7 Bit use the unicode system
                        sb.append("&#");
                        sb.append(new Integer(ci).toString());
                        sb.append(';');
                        }
                    }
                }
            }
        return sb.toString();
    }


}
