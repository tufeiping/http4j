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

package com.google.code.http.impl;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http.Header;
import com.google.code.http.Parser;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class HeadersParserTestCase {
	
	private Parser<List<Header>, byte[]> parser;
	
	@BeforeClass
	public void beforeClass() {
		parser = new HeadersParser();
	}
	
	@Test
	public void parse() throws IOException {
		assertion("Host:www.google.com", new NameValue("Host","www.google.com"));
		assertion("Host:www.google.com\r\nAccept:text/html", 
				new NameValue("Host","www.google.com"),
				new NameValue("Accept","text/html"));
		assertion("Host :\t www.google.com\r\nAccept:text/html", 
				new NameValue("Host","www.google.com"),
				new NameValue("Accept","text/html"));
	}
	
	private void assertion(String src, NameValue... entries) throws IOException {
		List<Header> headers = parser.parse(src.getBytes());
		Assert.assertNotNull(headers);
		Assert.assertEquals(headers.size(), entries.length);
		int found = 0;
		for(NameValue nv:entries) {
			for(Header header:headers) {
				if(header.getName().equalsIgnoreCase(nv.name)) {
					Assert.assertEquals(header.getValue(), nv.value);
					found++;
				}
			}
		}
		Assert.assertEquals(found, entries.length);
	}
	
	static class NameValue {
		NameValue(String name, String value) {
			this.name = name;
			this.value = value;
		}
		String name;
		String value;
	}
}
