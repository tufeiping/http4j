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

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import com.google.code.http4j.client.ConnectionPool;
import com.google.code.http4j.client.HttpHost;
import com.google.code.http4j.client.HttpRequest;
import com.google.code.http4j.client.impl.BasicHttpClient;
import com.google.code.http4j.client.impl.HttpGet;
import com.google.code.http4j.client.impl.HttpHead;
import com.google.code.http4j.client.impl.HttpPost;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class MetricHttpClient extends BasicHttpClient {

	public MetricHttpClient() {
		super();
	}
	
	protected void resetMetrics() {
		ThreadLocalMetrics.getInstance().reset();
	}

	@Override
	protected ConnectionPool createConnectionPool() {
		return new MetricConnectionPool();
	}
	
	@Override
	protected HttpRequest createGetRequest(String url)
			throws MalformedURLException, UnknownHostException, URISyntaxException {
		return new HttpGet(url) {
			private static final long serialVersionUID = -7970795478275592916L;
			@Override
			protected HttpHost createHttpHost(String protocol, String host,
					int port) throws UnknownHostException {
				return createMetricHttpHost(protocol, host, port);
			}
		};
	}
	
	@Override
	protected HttpRequest createHeadRequest(String url)
			throws MalformedURLException, UnknownHostException, URISyntaxException {
		return new HttpHead(url) {
			private static final long serialVersionUID = 1L;
			@Override
			protected HttpHost createHttpHost(String protocol, String host,
					int port) throws UnknownHostException {
				return createMetricHttpHost(protocol, host, port);
			}
		};
	}
	
	protected MetricHttpHost createMetricHttpHost(String protocol, String host,
			int port) throws UnknownHostException {
		resetMetrics();// One metric, one request-response
		return new MetricHttpHost(protocol, host, port);
	}
	
	@Override
	protected HttpRequest createPostRequest(String url)
			throws MalformedURLException, UnknownHostException, URISyntaxException {
		return new HttpPost(url) {
			private static final long serialVersionUID = -112127817630730021L;
			@Override
			protected HttpHost createHttpHost(String protocol, String host,
					int port) throws UnknownHostException {
				return createMetricHttpHost(protocol, host, port);
			}
		};
	}
}
