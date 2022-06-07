package dev.net.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dev.net.client.ClientHandler;

public class TcpServerIntegrationTest {
	private final static int SERVER_PORT = 3002;
	private TcpServer tcpserver;
	
	@Before
	public void setup() throws InterruptedException, IOException {
		Executors.newSingleThreadExecutor().execute(()->{
		    try {
		    	ServerFactory factory = new ServerFactory();
			    tcpserver = (TcpServer)factory.createServer(SERVER_PORT);
				tcpserver.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		});
		TimeUnit.MILLISECONDS.sleep(2000);
	}
	
	@After
	public void clean() {
		//tcpserver.stop();
	}
	
	@Test
	public void testConnections() throws UnknownHostException, IOException, InterruptedException {
		
		
//        Socket clientSocket = new Socket(SERVER_PORT);
//        clientSocket.setKeepAlive(true);
//        List<ClientHandler> clientHandlers = tcpserver.getClientHandlers();        
//        assertEquals(clientHandlers.size(),1);
//        assertTrue(clientSocket.isConnected());
//        clientSocket.close();
//        tcpserver.stop();
		InetSocketAddress addr = new java.net.InetSocketAddress(SERVER_PORT);
         System.out.println("InetSocketAddress: "+addr);
        try(Socket ableToConnect = new Socket(addr.getAddress(), SERVER_PORT))
        {
        	System.out.println("Connecting");
            assertTrue("Accepts connection when server in listening",
                       ableToConnect.isConnected());
            // close the `clientSocket`
            ableToConnect.close();
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }

	}

}
