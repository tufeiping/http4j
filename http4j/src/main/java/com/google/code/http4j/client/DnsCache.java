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

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class DnsCache implements Serializable {
	
	private static final long serialVersionUID = -2294441769086049537L;
	
	private static DnsCache dnsCache;
	
	public static DnsCache getDefault() {
		return dnsCache;
	}

	public static void setDefault(DnsCache cache) {
		dnsCache = cache;
	}
	
	abstract public InetAddress getInetAddress(String host) throws UnknownHostException;
	
	abstract public void cache(String host, InetAddress address);
}
