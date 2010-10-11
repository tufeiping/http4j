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

package com.google.code.http4j.impl.protocol;

import java.io.IOException;

import com.google.code.http4j.Connection;
import com.google.code.http4j.Host;
import com.google.code.http4j.impl.conn.SSLSocketConnection;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public final class HttpsProtocol extends AbstractProtocol implements Protocol {

	private static final HttpsProtocol instance = new HttpsProtocol();
	
	private HttpsProtocol() {
	}
	
	@Override
	public int getDefaultPort() {
		return 443;
	}

	@Override
	public String getProtocol() {
		return "https";
	}

	public static HttpsProtocol getInstance() {
		return instance;
	}

	@Override
	public Connection createConnection(Host host) throws IOException {
		return new SSLSocketConnection(host);
	}

}
