package dev.net.server;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dev.net.client.Client;
import dev.net.client.ClientRequestHandler;
import dev.net.client.ClientRequestHandlerImp;
import dev.net.client.TcpClientHandler;

public class ServerFactory {
   private Executor executors;
   private int serverPort = 3000;
 
   public ServerFactory() {
	   executors = Executors.newFixedThreadPool(50);
   }
   
   public Server createServer(int port) {
	  TcpServer tcpServer = new NativeTcpServer(serverPort, this);
	   //TcpServer tcpServer = new NettyTcpServer(serverPort, this);
	  return tcpServer;
   }
   
   public TcpClientHandler createClientHandler(Client client) {
	  return new TcpClientHandler(client);   
   }
   
   public ClientRequestHandler<String> getClientRequestHandler() {
	    return new ClientRequestHandlerImp();
   }
   
}
