package dev.net.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class NativeTcpClient implements Client {
    private Socket socket;
    private ClientRequestHandler<String> requestHandler;
	
    public NativeTcpClient(Socket socket) {
		this(socket,null);
	}
    
    public NativeTcpClient(Socket socket, ClientRequestHandler<String> handler) {
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
				System.out.println("Client connection socket is closed ..");
			}
		}	
	}
	
	public boolean isConnected() {
		return socket.isConnected();
	}

	public void close() {
		try {
		  socket.close();
		  System.out.println("Client closed");
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}	
	}

	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}

}
