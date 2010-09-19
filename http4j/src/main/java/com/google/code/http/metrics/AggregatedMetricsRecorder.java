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

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class AggregatedMetricsRecorder extends AbstractMetricsRecorder {

	protected AtomicLong waitingCost;
	
	public AggregatedMetricsRecorder() {
		super();
		waitingCost = new AtomicLong(0);
	}

	public void aggregate(MetricsRecorder recorder) {
		((AggregatedTimer) connectionTimer).aggregate(recorder.getConnectionTimer());
		((AggregatedTimer) dnsTimer).aggregate(recorder.getDnsTimer());
		((AggregatedTimer) requestTimer).aggregate(recorder.getRequestTimer());
		((AggregatedTimer) responseTimer).aggregate(recorder.getResponseTimer());
		((AggregatedCounter<Long>) requestTransportCounter).aggregate(recorder.getRequestTransportCounter());
		((AggregatedCounter<Long>) responseTransportCounter).aggregate(recorder.getResponseTransportCounter());
		waitingCost.addAndGet(recorder.captureMetrics().getWaitingCost());
	}

	@Override
	public Metrics captureMetrics() {
		return new AggregatedMetrics();
	}
	
	protected class AggregatedMetrics extends BasicMetrics {
		@Override
		public long getWaitingCost() {
			return waitingCost.get();
		}
	}

	@Override
	protected void init() {
		dnsTimer = new SumTimer();
		connectionTimer = new SumTimer();
		requestTimer = new SumTimer();
		responseTimer = new SumTimer();
		requestTransportCounter = new AtomicLongCounter();
		responseTransportCounter = new AtomicLongCounter();
		connectionCounter = new AtomicIntegerCounter();
	}
}
