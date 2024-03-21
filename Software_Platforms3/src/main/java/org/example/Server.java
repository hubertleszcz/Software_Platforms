package org.example;

import javax.sound.sampled.Port;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.io.IOException;
public class Server {


    private static int PORT = 50000;
    public static void main(String[] args) {
        ServerSocket socket = null;
        try{
            socket = new ServerSocket(PORT);
            System.out.print("Serwer działa na porcie: " + PORT);

            while(true){
                Socket clientSocket = socket.accept();
                Thread clientThread = new Thread(new ClientThread(clientSocket));
                clientThread.start();

            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }

}
