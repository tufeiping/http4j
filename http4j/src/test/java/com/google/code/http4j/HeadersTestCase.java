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

package com.google.code.http4j;

import java.util.LinkedList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http4j.impl.CanonicalHeader;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HeadersTestCase {
	
	@Test
	public void getCharset_String() {
		String contentType = "text/html; charset=UTF-8 ";
		Assert.assertEquals(Headers.getCharset(contentType), "UTF-8");
		contentType = "text/html";
		Assert.assertEquals(Headers.getCharset(contentType), null);
	}
	
	@Test
	public void toString_Headers() {
		List<Header> headers = new LinkedList<Header>();
		headers.add(new CanonicalHeader("Content-Type","text/html"));
		headers.add(new CanonicalHeader("Content-Length","2012"));
		String string = Headers.toString(headers);
		Assert.assertEquals(string, "\r\nContent-Type:text/html\r\nContent-Length:2012");
	}
}
