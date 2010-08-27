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

import org.testng.annotations.Test;

import com.google.code.http4j.Connection;
import com.google.code.http4j.ConnectionTestCase;
import com.google.code.http4j.impl.SSLSocketConnection;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class SSLSocketConnectionTestCase extends ConnectionTestCase {
	
	@Override
	protected Connection createConnection() {
		return new SSLSocketConnection(host);
	}
	
	@Test
	public void testNothing() {
		// do nothing, testng will not recognize a class without testng specific annotation
	}
}
