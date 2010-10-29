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

package com.google.code.http4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

import com.google.code.http4j.utils.ThreadLocalMetricsRecorder;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class DNS {
	
	private static final DNS NORMAL = new DNS();
	
	private static final DNS CACHED = new CachedDNS();
	
	private static volatile DNS instance = CACHED;

	public static InetAddress getAddress(String host) throws UnknownHostException {
		ThreadLocalMetricsRecorder.getInstance().getDnsTimer().reset();
		return instance.getInetAddress(host);
	}
	
	public static void useCache(boolean use) {
		instance = use ? CACHED : noCache();
	}
	
	private static DNS noCache() {
		if(instance instanceof CachedDNS) {
			((CachedDNS)instance).CACHE.clear();
		}
		instance = NORMAL;
		return instance;
	}
	
	protected InetAddress getInetAddress(String host)
			throws UnknownHostException {
		ThreadLocalMetricsRecorder.getInstance().getDnsTimer().start();
		InetAddress address = InetAddress.getByName(host);
		ThreadLocalMetricsRecorder.getInstance().getDnsTimer().stop();
		return address;
	}

	protected static class CachedDNS extends DNS {
		protected final ConcurrentHashMap<String, InetAddress> CACHE = new ConcurrentHashMap<String, InetAddress>();

		public InetAddress getInetAddress(String host)
				throws UnknownHostException {
			InetAddress address = CACHE.get(host);
			if (address == null) {
				address = super.getInetAddress(host);
				InetAddress exist = CACHE.putIfAbsent(host, address);
				if (null != exist) {
					address = exist;
				}
			}
			return address;
		}

		public void cache(String host, InetAddress address) {
			CACHE.put(host, address);
		}
		
		@Override
		protected void finalize() throws Throwable {
			CACHE.clear();
			super.finalize();
		}
	}
}
