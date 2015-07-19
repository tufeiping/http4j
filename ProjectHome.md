
```
package com.google.code.http4j.example;

import java.io.IOException;
import java.net.URISyntaxException;

import com.google.code.http4j.Client;
import com.google.code.http4j.Response;
import com.google.code.http4j.impl.BasicClient;
import com.google.code.http4j.utils.Metrics;

public class BasicExample {
	
	public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
		Client client = new BasicClient();
		Response response = client.get("http://code.google.com/p/http4j/");
		Metrics metrics = response.getMetrics();
		System.out.println("Bytes sent:" + metrics.getBytesSent());
		System.out.println("Bytes received:" + metrics.getBytesReceived());
		System.out.println("Blocking cost:" + metrics.getBlockingCost());
		System.out.println("DNS lookup cost:" + metrics.getDnsLookupCost());
		System.out.println("Connection establish cost:" + metrics.getConnectingCost());
		System.out.println("Sending cost:" + metrics.getSendingCost());
		System.out.println("Waiting cost:" + metrics.getWaitingCost());
		System.out.println("Receiving cost:" + metrics.getReceivingCost());
		System.out.println("SSL handshake cost:" + metrics.getSslHandshakeCost());
		response.output(System.out);
		client.shutdown();
	}
}
```
# What is http4j #
http4j is a java lightweight HTTP client library. It is designed to be used to measure HTTP performance.
# Why is http4j created #
There are already some libraries for Java HTTP such as Apache [HttpComponents.](http://hc.apache.org/)
They are really good, but does not meet our requirement.
[HttpComponents](http://hc.apache.org/) is complex for a HTTP beginner as I "AM" now. And while I want to get some performance data, I have to write something such as proxy or HTTP listener. I tried that and finally gave up. I hope http4j will become a library which is easy to use, easy to read and easy to extend.
# How to use it #
System requirement: JRE1.6.0\_21 or above.
You've seen a very simple example above.User guide comes later.
# License #
http4j uses Apache License 2.0.