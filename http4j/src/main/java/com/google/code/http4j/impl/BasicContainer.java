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

package com.google.code.http4j.impl;

import com.google.code.http4j.ConnectionPool;
import com.google.code.http4j.Container;
import com.google.code.http4j.CookieCache;
import com.google.code.http4j.DnsCache;
import com.google.code.http4j.impl.connection.BasicConnectionPool;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicContainer extends Container {

	protected GenericFactory<ConnectionPool> connectionPoolFactory;

	protected GenericFactory<CookieCache> cookieCacheFactory;

	protected GenericFactory<DnsCache> dnsCacheFactory;

	public BasicContainer() {
		connectionPoolFactory = createConnectionPoolFactory();
		cookieCacheFactory = createCookieCacheFactory();
		dnsCacheFactory = createDnsCacheFactory();
	}

	protected GenericFactory<DnsCache> createDnsCacheFactory() {
		return new GenericFactory<DnsCache>() {
			@Override
			public DnsCache create() {
				return new BasicDnsCache();
			}
		};
	}

	protected GenericFactory<CookieCache> createCookieCacheFactory() {
		return new GenericFactory<CookieCache>() {
			@Override
			public CookieCache create() {
				return new CookieStoreAdapter();
			}
		};
	}

	protected GenericFactory<ConnectionPool> createConnectionPoolFactory() {
		return new GenericFactory<ConnectionPool>() {
			@Override
			public ConnectionPool create() {
				return new BasicConnectionPool();
			}
		};
	}

	@Override
	public GenericFactory<ConnectionPool> getConnectionPoolFactory() {
		return connectionPoolFactory;
	}

	@Override
	public GenericFactory<CookieCache> getCookieCacheFactory() {
		return cookieCacheFactory;
	}

	@Override
	public GenericFactory<DnsCache> getDnsCacheFactory() {
		return dnsCacheFactory;
	}
}
