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
public interface MetricsRecorder {

	/**
	 * DNS timer should be reset firstly while getting from DNS Cache.
	 * @return dnsTimer
	 */
	Timer getDnsTimer();

	/**
	 * Connection timer should be reset firstly while getting from Connection Pool.
	 * @return
	 */
	Timer getConnectionTimer();

	Timer getRequestTimer();

	Timer getResponseTimer();
	
	Counter<Integer> getConnectionCounter();
	
	Counter<Long> getRequestTransportCounter();

	Counter<Long> getResponseTransportCounter();
	
	/**
	 * @return snapshot at calling moment
	 */
	Metrics captureMetrics();
	
	void reset();
}
