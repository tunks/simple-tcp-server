package dev.net;

import java.io.*;
import java.net.*;

public class ClientApp {
    public static void main(String argv[]) throws Exception {
        String sentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 3000);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("Client connection established. Start sending data to server...");
        while((sentence = inFromUser.readLine()) != null){
            if(sentence.equalsIgnoreCase("q")){
                System.out.println("Quite client");
                break;
            }
            outToServer.writeBytes(sentence + '\n');
        }
        clientSocket.close();
    }
}
