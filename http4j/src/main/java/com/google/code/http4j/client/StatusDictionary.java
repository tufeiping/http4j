package com.google.code.http4j.client;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class StatusDictionary {

	public static final int OK = 200;

	public static final int NO_CONTENT = 204;

	public static final int RESET_CONTENT = 205;

	public static final int NOT_MODIFIED = 304;

	public static boolean hasEntity(int status) {
		return status > OK 
			&& status != NO_CONTENT 
			&& status != RESET_CONTENT
			&& status != NOT_MODIFIED;
	}
}
