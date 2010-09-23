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

package com.google.code.http4j.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http4j.Charset;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public final class ChunkedResponseTestCase extends AbstractResponseTestCase {

	@Test(expectedExceptions = IOException.class)
	public void construct_cause_IOException() throws IOException {
		new ChunkedResponse(statusLine, headers, new ByteArrayInputStream(
				"10\r\nhttp4j\r\n0\r\n\r\n".getBytes()));
	}

	@Test
	public void guessCharset() throws IOException {
		InputStream in = new ByteArrayInputStream("29\r\n<meta content=\"text/html; charset=GBK\" />\r\n6\r\nhttp4j\r\n0\r\n\r\n".getBytes());
		ChunkedResponse response = new ChunkedResponse(statusLine, headers, in);
		Assert.assertEquals(response.getCharset(), Charset.GBK);
		Assert.assertEquals(new String(response.getEntity(), response.getCharset()), "<meta content=\"text/html; charset=GBK\" />http4j");
	}

	@Override
	protected String getHeaders() {
		return "Content-Type:text/html\r\nTransfer-Encoding:chunked\r\n";
	}

	@Override
	protected String getStatusLine() {
		return "HTTP/1.1 200 OK";
	}
}
