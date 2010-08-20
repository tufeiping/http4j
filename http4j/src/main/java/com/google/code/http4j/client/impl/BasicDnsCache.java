package com.google.code.http4j.client.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

import com.google.code.http4j.client.DnsCache;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicDnsCache implements DnsCache {

	protected static final ConcurrentHashMap<String, InetAddress> CACHE = new ConcurrentHashMap<String, InetAddress>();
	
	private static final long serialVersionUID = 5641865794616472509L;
	
	protected String getDnsKey(String host, byte[] ip) {
		StringBuilder key = new StringBuilder().append(host);
		if(null != ip) {
			key.append("/").append(new String(ip));
		}
		return key.toString();
	}
	
	protected InetAddress lookupDns(String host, byte[] ip) throws UnknownHostException {
		return null == ip ? InetAddress.getByName(host) : InetAddress.getByAddress(ip);
	}
	
	@Override
	public InetAddress getInetAddress(String host, byte[] ip) throws UnknownHostException {
		String key = getDnsKey(host, ip);
		InetAddress address = CACHE.get(key);
		if(address == null) {
			address = lookupDns(host, ip);
			CACHE.putIfAbsent(key, address);
		}
		return address;
	}
	
}
