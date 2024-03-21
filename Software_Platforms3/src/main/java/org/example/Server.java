package org.example;

import javax.sound.sampled.Port;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.io.IOException;
public class Server {

    private final int clients = 5;
    private static final String READY_MESSAGE = "Ready";
    private static final String READY_FOR_MESSAGES_MESSAGE = "Ready for messages";
    private static final String FINISHED_MESSAGE = "Finished";
    private static final int PORT = 50000;


    public static void main(String[] args) {
        ServerSocket socket = null;
        int client = 0;
        try{
            socket = new ServerSocket(PORT);


            System.out.print("Serwer działa na porcie: " + PORT);

            Socket clientSocket = socket.accept();
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            out.writeObject(READY_MESSAGE);
            System.out.println("Klient: " + client);
            client++;

            int messageCount = (int) in.readObject();
            out.writeObject(READY_FOR_MESSAGES_MESSAGE);

            for(int i=0;i<messageCount;i++){
                Message receivedMessage = (Message) in.readObject();
                System.out.println("Odebrano wiadomosc o id: " + receivedMessage.getNumber() + "i tresci: " + receivedMessage.getMessage());
            }
            out.writeObject(FINISHED_MESSAGE);
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}
