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

	@Override
	protected Counter<Integer> createIntegerCounter() {
		return new IntCounter();
	}

	@Override
	protected Counter<Long> createLongCounter() {
		return new LongCounter();
	}
	
	@Override
	protected Timer createTimer() {
		return new NanoSecondTimer();
	}
	
	public static void requestStarted() {
		getInstance().getRequestTimer().start();
	}

	public static void requestStopped(long sent) {
		ThreadLocalMetricsRecorder recorder = getInstance();
		recorder.getRequestTimer().stop();
		recorder.getRequestTransportCounter().increase(sent);
	}

	public static void connectStarted() {
		getInstance().getConnectionTimer().start();
	}

	public static void connectStopped() {
		getInstance().getConnectionTimer().stop();
	}

	public static void responseStarted() {
		Timer timer = getInstance().getResponseTimer();
		timer.reset();
		timer.start();
	}

	public static void responseStopped(long sent) {
		ThreadLocalMetricsRecorder recorder = getInstance();
		recorder.getResponseTimer().stop();
		recorder.getResponseTransportCounter().increase(sent);
	}

	public static void dnsLookupStarted() {
		getInstance().getDnsTimer().start();
	}

	public static void dnsLookupStopped() {
		getInstance().getDnsTimer().stop();
	}

	public static void connectionCreated() {
		getInstance().getConnectionCounter().increase(1);
	}

	public static void resetDnsTimer() {
		getInstance().getDnsTimer().reset();
	}
}
