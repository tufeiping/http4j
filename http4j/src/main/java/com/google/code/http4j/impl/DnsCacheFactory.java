package com.google.code.http4j.impl;

import com.google.code.http4j.DnsCache;
import com.google.code.http4j.Factory;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class DnsCacheFactory implements Factory<DnsCache> {

	@Override
	public DnsCache create() {
		return new BasicDnsCache();
	}
}
