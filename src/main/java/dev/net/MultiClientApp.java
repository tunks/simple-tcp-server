package dev.net;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiClientApp {
    private static ExecutorService executor = Executors.newFixedThreadPool(20);
    private static AtomicInteger clientCounter = new AtomicInteger(1);
    public static void main(String argv[]) throws Exception {
        try{
        	
            for(int index = 0; index < 1; index++)
            {
               executor.execute(()-> sendClientRequest());
            }

            TimeUnit.SECONDS.sleep(5*60);
            executor.shutdownNow();
        }
        catch(Exception ex){
          ex.printStackTrace();  
        }
   }

private static void sendClientRequest() {
    Socket clientSocket;
    try{
    	int clientIndex = clientCounter.getAndIncrement();
        //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        clientSocket= new Socket("localhost", 3000);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("Client connection established. Start sending data to server...");
        String data;
        for(int index = 1; index <= 1000000; index++){
            data = String.format("Sample data , client id: %s, msg number %s",clientIndex,index);
            outToServer.writeBytes(data + '\n');
        }  
        clientSocket.close();
        data = String.format("Client connection client id %s: ",clientIndex);
        System.out.println(data);
    }catch(Exception ex){
       ex.printStackTrace();    }   
   
 } 

}
