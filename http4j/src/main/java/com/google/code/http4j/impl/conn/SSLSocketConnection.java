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

package com.google.code.http4j.impl.conn;

import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import com.google.code.http4j.Host;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class SSLSocketConnection extends SocketConnection {

	public SSLSocketConnection(Host host) throws IOException {
		this(host, 0);
	}

	public SSLSocketConnection(Host host, int timeout) throws IOException {
		super(host, timeout);
	}
	
	@Override
	protected Socket createSocket() throws IOException {
		SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();
		return factory.createSocket();
	}
	
	@Override
	protected void doConnect() throws IOException {
		super.doConnect();//TODO add something such as handshake...
	}
	
	// TODO
}
