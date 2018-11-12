/**
 * This a simulation of DNS server, written in java
 * Created by LE Thu Huong
 * THIS IS CLIENT SERVER
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;
 

public class Client {
    public static void main(String[] args) throws IOException {
        try
        {
            Scanner scn = new Scanner(System.in);
             
            InetAddress address = InetAddress.getByName("127.0.0.1");
     
            // establish the connection with server port 5002
            Socket client = new Socket(address, 5002);
     
            // obtaining input and out streams
            DataInputStream receive = new DataInputStream(client.getInputStream());
            DataOutputStream send = new DataOutputStream(client.getOutputStream());
     
            // the following loop performs the exchange of
            // information between client and client handler
            while (true) 
            {
                System.out.println(receive.readUTF());
                String tosend = scn.nextLine();
                send.writeUTF(tosend);
                 
                // If client sends exit,close this connection 
                // and then break from the while loop
                if(tosend.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + client);
                    client.close();
                    System.out.println("Connection closed");
                    break;
                }
                 
                // printing the answer
                String received = receive.readUTF();
                System.out.println(received);
            }
             
            // closing resources
            scn.close();
            receive.close();
            send.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
