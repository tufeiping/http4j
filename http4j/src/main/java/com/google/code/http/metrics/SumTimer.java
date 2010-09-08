package com.google.code.http.metrics;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class SumTimer extends SpanTimer {
	
	protected AtomicLong duration;
	
	public SumTimer() {
		super();
		duration = new AtomicLong(0);
	}
	
	@Override
	public void aggregate(Timer timer) {
		super.aggregate(timer);
		duration.addAndGet(timer.getDuration());
	}
	
	@Override
	public long getDuration() {
		return duration.get();
	}
}
