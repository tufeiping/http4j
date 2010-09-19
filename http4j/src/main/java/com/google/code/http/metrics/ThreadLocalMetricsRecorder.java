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

package com.google.code.http.metrics;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public class ThreadLocalMetricsRecorder extends AbstractMetricsRecorder {

	protected static final ThreadLocal<ThreadLocalMetricsRecorder> local = new ThreadLocal<ThreadLocalMetricsRecorder>();

	protected ThreadLocalMetricsRecorder() {
		super();
		local.set(this);
	}

	public static ThreadLocalMetricsRecorder getInstance() {
		ThreadLocalMetricsRecorder recorder = local.get();
		return recorder == null ? new ThreadLocalMetricsRecorder() : recorder;
	}

	public static void responseStopped() {
		getInstance().getResponseTimer().stop();
	}
	
	public static void dnsLookupStarted() {
		getInstance().getDnsTimer().start();
	}

	public static void dnsLookupStopped() {
		getInstance().getDnsTimer().stop();
	}

	public static void connectionCreated() {
		getInstance().getConnectionCounter().addAndGet(1);
	}

	public static void resetDnsTimer() {
		getInstance().getDnsTimer().reset();
	}
	
	@Override
	protected void init() {
		dnsTimer = new NanoSecondTimer();
		connectionTimer = new NanoSecondTimer();
		requestTimer = new NanoSecondTimer();
		responseTimer = new NanoSecondTimer();
		requestTransportCounter = new LongCounter();
		responseTransportCounter = new LongCounter();
		connectionCounter = new IntCounter();
	}
}
