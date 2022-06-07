package dev.net;

import dev.net.server.Server;
import dev.net.server.ServerFactory;

/**
 *Server application
 *
 */
public class ServerApp 
{
	public static void main(String[] args) {
		ServerFactory serverFactory = new ServerFactory();
		Server server = serverFactory.createServer(3000);
		try {
		    server.start();
		}
		catch(Exception ex) {
			server.stop();
			ex.printStackTrace();
		}
	}
}
