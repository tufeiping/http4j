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

import java.io.IOException;
import java.util.List;

import org.testng.annotations.BeforeClass;

import com.google.code.http4j.Header;
import com.google.code.http4j.StatusLine;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public abstract class AbstractResponseTestCase {
	
	protected StatusLine statusLine;

	protected List<Header> headers;
	
	@BeforeClass
	public void beforeClass() throws IOException {
		statusLine = new StatusLineParser().parse(getStatusLine().getBytes());
		headers = new HeadersParser().parse(getHeaders().getBytes());
	}

	abstract protected String getHeaders();

	abstract protected String getStatusLine();
}
