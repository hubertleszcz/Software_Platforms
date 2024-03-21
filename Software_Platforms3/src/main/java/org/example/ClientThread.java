package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable{
    private Socket socket;

    private static String READY_MESSAGE = "Ready";
    private static String READY_FOR_MESSAGES_MESSAGE = "Ready for messages";
    private static String FINISHED_MESSAGE = "Finished";


    public ClientThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try{
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(READY_MESSAGE);

            int n = (int) in.readObject();

            out.writeObject(READY_FOR_MESSAGES_MESSAGE);

            for (int i = 0; i < n; i++) {
                Message message = (Message) in.readObject();
                System.out.println("Odebrano wiadomość: " + message.getMessage());
            }

            out.writeObject(FINISHED_MESSAGE);

            this.socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
