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

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class IOUtilsTestCase {

	@Test
	public void extractByEnd() throws IOException {
		assertionExtractByEnd("http4j", "", "http4j");
		assertionExtractByEnd("http4j", "h", "ttp4j");
		assertionExtractByEnd("http4j", "http", "4");
		assertionExtractByEnd("http4j", "http", "4j");
		assertionExtractByEnd("http4j", "http4j", "4g");
	}
	
	@Test
	public void getNextChunk() throws IOException {
		InputStream in = new ByteArrayInputStream("19\r\nHello World!-From http4j.\r\n1f\r\nAuthor:guilin.zhang@hotmail.com\r\n0\r\n\r\n".getBytes());
		byte[] chunk1 = IOUtils.getNextChunk(in);
		Assert.assertEquals(new String(chunk1), "Hello World!-From http4j.");
		byte[] chunk2 = IOUtils.getNextChunk(in);
		Assert.assertEquals(new String(chunk2), "Author:guilin.zhang@hotmail.com");
	}
	
	private void assertionExtractByEnd(String source, String dest, String end) throws IOException {
		byte[] bytes = IOUtils.extractByEnd(new ByteArrayInputStream(source.getBytes()), end.getBytes());
		Assert.assertNotNull(bytes);
		Assert.assertEquals(new String(bytes), dest);
	}
}
