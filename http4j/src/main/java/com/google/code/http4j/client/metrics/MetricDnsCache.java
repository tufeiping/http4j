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

package com.google.code.http4j.client.metrics;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.google.code.http4j.client.DnsCache;
import com.google.code.http4j.client.impl.BasicDnsCache;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class MetricDnsCache extends BasicDnsCache implements DnsCache {

	private static final long serialVersionUID = 4022408023178943059L;

	protected MetricDnsCache() {
		super();
	}
	
	public static void enableMetrics() {
		setInstance(new MetricDnsCache());
	}

	@Override
	public InetAddress getInetAddress(String host) throws UnknownHostException {
		Timer timer = ThreadLocalMetrics.getInstance().getDnsTimer();
		timer.reset();
		return super.getInetAddress(host);
	}
	
	@Override
	protected InetAddress lookupDns(String host)
			throws UnknownHostException {
		Timer timer = ThreadLocalMetrics.getInstance().getDnsTimer();
		timer.startTimer();
		InetAddress address = super.lookupDns(host);
		timer.stopTimer();
		return address;
	}
}
