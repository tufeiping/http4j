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

	public MetricDnsCache() {
		super();
	}

	@Override
	protected InetAddress lookupDns(String host, byte[] ip)
			throws UnknownHostException {
		Timer timer = ThreadLocalMetrics.getInstance().getDnsTimer();
		timer.startTimer();
		InetAddress address = super.lookupDns(host, ip);
		timer.stopTimer();
		return address;
	}
}
