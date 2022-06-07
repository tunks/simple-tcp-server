package dev.net.server;

import java.io.IOException;

import dev.net.client.ClientRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * Netty Tcp server implementation 
 * 
 */
public class NettyTcpServer extends TcpServer{
	private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
	
    public NettyTcpServer(int port, ServerFactory factory) {
		super(port, factory);
		initNettyServer();
	}

    private void initNettyServer() {
         bossGroup = new NioEventLoopGroup();
         workerGroup = new NioEventLoopGroup();
    }
    
	@Override
	public void start() throws IOException{
		System.out.println("Starting Netty server ...");
	        try {
	        	ServerFactory factory = getServerFactory();
	        	ClientRequestHandler<String> requestHandler = factory.getClientRequestHandler();
	            ServerBootstrap boostrap = new ServerBootstrap();
	            boostrap.group(bossGroup, workerGroup)
	             .channel(NioServerSocketChannel.class)
	             .childHandler(new ChannelInitializer<SocketChannel>() {
	                 @Override
	                 public void initChannel(SocketChannel ch) throws Exception {
	                	 ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(1 * 1024));
	                     ChannelPipeline pipeline = ch.pipeline();
	                     pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, 5));
	                     pipeline.addLast(new StringEncoder());
	                     pipeline.addLast(new StringDecoder());
	                     ch.pipeline().addLast(new ClientChannelHandler(requestHandler));
	                 }
	             })
	             .option(ChannelOption.SO_BACKLOG, 128)   
	             .childOption(ChannelOption.SO_KEEPALIVE, true); 
	    
	            // Bind and start to accept incoming connections.
	            ChannelFuture future = boostrap.bind(getPort()).sync();
	    		System.out.println("Started Netty server , port: "+getPort());
	            // Wait until the server socket is closed.
	            // In this example, this does not happen, but you can do that to gracefully
	            // shut down your server.
	    		future.channel().closeFuture().sync();    
	        }
	        catch(Exception ex) 
	        {
	        	throw new IOException(ex.getMessage());
	        }
	        finally {
	        	stop();
	        }
	}

	@Override
	public void stop() {
		 workerGroup.shutdownGracefully();
         bossGroup.shutdownGracefully();
	}
	
	/**
	 * Handles a server-side client channel message
	 */
	private static class ClientChannelHandler extends ChannelInboundHandlerAdapter {
        private ClientRequestHandler<String> requestHandler;
        
	    public ClientChannelHandler(ClientRequestHandler<String> requestHandler) {
			this.requestHandler = requestHandler;
		}

		@Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) {
	    	try {
	    		System.out.println("Data received on netty: "+msg);
	    		requestHandler.handle(String.valueOf(msg));
	    	}
	    	finally {
	    		 ReferenceCountUtil.release(msg);
	    	}
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        // Close the connection when an exception is raised.
	        cause.printStackTrace();
	        ctx.close();
	    }
	}
	
	

}
