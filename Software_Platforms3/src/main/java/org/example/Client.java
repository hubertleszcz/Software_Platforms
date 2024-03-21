package org.example;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
public class Client {
    private static int PORT = 50000;

    private static String HOST = "localhost";

    public static void main(String[] args) {
        int n = 5; // Przykładowa wartość n

        try{
            Socket socket = new Socket(HOST, PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Wyślij liczbę n
            out.writeObject(n);

            // Wyślij n obiektów Message
            for (int i = 0; i < n; i++) {
                Message message = new Message(i, "Content " + i);
                  out.writeObject(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
