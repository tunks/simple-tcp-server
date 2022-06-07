package dev.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import dev.net.client.Client;
import dev.net.client.ClientRequestHandler;
import dev.net.client.TcpClientHandler;
import dev.net.client.NativeTcpClient;

/**
 * 
 * Native Java TCP server implementation
 */
public class NativeTcpServer extends TcpServer {
	private ServerSocket server;
	private boolean started = false;
	private Thread serverThread;
	private AtomicInteger clientCounter = new AtomicInteger();
	public NativeTcpServer(int port) {
		super(port);
	}
	
	public NativeTcpServer(int port,ServerFactory serverFactory) 
	{
		super(port,serverFactory);
	}

	public void start() throws IOException{
		System.out.println("Starting... server on port: "+getPort());
		ServerFactory factory = getServerFactory();
		ClientRequestHandler<String> requestHandler = factory.getClientRequestHandler();
		serverThread = new Thread(() -> {
			try {
				System.out.println("Started server on port: "+getPort());
				started = true;
				server = new ServerSocket();//getPort());
				server.setReuseAddress(true);
				server.bind(new java.net.InetSocketAddress(getPort()));
				while (started) {
					Socket clientSocket = server.accept();
					String clientId = "Client::connection_"+clientCounter.incrementAndGet();
					Client client = new NativeTcpClient(clientId, clientSocket, requestHandler);
					TcpClientHandler clientHandler = factory.createClientHandler(client);
					addHandler(clientHandler);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		serverThread.setName("NativeTcpServer_thread");
		serverThread.start();	
	}

	public void stop() {
		try {
			if (server != null) {
				started = false;
				getClientHandlers().stream().forEach(client->client.getClient().close());
				server.close();
				System.out.println("TcpServer closed");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
