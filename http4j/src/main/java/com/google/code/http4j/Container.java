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

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
@Deprecated
public abstract class Container {
	
	private static Container instance;

	public static Container getDefault() {
		return instance;
	}
	
	public static void setDefault(Container container) {
		instance = container;
	}
	
	public static interface GenericFactory<P> {
		P create();
	}
	
	public static interface NameValuePairFactory<T extends NameValuePair> {
		T create(String name, String value);
	}
	
	public static HttpHeader createHttpHeader(String name, String value) {
		return instance.getHttpHeaderFactory().create(name, value);
	}
	
	public static ConnectionPool createConnectionPool() {
		return instance.getConnectionPoolFactory().create();
	}
	
	public static CookieCache createCookieCache() {
		return instance.getCookieCacheFactory().create();
	}
	
	public static DnsCache createDnsCache() {
		return instance.getDnsCacheFactory().create();
	}
	
	abstract protected GenericFactory<ConnectionPool> getConnectionPoolFactory();
	
	abstract protected GenericFactory<CookieCache> getCookieCacheFactory();
	
	abstract protected GenericFactory<DnsCache> getDnsCacheFactory();
	
	abstract protected NameValuePairFactory<? extends HttpHeader> getHttpHeaderFactory();
}
