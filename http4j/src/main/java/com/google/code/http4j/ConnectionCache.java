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

package com.google.code.http4j;

import java.io.IOException;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public interface ConnectionCache {

	/**
	 * @see #setMaxConnectionsPerHost(int)
	 * @param host
	 * @return connection for {@code host}, blocked util connections for this
	 *         host is less than max connections per host value.
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	Connection acquire(Host host) throws InterruptedException, IOException;

	/**
	 * @param connection
	 * @return <code>true</code> if the connection has been release,
	 *         <code>false</code> if the manager has been shutdown or connection
	 *         is closed.
	 */
	boolean release(Connection connection);

	void setMaxConnectionsPerHost(int maxConnectionsPerHost);

	void shutdown();
}
