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
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class StatusDictionaryTestCase {
	
	@Test
	public void testHasEntity() {
		assertion(0, false);
		assertion(100, false);
		assertion(200, true);
		assertion(204, false);
		assertion(205, false);
		assertion(304, false);
		assertion(399, true);
		assertion(400, true);
		assertion(404, true);
	}
	
	private void assertion(int status, boolean hasEntity) {
		boolean result = StatusDictionary.hasEntity(status);
		Assert.assertEquals(result, hasEntity);
	}
}
