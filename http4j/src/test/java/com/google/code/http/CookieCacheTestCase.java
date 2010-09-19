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

package com.google.code.http;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http.impl.CanonicalHeader;
import com.google.code.http.impl.CookieStoreAdapter;

public final class CookieCacheTestCase {
	
	private CookieCache cache;
	
	private URI uri;
	
	private URI anotherUri;
	
	@BeforeClass
	public void beforeClass() throws MalformedURLException, URISyntaxException {
		cache = new CookieStoreAdapter();
		uri = new URL("http://www.google.com/search?q=http4j").toURI();
		anotherUri = new URL("http://www.baidu.com/s?wd=http4j").toURI();
	}
	
	@Test
	public void get() {
		Assert.assertNull(cache.get(uri));
		Assert.assertNull(cache.get(anotherUri));
	}
	
	@Test(dependsOnMethods = "get")
	public void set() {
		List<Header> headers = new LinkedList<Header>();
		headers.add(new CanonicalHeader(Headers.RESPONSE_COOKIE, "PREF=ID=2067d32f823de961:NW=1:TM=1284034854:LM=1284034854:S=eLKAQ-QOYpFKPwI3; expires=Sat, 08-Sep-2012 12:20:54 GMT; path=/; domain=.google.com"));
		headers.add(new CanonicalHeader(Headers.RESPONSE_COOKIE, "NID=38=g4PriUXNBibltyAS5Rko6b6ygubtZQs0s2FAy0zMYHOKOIY5rQyE1gAskYS0MU6g767OB24_xknddTgcBKuEMsShbEhZMH0ev9A_uMbWF9D61d8VcASWeksFx2kTSR_i; expires=Fri, 11-Mar-2011 12:20:54 GMT; path=/; domain=.google.com; HttpOnly"));
		headers.add(new CanonicalHeader(Headers.CONTENT_LENGTH, "458"));
		cache.set(uri, headers);
		String cookie = cache.get(uri);
		Assert.assertNotNull(cookie);
		Assert.assertEquals(cookie, "PREF=ID=2067d32f823de961:NW=1:TM=1284034854:LM=1284034854:S=eLKAQ-QOYpFKPwI3;NID=38=g4PriUXNBibltyAS5Rko6b6ygubtZQs0s2FAy0zMYHOKOIY5rQyE1gAskYS0MU6g767OB24_xknddTgcBKuEMsShbEhZMH0ev9A_uMbWF9D61d8VcASWeksFx2kTSR_i");
	}
	
	@AfterClass
	public void afterClass() {
		cache.clear();
	}
}
