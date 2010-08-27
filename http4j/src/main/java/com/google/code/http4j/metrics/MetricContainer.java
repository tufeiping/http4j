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
import com.google.code.http4j.Factory;
import com.google.code.http4j.impl.BasicContainer;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class MetricContainer extends BasicContainer {
	protected Factory<DnsCache> createDnsCacheFactory() {
		return new Factory<DnsCache>() {
			@Override
			public DnsCache create() {
				return new MetricDnsCache();
			}
		};
	}

	protected Factory<ConnectionPool> createConnectionPoolFactory() {
		return new Factory<ConnectionPool>() {
			@Override
			public ConnectionPool create() {
				return new MetricConnectionPool();
			}
		};
	}
}
