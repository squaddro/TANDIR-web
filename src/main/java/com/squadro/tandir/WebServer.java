package com.squadro.tandir;

import java.util.logging.Logger;
import java.util.logging.Level;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class WebServer {
	
	private static final Logger logger = Logger.getLogger(WebServer.class.getName());

	private static int getPort(){
		String port = System.getenv("PORT");
		if(port == null)
			port = "8888";
		return Integer.parseInt(port);
	}
	
	public static void main(String[] args){
		String ip = "0.0.0.0";
		int port = getPort();
		
		logger.log(Level.INFO, "Starting server on " + ip + ":" + port);
		
		Undertow server = Undertow.builder()
			.addHttpListener(port, ip)
			.setHandler(new HttpHandler() {
				@Override
				public void handleRequest(final HttpServerExchange exchange) throws Exception {
					WebServer.logger.log(Level.INFO, exchange.getDestinationAddress().getAddress()
						+ " " + exchange.getRequestMethod()
						+ " " + exchange.getProtocol()
						+ " " + exchange.getQueryString()
						+ " " + exchange.getRequestURL());
					exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
					exchange.getResponseSender().send("Yo!");
				}
			}).build();
		server.start();
	}
}