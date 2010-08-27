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

package com.google.code.http4j.metrics;

import com.google.code.http4j.ConnectionPool;
import com.google.code.http4j.DnsCache;
import com.google.code.http4j.impl.BasicContainer;
import com.google.code.http4j.metrics.connection.MetricConnectionPool;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class MetricContainer extends BasicContainer {
	
	@Override
	protected GenericFactory<DnsCache> createDnsCacheFactory() {
		return new GenericFactory<DnsCache>() {
			@Override
			public DnsCache create() {
				return new MetricDnsCache();
			}
		};
	}

	@Override
	protected GenericFactory<ConnectionPool> createConnectionPoolFactory() {
		return new GenericFactory<ConnectionPool>() {
			@Override
			public ConnectionPool create() {
				return new MetricConnectionPool();
			}
		};
	}
}
