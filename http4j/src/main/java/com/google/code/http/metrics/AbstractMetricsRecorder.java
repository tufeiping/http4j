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
 */
public abstract class AbstractMetricsRecorder<N extends Number> implements MetricsRecorder<N> {

	protected Timer dnsTimer;
	protected Timer connectionTimer;
	protected Timer requestTimer;
	protected Timer responseTimer;
	protected Counter<N> requestTransportCounter;
	protected Counter<N> responseTransportCounter;
	protected Counter<N> connectionCounter;
	protected Counter<N> conversationCounter;

	protected AbstractMetricsRecorder() {
		dnsTimer = createTimer();
		connectionTimer = createTimer();
		requestTimer = createTimer();
		responseTimer = createTimer();
		requestTransportCounter = createCounter();
		responseTransportCounter = createCounter();
		connectionCounter = createCounter();
		conversationCounter = createCounter();
	}

	abstract protected Counter<N> createCounter();

	abstract protected Timer createTimer();
	
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
	}

	@Override
	public Counter<N> getRequestTransportCounter() {
		return requestTransportCounter;
	}

	@Override
	public Counter<N> getResponseTransportCounter() {
		return responseTransportCounter;
	}
	
	@Override
	public Counter<N> getConnectionCounter() {
		return connectionCounter;
	}
	
	@Override
	public Counter<N> getConversationCounter() {
		return conversationCounter;
	}
}
