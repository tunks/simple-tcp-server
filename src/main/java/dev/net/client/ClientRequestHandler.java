package dev.net.client;

import java.io.Serializable;

/**
 * Client Request Handler base interface 
 */
public interface ClientRequestHandler<T extends Serializable> {
   public void handle(T message);
}
