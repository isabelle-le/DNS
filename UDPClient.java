
import java.io.*;
import java.net.*;


public class UDPClient {
	
	  public static void main(String args[])
      {
      try
      {
             	 DatagramSocket client=new DatagramSocket();
		         InetAddress addr=InetAddress.getByName("127.0.0.1");
		
		         byte[] sendbyte = new byte[512];
		         byte[] receivebyte = new byte[512];
		         
		         BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
		         System.out.println("ENTER THE LOCALHOST AND YOUR SEARCHING DOMAIN (ex:dnsx-dnsx):");
		         String str = in.readLine();
			 sendbyte =str.getBytes();

                 
                 //send msg
                 DatagramPacket sender = new DatagramPacket(sendbyte,sendbyte.length,addr,6000);
                 client.send(sender);
         		 //System.out.println("MSG SENT : " + str);
                 
                 //receive msg
                 DatagramPacket receiver = new DatagramPacket(receivebyte,receivebyte.length);
                 client.receive(receiver);
                 String s=new String(receiver.getData());
                 System.out.println("");
                 System.out.println("RESULT IS : "+ s.trim()); 
                 
               
                 
                 client.close();
      }
      catch(Exception e)
      {
                 System.out.println("myDNSClient : no execute");
      }
      }
}

//ps -aux | grep java.
// kill -9 pid
