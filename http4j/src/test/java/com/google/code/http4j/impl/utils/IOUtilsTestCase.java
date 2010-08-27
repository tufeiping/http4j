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

import java.nio.ByteBuffer;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.code.http4j.impl.utils.IOUtils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class IOUtilsTestCase {

	@Test
	public void testExtendBuffer() {
		ByteBuffer buffer = ByteBuffer.wrap("http4j".getBytes());
		buffer.position(buffer.capacity());
		ByteBuffer extended = IOUtils.extendBuffer(buffer);
		Assert.assertNotNull(extended);
		Assert.assertTrue(extended.capacity() > buffer.capacity());
		Assert.assertEquals(extended.position(), buffer.limit());
		byte[] src = buffer.array();
		byte[] dest = extended.array();
		for (int i = 0; i < buffer.limit(); i++) {
			Assert.assertEquals(src[i], dest[i]);
		}
	}
	
	@Test
	public void testExtract() {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		buffer.put("http4j".getBytes());
		byte[] bytes = IOUtils.extract(buffer);
		Assert.assertNotNull(bytes);
		Assert.assertEquals(new String(bytes), "http4j");
	}
	
	@Test
	public void testExtractByEnd() {
		assertionExtractByEnd("http4j", "", "http4j");
		assertionExtractByEnd("http4j", "h", "ttp4j");
		assertionExtractByEnd("http4j", "http", "4");
		assertionExtractByEnd("http4j", "http", "4j");
		assertionExtractByEnd("http4j", "http4j", "4g");
	}
	
	@Test
	public void testTransfer() {
		byte[] bytes = "http4j".getBytes();
		ByteBuffer src = ByteBuffer.allocate(bytes.length);
		src.put(bytes);
		ByteBuffer dest = ByteBuffer.allocate(bytes.length);
		IOUtils.transfer(src, dest);
		Assert.assertEquals(dest.position(), bytes.length);
	}
	
	private void assertionExtractByEnd(String source, String dest, String end) {
		ByteBuffer buffer = ByteBuffer.wrap(source.getBytes());
		byte[] bytes = IOUtils.extractByEnd(buffer, end.getBytes());
		Assert.assertNotNull(bytes);
		Assert.assertEquals(new String(bytes), dest);
	}
}
