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

package com.google.code.http.impl;

import com.google.code.http.Host;
import com.google.code.http.impl.protocol.HttpProtocol;
import com.google.code.http.impl.protocol.HttpsProtocol;
import com.google.code.http.impl.protocol.Protocol;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public final class BasicHost implements Host {

	private final String name;
	
	private final int port;
	
	private final Protocol protocol;
	
	public BasicHost(String protocol, String name, int port) {
		this.protocol = selectProtocol(protocol);
		this.name = name;
		this.port = port;
	}
	
	private Protocol selectProtocol(String scheme) {
		return "https".equals(scheme) ? HttpsProtocol.getInstance() : HttpProtocol.getInstance();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
		result = prime * result + port;
		result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicHost other = (BasicHost) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (port != other.port)
			return false;
		if (protocol == null) {
			if (other.protocol != null)
				return false;
		} else if (!protocol.equals(other.protocol))
			return false;
		return true;
	}

	@Override
	public String getAuthority() {
		return protocol.getAuthority(name, port);
	}

	@Override
	public int getDefaultPort() {
		return protocol.getDefaultPort();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getProtocol() {
		return protocol.getProtocol();
	}
}
