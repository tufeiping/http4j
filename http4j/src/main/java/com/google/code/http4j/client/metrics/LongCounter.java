package com.google.code.http4j.client.metrics;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public class LongCounter implements Counter<Long> {
	
	protected Long total;
	
	public LongCounter() {
		total = 0L;
	}
	
	@Override
	public Long get() {
		return total;
	}

	@Override
	public void increase() {
		total += 1;
	}

	@Override
	public void increase(Long number) {
		total += number;
	}

	@Override
	public void increase(Counter<Long> counter) {
		total += counter.get();
	}

	@Override
	public void reset() {
		total = 0L;
	}
}
