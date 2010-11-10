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

package com.google.code.http4j.utils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public class BasicMetrics implements Metrics {

	protected final long dnsLookupCost;

	protected final long connectingCost;

	protected final long sendingCost;

	protected final long waitingCost;

	protected final long receivingCost;

	protected final long bytesSent;

	protected final long bytesReceived;

	protected final long sslHandshakeCost;

	protected Metrics sourceMetrics;
	
	public BasicMetrics(long dnsLookupCost, long connectingCost,
			long sendingCost, long waitingCost, long receivingCost,
			long sslHandshakeCost, long bytesSent, long bytesReceived) {
		this.dnsLookupCost = dnsLookupCost;
		this.connectingCost = connectingCost;
		this.sendingCost = sendingCost;
		this.waitingCost = waitingCost;
		this.receivingCost = receivingCost;
		this.sslHandshakeCost = sslHandshakeCost;
		this.bytesSent = bytesSent;
		this.bytesReceived = bytesReceived;
	}

	public long getDnsLookupCost() {
		return dnsLookupCost;
	}

	public long getConnectingCost() {
		return connectingCost;
	}

	public long getSendingCost() {
		return sendingCost;
	}

	public long getWaitingCost() {
		return waitingCost;
	}

	public long getReceivingCost() {
		return receivingCost;
	}

	public long getBytesSent() {
		return bytesSent;
	}

	public long getBytesReceived() {
		return bytesReceived;
	}

	public long getSslHandshakeCost() {
		return sslHandshakeCost;
	}

	@Override
	public Metrics getSourceMetrics() {
		return sourceMetrics;
	}

	@Override
	public void setSourceMetrics(Metrics sourceMetrics) {
		this.sourceMetrics = sourceMetrics;
	}
}
