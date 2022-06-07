package dev.net.client;

/**
 * TCP client handler implementation
 */
public class TcpClientHandler implements ClientHandler, Runnable {
    private Client client;
	
	public TcpClientHandler(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

	public void run() {
		try {
		  while(!client.isClosed()) {
		      client.read();
		  }
		}
		catch(Exception ex) {
		   ex.printStackTrace();	
		}
		finally {
			client.close();
		}
	}
}
