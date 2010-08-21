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

package com.google.code.http4j.client.impl;


import java.net.InetAddress;
import java.net.UnknownHostException;

import com.google.code.http4j.client.DnsCache;
import com.google.code.http4j.client.Http;
import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.impl.utils.URLFormatter;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicHttpHost implements HttpHost {
	
	protected static final DnsCache DEFAULT_DNS_CACHE = new BasicDnsCache();
	
	protected String protocol;
	
	protected String name;
	
	protected int port;
	
	protected InetAddress inetAddress;

	public BasicHttpHost(String hostName) throws UnknownHostException {
		this(Http.PROTOCOL_HTTP, hostName, -1);
	}
	
	public BasicHttpHost(String protocol, String hostName, int port) throws UnknownHostException {
		this.protocol = protocol;
		this.name = hostName;
		this.port = port;
		this.inetAddress = getDnsCache().getInetAddress(hostName);
	}

	protected final DnsCache getDnsCache() {
		DnsCache cache = DnsCache.getDefault();
		if(null == cache) {
			cache = DEFAULT_DNS_CACHE;
			DnsCache.setDefault(cache);
		}
		return cache;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicHttpHost other = (BasicHttpHost) obj;
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
	public String getName() {
		return name;
	}

	@Override
	public InetAddress getInetAddress() throws UnknownHostException {
		return inetAddress;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getProtocol() {
		return protocol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + port;
		result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return URLFormatter.buildURLString(protocol, name, port, "");
	}
}
