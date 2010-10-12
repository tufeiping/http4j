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

package com.google.code.http4j.ssl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public abstract class SSLManager {
	
	protected static volatile SSLManager instance;
	
	public static SSLManager getDefault() {
		return instance;
	}
	
	public static void setDefault(SSLManager manager) {
		instance = manager;
	}
	
	public static void restoreDefault() {
		
	}
	
	abstract SSLManager addVerifier(SSLVerifier verifier);
	
	abstract SSLVerifier getVerifier(String host);
	
	protected static class DefaultSSLManager extends SSLManager {
		
		protected Map<String, SSLVerifier> map;
		
		protected DefaultSSLManager() {
			map = new ConcurrentHashMap<String, SSLVerifier>();
		}
		
		@Override
		SSLManager addVerifier(SSLVerifier verifier) {
			map.put(verifier.getHost(), verifier);
			return this;
		}

		@Override
		SSLVerifier getVerifier(final String host) {
			SSLVerifier verifier = map.get(host);
			return null == verifier ? new AllowAllSSLVerifier(host) : verifier;
		}
	}
}
