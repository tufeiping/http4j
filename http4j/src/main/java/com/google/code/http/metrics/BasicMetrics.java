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

package com.google.code.http.metrics;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicMetrics implements Metrics {

	protected long connectingCost;
	protected long dnsLookupCost;
	protected long receivingCost;
	protected long sendingCost;
	protected long waitingCost;
	protected long bytesSent;
	protected long bytesReceived;

	public BasicMetrics() {
		super();
	}

	public BasicMetrics(long connectingCost, long dnsLookupCost,
			long receivingCost, long sendingCost, long waitingCost,
			long bytesSent, long bytesReceived) {
		super();
		this.connectingCost = connectingCost;
		this.dnsLookupCost = dnsLookupCost;
		this.receivingCost = receivingCost;
		this.sendingCost = sendingCost;
		this.waitingCost = waitingCost;
		this.bytesSent = bytesSent;
		this.bytesReceived = bytesReceived;
	}

	public long getConnectingCost() {
		return connectingCost;
	}

	public void setConnectingCost(long connectingCost) {
		this.connectingCost = connectingCost;
	}

	public long getDnsLookupCost() {
		return dnsLookupCost;
	}

	public void setDnsLookupCost(long dnsLookupCost) {
		this.dnsLookupCost = dnsLookupCost;
	}

	public long getReceivingCost() {
		return receivingCost;
	}

	public void setReceivingCost(long receivingCost) {
		this.receivingCost = receivingCost;
	}

	public long getSendingCost() {
		return sendingCost;
	}

	public void setSendingCost(long sendingCost) {
		this.sendingCost = sendingCost;
	}

	public long getWaitingCost() {
		return waitingCost;
	}

	public void setWaitingCost(long waitingCost) {
		this.waitingCost = waitingCost;
	}

	public long getBytesSent() {
		return bytesSent;
	}

	public void setBytesSent(long bytesSent) {
		this.bytesSent = bytesSent;
	}

	public long getBytesReceived() {
		return bytesReceived;
	}

	public void setBytesReceived(long bytesReceived) {
		this.bytesReceived = bytesReceived;
	}
}
