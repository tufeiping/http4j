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

import java.io.IOException;

import com.google.code.http4j.Connection;
import com.google.code.http4j.HttpHost;

public class MetricConnectionDecorator implements Connection {
	
	protected Connection connection;
	
	public MetricConnectionDecorator(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void close() throws IOException {
		connection.close();
	}

	@Override
	public HttpHost getHost() {
		return connection.getHost();
	}

	@Override
	public void connect() throws IOException {
		Timer timer = ThreadLocalMetrics.getInstance().getConnectionTimer();
		timer.startTimer();
		connection.connect();
		timer.stopTimer();
	}

	@Override
	public void write(byte[] message) throws IOException {
		Metrics metrics = ThreadLocalMetrics.getInstance();
		Timer timer = metrics.getRequestTimer();
		timer.startTimer();
		connection.write(message);
		timer.stopTimer();
		metrics.getRequestTrafficCounter().increase(Long.valueOf(message.length));
	}

	@Override
	public byte[] read() throws IOException {
		Metrics metrics = ThreadLocalMetrics.getInstance();
		Timer timer = metrics.getResponseTimer();
		timer.startTimer();
		byte[] message = connection.read();
		timer.stopTimer();
		metrics.getResponseTrafficCounter().increase(Long.valueOf(message.length));
		return message;
	}

	@Override
	public boolean isClosed() {
		return connection.isClosed();
	}
}
