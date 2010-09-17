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

package com.google.code.http;

import java.io.IOException;
import java.net.URISyntaxException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.code.http.impl.BasicRequestExecutor;
import com.google.code.http.impl.Get;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class RequestExecutorTestCase {
	
	private RequestExecutor executor;
	
	@BeforeClass
	public void beforeClass() {
		executor = new BasicRequestExecutor();
	}
	
	@Test
	public void execute() throws URISyntaxException, InterruptedException, IOException {
		Request request = new Get("http://code.google.com/p/http4j/");
		Response response = executor.execute(request);
		Assert.assertNotNull(response);
	}
}
