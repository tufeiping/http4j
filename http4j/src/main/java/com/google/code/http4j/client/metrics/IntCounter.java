package com.google.code.http4j.client.metrics;

/**
 * Not thread safe counter, but safe in ThreadLocal environment.
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class IntCounter implements Counter<Integer> {
	
	protected Integer total;
	
	public IntCounter() {
		total = 0;
	}
	
	@Override
	public Integer get() {
		return total;
	}

	@Override
	public void increase() {
		total++;
	}

	@Override
	public void increase(Integer number) {
		total += number;
	}
	
	@Override
	public void increase(Counter<Integer> counter) {
		total += counter.get();
	}

	@Override
	public void reset() {
		total = 0;
	}
}
