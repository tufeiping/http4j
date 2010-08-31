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
public class ThreadLocalMetrics extends AbstractMetrics implements Metrics {

	protected static final ThreadLocal<ThreadLocalMetrics> local = new ThreadLocal<ThreadLocalMetrics>();

	protected ThreadLocalMetrics() {
		super();
		local.set(this);
	}

	public static ThreadLocalMetrics getInstance() {
		ThreadLocalMetrics metrics = local.get();
		return metrics == null ? new ThreadLocalMetrics() : metrics;
	}

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
		Metrics metrics = getInstance();
		metrics.getRequestTimer().stop();
		metrics.getRequestTrafficCounter().increase(sent);
	}

	public static void connectStarted() {
		getInstance().getConnectionTimer().start();
	}

	public static void connectStopped() {
		getInstance().getConnectionTimer().stop();
	}

	public static void responseStarted() {
		getInstance().getResponseTimer().start();
	}

	public static void responseStopped(long sent) {
		Metrics metrics = getInstance();
		metrics.getResponseTimer().stop();
		metrics.getResponseTrafficCounter().increase(sent);
	}

	public static void dnsLookupStarted() {
		getInstance().getDnsTimer().start();
	}

	public static void dnsLookupStopped() {
		getInstance().getDnsTimer().stop();
	}
}
