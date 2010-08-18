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

package com.google.code.http4j.client;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.client.impl.BasicStatusLineParser;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class StatusLineParserTestCase {
	
	private StatusLineParser parser;
	
	@BeforeClass
	public void setUp() {
		parser = new BasicStatusLineParser();
	}
	
	@Test
	public void testParse() {
		assertion("HTTP/1.1 200 OK", "HTTP/1.1", 200, "OK");
		assertion("HTTP/1.1 404\tNot Found", "HTTP/1.1", 404, "Not Found");
		assertion("HTTP/1.1	204  No Content", "HTTP/1.1", 204, "No Content");
	}
	
	private void assertion(String source, String version, int statusCode, String reason) {
		byte[] line = source.getBytes();
		StatusLine statusLine = parser.parse(line);
		Assert.assertNotNull(statusLine);
		Assert.assertEquals(statusLine.getVersion(), version);
		Assert.assertEquals(statusLine.getStatusCode(), statusCode);
		Assert.assertEquals(statusLine.getReason(), reason);
	}
}
