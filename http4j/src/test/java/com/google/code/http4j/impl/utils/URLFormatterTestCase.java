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

package com.google.code.http4j.impl.utils;


import java.net.MalformedURLException;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http4j.impl.utils.URLFormatter;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class URLFormatterTestCase {
	
	@Test
	public void testEnsureProtocol() {
		String url = URLFormatter.ensureProtocol("www.google.com");
		Assert.assertEquals(url, "http://www.google.com");
	}
	
	@Test
	public void testFormat() throws MalformedURLException {
		assertion("www.google.com", "http://www.google.com/");
		assertion("http://www.google.com", "http://www.google.com/");
		assertion("https://www.google.com", "https://www.google.com/");
		assertion("http://www.google.com/search?q=http4j", "http://www.google.com/search?q=http4j");
	}
	
	private void assertion(String input, String output) throws MalformedURLException {
		URL url = URLFormatter.format(input);
		Assert.assertNotNull(url);
		Assert.assertEquals(url.toString(), output);
	}
}
