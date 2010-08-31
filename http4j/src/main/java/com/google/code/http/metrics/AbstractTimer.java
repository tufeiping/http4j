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
public abstract class AbstractTimer<T extends Number> implements Timer {
	
	protected T start;
	
	protected T stop;
	
	protected AbstractTimer() {
		reset();
	}
	
	abstract protected T getCurrentTime();
	
	@Override
	public long getDuration() {
		return getStop() - getStart();
	}
	
	@Override
	public void start() {
		reset();
		start = getCurrentTime();
	}

	@Override
	public void stop() {
		stop = getCurrentTime();
	}

	public long getStart() {
		return start.longValue();
	}

	public long getStop() {
		return stop.longValue();
	}
	
	@Override
	public String toString() {
		return getStop() + " - " + getStart() + " = " + getDuration();
	}
}
