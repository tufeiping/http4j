package com.google.code.http4j.client.metrics;


/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public abstract class AbstractTimer implements Timer {
	
	protected long start;
	
	protected long end;
	
	abstract protected long getCurrentTime();
	
	@Override
	public long getTimeCost() {
		return end - start;
	}
	
	@Override
	public void reset() {
		start = 0;
		end = 0;
	}

	@Override
	public long startTimer() {
		start = getCurrentTime();
		return start;
	}

	@Override
	public long stopTimer() {
		end = getCurrentTime();
		return end;
	}
}
