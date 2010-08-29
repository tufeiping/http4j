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

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public final class GetTestCase {
	
	private Get get;
	
	@BeforeClass
	public void beforeClass() throws MalformedURLException {
		get = new Get("http://code.google.com/q/http4j");
	}
	
	@Test(expectedExceptions = MalformedURLException.class)
	public void construct_cause_exception() throws MalformedURLException {
		new Get("code.google.com");
	}
	
	@Test
	public void toMessage() {
		String m = get.toMessage();
		StringBuilder sb = new StringBuilder("GET /q/http4j HTTP/1.1\r\n");
		sb.append("Host:code.google.com\r\n");
		sb.append("\r\n");
		Assert.assertEquals(m, sb.toString());
	}
}
