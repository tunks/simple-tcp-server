package dev.net.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * Native TCP client implementation
 *  
 */
public class NativeTcpClient implements Client {
    private Socket socket;
    private ClientRequestHandler<String> requestHandler;
	private ClientInfo clientInfo;
    public NativeTcpClient(String id, Socket socket) {
		this(id,socket,null);
	}
   
    public NativeTcpClient(String id, Socket socket, ClientRequestHandler<String> handler) {
    	this.clientInfo = new ClientInfo(id);
		this.socket = socket;
		this.requestHandler = handler;
	}
	
	public void read() throws IOException {
		try( BufferedReader bufferRead = new BufferedReader(new InputStreamReader(socket.getInputStream()))) 
		{
			String data;
	        while ((data = bufferRead.readLine()) != null) 
	        {
	        	requestHandler.handle(data);
	        }	
		}
		catch(SocketException ex) {
			  System.out.println("SocketException.Connection terminated. "+ex.getMessage());
			  ex.printStackTrace();
		}
		catch(IOException ex) {
			System.out.println("IOException terminated. "+ex.getMessage());
			ex.printStackTrace();
		}
		finally {
			if(socket.isClosed()) {
				System.out.println("Client connection socket is closed .."+clientInfo);
			}
		}	
	}
	
	public boolean isConnected() {
		return socket.isConnected();
	}

	public void close() {
		try {
		  socket.close();
		  System.out.println("Client closed "+clientInfo);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}	
	}

	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}

	@Override
	public ClientInfo getClientInfo() {
		return clientInfo;
	}

}
