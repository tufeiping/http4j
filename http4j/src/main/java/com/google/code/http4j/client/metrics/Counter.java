package com.google.code.http4j.client.metrics;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface Counter<T extends Number> {
	
	T get();
	
	void increase();
	
	void increase(T number);
	
	void increase(Counter<T> counter);
	
	void reset();
}
