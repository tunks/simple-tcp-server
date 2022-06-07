package dev.net.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import dev.net.client.ClientHandler;
import dev.net.client.TcpClientHandler;

/**
 * Abstract TCP server implementation 
 * 
 */
public abstract class TcpServer implements Server {
	private static final int SERVER_THREAD_POOL = 50;
    private final AtomicInteger instanceCount = new AtomicInteger();
    private int port;
	private ExecutorService threadPool;
	private ServerFactory serverFactory;
    private List<ClientHandler> clientHandlers  = new ArrayList<ClientHandler>();
    
    public TcpServer(int port) {
		this(port,null);
	}

    public TcpServer(int port, ServerFactory factory) {
		this.port = port;
		this.serverFactory =  factory;
		createThreadPool();
	}

	public List<ClientHandler> getClientHandlers() {
		return clientHandlers;
	}

	public int getPort() {
		return port;
	}

	public ServerFactory getServerFactory() {
		return serverFactory;
	}

	public void setServerFactory(ServerFactory serverFactory) {
		this.serverFactory = serverFactory;
	}

	protected void addHandler(TcpClientHandler handler) {
		clientHandlers.add(handler);
		threadPool.execute(handler);
	}
	
	
	private void createThreadPool() {
		threadPool = Executors.newFixedThreadPool(SERVER_THREAD_POOL,new ThreadFactory()
        {
            @Override
            public Thread newThread(Runnable r)
            {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                t.setName("Connection::Handler_" + instanceCount.getAndIncrement());
                return t;
            }
        });     
	}
}
