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

import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.impl.BasicHttpHost;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class MetricHttpHostProxy implements HttpHost {
	
	protected HttpHost host;
	
	public MetricHttpHostProxy(String _host) throws UnknownHostException {
		host = createHttpHost(PROTOCOL_HTTP, _host, -1, null);
	}

	public MetricHttpHostProxy(String protocol, String _host, int port)
			throws UnknownHostException {
		host = createHttpHost(protocol, _host, port, null);
	}

	public MetricHttpHostProxy(String protocol, String _host, int port, byte[] address)
			throws UnknownHostException {
		host = createHttpHost(protocol, _host, port, address);
	}
	
	protected HttpHost createHttpHost(String protocol, String _host, int port, byte[] ip) throws UnknownHostException {
		return new BasicHttpHost(protocol, _host, port, ip);
	}
	
	public InetAddress lookupDNS(String _host, byte[] address) throws UnknownHostException {
		Timer timer = ThreadLocalMetrics.getInstance().getDNSTimer();
		timer.startTimer();
		InetAddress ip = host.lookupDNS(_host, address);
		timer.stopTimer();
		return ip;
	}

	@Override
	public String getProtocol() {
		return host.getProtocol();
	}

	@Override
	public String getHostName() {
		return host.getHostName();
	}

	@Override
	public int getPort() {
		return host.getPort();
	}

	@Override
	public InetAddress getInetAddress() {
		return host.getInetAddress();
	}
}
