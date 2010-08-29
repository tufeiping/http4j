package com.google.code.http;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 *
 */
public interface Parser<R,S> {
	
	R parse(S s);
}
