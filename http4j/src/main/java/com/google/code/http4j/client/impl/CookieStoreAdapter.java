/**
 * Copyright (C) 2010 Zhang, Guilin <guilin.zhang@hotmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.http4j.client.impl;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.Collection;
import java.util.List;

import com.google.code.http4j.client.CookieCache;
import com.google.code.http4j.client.Http;
import com.google.code.http4j.client.HttpHeader;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public class CookieStoreAdapter implements CookieCache {

	protected CookieStore store;

	public CookieStoreAdapter() {
		this(CookiePolicy.ACCEPT_ALL);
	}

	public CookieStoreAdapter(CookiePolicy policy) {
		this.store = createCookieStore(policy);
	}

	protected HttpHeader convertToHeader(List<HttpCookie> cookies) {
		StringBuilder buffer = new StringBuilder();
		for (HttpCookie cookie : cookies) {
			buffer.append(cookie).append(Http.COOKIE_SPLITTER);
		}
		return createHttpHeader(Http.HEADER_NAME_REQUEST_COOKIE, buffer.substring(0, buffer.length() - 1));
	}

	protected CookieStore createCookieStore(CookiePolicy policy) {
		CookieManager manager = new CookieManager();
		manager.setCookiePolicy(policy);
		CookieHandler.setDefault(manager);
		return manager.getCookieStore();
	}

	protected HttpHeader createHttpHeader(String name, String value) {
		return new BasicHttpHeader(name, value);
	}
	
	@Override
	public HttpHeader getCookies(URI uri) {
		List<HttpCookie> cookies = store.get(uri);
		return null == cookies || cookies.isEmpty()? null : convertToHeader(cookies);
	}

	protected void processHeader(URI uri, HttpHeader header) {
		if (Http.HEADER_NAME_RESPONSE_COOKIE.equalsIgnoreCase(header.getName())) {
			List<HttpCookie> cookies = HttpCookie.parse(header.getValue());
			for(HttpCookie cookie : cookies) {
				store.add(uri, cookie);
			}
		}
	}

	@Override
	public int storeCookies(URI uri, Collection<HttpHeader> headers) {
		for (HttpHeader header : headers) {
			processHeader(uri, header);
		}
		return headers.size();
	}
}
