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

package com.google.code.http4j.utils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public class ThreadLocalMetricsRecorder implements MetricsRecorder {

	protected static final ThreadLocal<ThreadLocalMetricsRecorder> local = new ThreadLocal<ThreadLocalMetricsRecorder>();

	protected Timer dnsTimer;
	protected Timer connectionTimer;
	protected Timer requestTimer;
	protected Timer responseTimer;
	protected Counter<Long> requestTransportCounter;
	protected Counter<Long> responseTransportCounter;
	protected boolean cachedConnection;

	protected ThreadLocalMetricsRecorder() {
		init();
		local.set(this);
	}

	public static ThreadLocalMetricsRecorder getInstance() {
		ThreadLocalMetricsRecorder recorder = local.get();
		return recorder == null ? new ThreadLocalMetricsRecorder() : recorder;
	}

	protected void init() {
		dnsTimer = new NanoSecondTimer();
		connectionTimer = new NanoSecondTimer();
		requestTimer = new NanoSecondTimer();
		responseTimer = new NanoSecondTimer();
		requestTransportCounter = new LongCounter();
		responseTransportCounter = new LongCounter();
		cachedConnection = true;
	}

	@Override
	public Timer getDnsTimer() {
		return dnsTimer;
	}

	public Timer getConnectionTimer() {
		return connectionTimer;
	}

	@Override
	public Timer getRequestTimer() {
		return requestTimer;
	}

	@Override
	public Timer getResponseTimer() {
		return responseTimer;
	}

	@Override
	public void reset() {
		dnsTimer.reset();
		connectionTimer.reset();
		requestTimer.reset();
		responseTimer.reset();
		requestTransportCounter.reset();
		responseTransportCounter.reset();
		cachedConnection = true;
	}

	@Override
	public Counter<Long> getRequestTransportCounter() {
		return requestTransportCounter;
	}

	@Override
	public Counter<Long> getResponseTransportCounter() {
		return responseTransportCounter;
	}

	@Override
	public Metrics captureMetrics() {
		return new BasicMetrics(
				dnsTimer.getDuration(),
				connectionTimer.getDuration(), 
				requestTimer.getDuration(),
				responseTimer.getStart() - requestTimer.getStop(),
				responseTimer.getDuration(), 
				requestTransportCounter.get(),
				responseTransportCounter.get(), 
				cachedConnection);
	}

	public void connectionCreated() {
		this.cachedConnection = false;
	}
}
