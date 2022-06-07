package dev.net.client;

/**
 * 
 *  Client request handler to handle and process client request data
 */
public class ClientRequestHandlerImp implements ClientRequestHandler<String> {
	/**
	 * Handle data from client request
	 * 
	 * @param data
	 */
	public void handle(String data) {
        System.out.printf("Received data from the client: %s\n",data);
	}

}
