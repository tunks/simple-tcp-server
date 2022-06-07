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
		  System.out.println("Start tcp client handler: "+client.getClientInfo());
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
