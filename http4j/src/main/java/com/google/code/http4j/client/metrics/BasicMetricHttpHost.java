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

import com.google.code.http4j.client.impl.BasicHttpHost;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class BasicMetricHttpHost extends BasicHttpHost {

	public BasicMetricHttpHost(String host) throws UnknownHostException {
		super(host);
	}

	public BasicMetricHttpHost(String protocol, String host, int port)
			throws UnknownHostException {
		super(protocol, host, port);
	}

	public BasicMetricHttpHost(String protocol, String host, int port, byte[] address)
			throws UnknownHostException {
		super(protocol, host, port, address);
	}
	
	protected InetAddress lookupDNS(String host, byte[] address) throws UnknownHostException {
		ThreadLocalMetrics metrics = ThreadLocalMetrics.getInstance();
		metrics.startDNSTimer();
		InetAddress ip = super.lookupDNS(host, address);
		metrics.stopDNSTimer();
		return ip;
	}
}
