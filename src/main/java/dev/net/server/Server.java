package dev.net.server;

import java.io.IOException;

/**
 * Base server 
 */
public interface Server {
	/**
	 *  Start server
	 */
	public void start() throws IOException;
	
	/**
	 * Stop server 
	 */
	public void stop();
}
