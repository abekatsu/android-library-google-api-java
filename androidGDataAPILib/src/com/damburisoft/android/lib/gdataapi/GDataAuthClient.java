/*
 * Copyright (c) 2011 Katsuhisa ABE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */


/*
 * Copyright (c) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */



/**
 * Google I/O 2010 mini-sample of an Android application based on the new Google
 * Data Java client library for the Buzz API.
 * <p>
 * To enable logging of HTTP requests/responses, change
 * {@link BuzzParameters#DEBUG} to true and run this command: {@code adb shell
 * setprop log.tag.HttpTransport DEBUG}.
 * </p>
 *
 * @author Yaniv Inbar
 */


package com.damburisoft.android.lib.gdataapi;

import java.io.IOException;

import com.google.api.client.auth.oauth.OAuthCallbackUrl;
import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.googleapis.auth.oauth.GoogleOAuthAuthorizeTemporaryTokenUrl;
import com.google.api.client.googleapis.auth.oauth.GoogleOAuthGetAccessToken;
import com.google.api.client.googleapis.auth.oauth.GoogleOAuthGetTemporaryToken;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.xml.atom.AtomParser;
import com.google.api.client.xml.XmlNamespaceDictionary;

abstract public class GDataAuthClient {

    public static final HttpTransport transport = new ApacheHttpTransport();
    public HttpRequestFactory requestFactory;
    public String appname;
    public String callback;
    public String scope;

    public GDataAuthClient(String _appname) {
        super();
        setUpClient(_appname, "3", null, null);
    }

    public GDataAuthClient(final String _appname, final String _gdataVersion) {
        super();
        setUpClient(_appname, _gdataVersion, null, null);
    }
    
    public GDataAuthClient(final String _appname, final String _gdataVersion, 
            final String _token, final String _tokenSecret) {
        super();
        setUpClient(_appname, _gdataVersion, _token, _tokenSecret);
    }
    
    
    abstract public void setUpClient(final String _appname, final String _gdataVersion, 
            final String _token, final String _tokenSecret);


    public OAuthCredentialsResponse getCredentialsResponse() throws IOException {
        return _getCredentialsResponse(this.scope);
    }

    protected OAuthCredentialsResponse _getCredentialsResponse(String scope) throws IOException {
        GoogleOAuthGetTemporaryToken temporaryToken = new GoogleOAuthGetTemporaryToken();
        temporaryToken.signer = createOAuthSigner();
        temporaryToken.displayName = appname;
        temporaryToken.consumerKey = "anonymous";
        temporaryToken.scope = scope;
        temporaryToken.transport = transport;
        temporaryToken.callback = callback;

        return temporaryToken.execute();
    }
    
    public GoogleOAuthAuthorizeTemporaryTokenUrl getOAuthAuthorizeTemporaryTokenUrl(
            OAuthCredentialsResponse credentials) throws IOException {
        return _getOAuthAuthorizeTemporaryTokenUrl(scope, credentials);
    }

    protected GoogleOAuthAuthorizeTemporaryTokenUrl _getOAuthAuthorizeTemporaryTokenUrl(String scope,
            OAuthCredentialsResponse _credentials) throws IOException {
        GoogleOAuthAuthorizeTemporaryTokenUrl authorizeUrl = new GoogleOAuthAuthorizeTemporaryTokenUrl();
        authorizeUrl.template = "mobile";
        authorizeUrl.set("scope", scope);
        authorizeUrl.set("domain", "anonymous");
        authorizeUrl.set("xoauth_displayname", appname);
        authorizeUrl.temporaryToken = _credentials.token;
        return authorizeUrl;
    }
    
    public GoogleOAuthGetAccessToken getOAuthAccessToken(OAuthCallbackUrl callbackUrl, 
            OAuthCredentialsResponse credentials) throws IOException {
        return _getOAuthAccessToken(callbackUrl, credentials);
    }

    protected GoogleOAuthGetAccessToken _getOAuthAccessToken(OAuthCallbackUrl callbackUrl, OAuthCredentialsResponse credentials) {
        GoogleOAuthGetAccessToken accessToken = new GoogleOAuthGetAccessToken();
        accessToken.transport = transport;
        accessToken.temporaryToken = callbackUrl.token;
        accessToken.verifier = callbackUrl.verifier;
        accessToken.signer = createOAuthSigner(credentials);
        accessToken.consumerKey = "anonymous";

        return accessToken;
    }
    
    private OAuthHmacSigner createOAuthSigner(OAuthCredentialsResponse credentials) {
        OAuthHmacSigner result = new OAuthHmacSigner();
        if (credentials != null) {
            result.tokenSharedSecret = credentials.tokenSecret;
        }
        result.clientSharedSecret = "anonymous";
        return result;
    }
    
    private OAuthHmacSigner createOAuthSigner() {
        return createOAuthSigner((String)null);
    }


    public OAuthHmacSigner createOAuthSigner(String tokenSecret) {
        OAuthHmacSigner signer = new OAuthHmacSigner();
        if (tokenSecret != null) {
            signer.tokenSharedSecret = tokenSecret;
        }
        signer.clientSharedSecret = "anonymous";
        return signer;
    }
    
    public OAuthParameters createOAuthParameters(String token, String tokenSecret) {
        OAuthParameters authorizer = new OAuthParameters();
        authorizer.consumerKey = "anonymous";
        authorizer.signer = createOAuthSigner(tokenSecret);
        authorizer.token = token;
        return authorizer;
    }
    
    protected abstract XmlNamespaceDictionary createXmlNamespaceDictionary();
    
    protected abstract AtomParser createAtomParser();


}
