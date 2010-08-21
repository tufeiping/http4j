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
import java.util.concurrent.ConcurrentHashMap;

import com.google.code.http4j.client.DnsCache;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicDnsCache implements DnsCache {

	private static final long serialVersionUID = 5641865794616472509L;
	
	protected static final ConcurrentHashMap<String, InetAddress> CACHE = new ConcurrentHashMap<String, InetAddress>();
	
	private static volatile DnsCache instance;
	
	protected BasicDnsCache() {
	}
	
	public static DnsCache getInstance() {
		return instance == null ? new BasicDnsCache() : instance;
	}
	
	protected static void setInstance(DnsCache cache) {
		instance = cache;
	}
	
	protected InetAddress lookupDns(String host) throws UnknownHostException {
		return InetAddress.getByName(host);
	}
	
	@Override
	public InetAddress getInetAddress(String host) throws UnknownHostException {
		InetAddress address = CACHE.get(host);
		if(address == null) {
			address = lookupDns(host);
			CACHE.putIfAbsent(host, address);// multi request concurrent
		}
		return address;
	}

	@Override
	public void cache(String host, InetAddress address) {
		CACHE.put(host, address);
	}
}
