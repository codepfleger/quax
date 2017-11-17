package de.codepfleger.quax;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class SimpleGraphiteClient {
	private final String graphiteHost;
	private final int graphitePort;

	public SimpleGraphiteClient(String graphiteHost, int graphitePort) {
		this.graphiteHost = graphiteHost;
		this.graphitePort = graphitePort;
	}

	public void sendMetrics(Map<String, Number> metrics) {
		sendMetrics(metrics, getCurrentTimestamp());
	}

	public void sendMetrics(Map<String, Number> metrics, long timeStamp) {
		try {
			Socket socket = createSocket();
			OutputStream s = socket.getOutputStream();
			PrintWriter out = new PrintWriter(s, true);
			for (Map.Entry<String, Number> metric: metrics.entrySet()) {
				out.printf("%s %s %d%n", metric.getKey(), metric.getValue(), timeStamp);	
			}			
			out.close();
			socket.close();
		} catch (UnknownHostException e) {
			throw new GraphiteException("Unknown host: " + graphiteHost);
		} catch (IOException e) {
			throw new GraphiteException("Error while writing data to graphite: " + e.getMessage(), e);
		}
	}
	

	public void sendMetric(String key, Number value) {
		sendMetric(key, value, getCurrentTimestamp());
	}
	
	public void sendMetric(final String key, final Number value, long timeStamp) {
		sendMetrics(new HashMap<String, Number>() {{
			put(key, value);
		}}, timeStamp);
	}
	
	protected Socket createSocket() throws UnknownHostException, IOException {
		return new Socket(graphiteHost, graphitePort);
	}

	protected long getCurrentTimestamp() {
		return System.currentTimeMillis() / 1000;
	}
}