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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

import com.google.code.http.metrics.ThreadLocalMetricsRecorder;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class DnsCache {
	private static volatile DnsCache instance = new InMemoryDnsCache();

	public static DnsCache getDefault() {
		return instance;
	}

	public static void setDefault(DnsCache dnsCache) {
		instance = dnsCache;
	}

	/**
	 * @param host
	 * @return get address by host, create one if no one in cache and put it
	 *         into the cache.
	 * @throws UnknownHostException
	 */
	public static InetAddress getAddress(String host)
			throws UnknownHostException {
		return instance.getInetAddress(host);
	}

	public static void cache(String host, InetAddress address) {
		instance.doCache(host, address);
	}

	abstract protected InetAddress getInetAddress(String host)
			throws UnknownHostException;

	abstract protected void doCache(String host, InetAddress address);

	public static class InMemoryDnsCache extends DnsCache {
		protected static final ConcurrentHashMap<String, InetAddress> CACHE = new ConcurrentHashMap<String, InetAddress>();

		protected InetAddress lookupDns(String host)
				throws UnknownHostException {
			ThreadLocalMetricsRecorder.dnsLookupStarted();
			InetAddress address = InetAddress.getByName(host);
			ThreadLocalMetricsRecorder.dnsLookupStopped();
			return address;
		}

		protected InetAddress getInetAddress(String host)
				throws UnknownHostException {
			ThreadLocalMetricsRecorder.resetDnsTimer();
			InetAddress address = CACHE.get(host);
			if (address == null) {
				address = lookupDns(host);
				InetAddress exist = CACHE.putIfAbsent(host, address);
				if (null != exist) {
					address = exist;
				}
			}
			return address;
		}

		protected void doCache(String host, InetAddress address) {
			CACHE.put(host, address);
		}
	}
}
