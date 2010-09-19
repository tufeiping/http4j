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

/**
 * Not thread safe counter, but safe in ThreadLocal environment.
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class IntCounter implements Counter<Integer> {
	
	protected Integer total;
	
	public IntCounter() {
		this(0);
	}
	
	public IntCounter(int i) {
		this.total = i;
	}

	@Override
	public Integer get() {
		return total;
	}

	@Override
	public Integer addAndGet(Integer number) {
		total += number;
		return total;
	}

	@Override
	public void reset() {
		total = 0;
	}
}
