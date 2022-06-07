package dev.net.client;

import java.io.IOException;

/**
 * Base client connection interface 
 */
public interface Client {
   public void read() throws IOException;
   public boolean isConnected();
   public boolean isClosed();
   public void close();
  
}
