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

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http4j.Header;
import com.google.code.http4j.impl.CanonicalHeader;


/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public final class HeaderTestCase {
	
	private Header host;
	
	private Header acceptLanguage;
	
	@BeforeClass
	public void beforeClass() {
		host = new CanonicalHeader("host", "code.google.com");
		acceptLanguage = new CanonicalHeader("ACcePT-lanGuagE", "en_US");
	}
	
	@Test
	public void getName() {
		Assert.assertEquals(host.getName(), "Host");
		Assert.assertEquals(acceptLanguage.getName(), "Accept-Language");
	}
	
	@Test
	public void getValue() {
		Assert.assertEquals(host.getValue(), "code.google.com");
		Assert.assertEquals(acceptLanguage.getValue(), "en_US");
	}
}
