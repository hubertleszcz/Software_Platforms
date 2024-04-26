package org.example;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {
    private static int PORT = 50000;

    private static String HOST = "localhost";
    public static void main(String[] args) {
       try(Socket socket = new Socket(HOST,PORT)){
           Scanner scanner = new Scanner(System.in);
           ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
           ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

           System.out.print("Połączono się na " + HOST + " Port: " + PORT);

           String signal = (String) in.readObject();

           System.out.println(signal);
           int n = scanner.nextInt();
           out.writeObject(n);

           signal = (String) in.readObject();
           System.out.println(signal);

           for(int i=0;i<n;i++){
               System.out.println("Message nr " + i);
               String mess = scanner.nextLine();
               Message message = new Message(i,mess);
               out.writeObject(message);
               Thread.sleep(3000);
           }

           signal = (String)in.readObject();
           System.out.println(signal);

           scanner.close();
           out.close();
           in.close();
       }catch(IOException e){
           e.printStackTrace();
       } catch (ClassNotFoundException | InterruptedException e) {
           throw new RuntimeException(e);
       }
    }
}
