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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class AtomicIntegerCounter implements AggregatedCounter<Integer> {
	
	protected AtomicInteger total;
	
	public AtomicIntegerCounter() {
		this(0);
	}
	
	public AtomicIntegerCounter(int i) {
		total = new AtomicInteger(i);
	}
	
	@Override
	public Integer get() {
		return total.get();
	}

	@Override
	public Integer addAndGet(Integer number) {
		return total.addAndGet(number);
	}

	@Override
	public void reset() {
		total = new AtomicInteger(0);
	}

	@Override
	public void aggregate(Counter<? extends Number> counter) {
		total.addAndGet(counter.get().intValue());
	}
}
