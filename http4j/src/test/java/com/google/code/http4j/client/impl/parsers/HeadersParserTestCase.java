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

package com.google.code.http4j.client.impl.parsers;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http4j.client.HttpHeader;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class HeadersParserTestCase {

	@Test
	public void testParse_byteArray() throws IOException {
		//assertion("Content-Type:text/html", new String[] { "Content-Type", "text/html" });
		assertion("Content-Type:text/html\r\nContent-Location:www.google.com", 
				new String[] {"Content-Type", "text/html"}, 
				new String[] {"Content-Location", "www.google.com"});
	}

	private void assertion(String source, String[]... values)
			throws IOException {
		Map<String, HttpHeader> headers = new HeadersParser().parse(source.getBytes());
		Assert.assertNotNull(headers);
		Assert.assertEquals(headers.size(), values.length);
		for (String[] header: values) {
			Assert.assertTrue(headers.containsKey(header[0]));
			Assert.assertEquals(headers.get(header[0]).getValue(), header[1]);
		}
	}
}
