
import java.net.*;
import java.io.*;

public class TCPClient {
	
	public static void main (String[]args) throws Exception
	{
		
		try{

		InetAddress address = InetAddress.getByName("127.0.0.1");
		Socket client = new Socket(address, 5001);
		 
		
		BufferedReader theIn = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("ENTER THE LOCALHOST AND YOUR SEARCHING DOMAIN (ex:dnsx-dnsx):");
		String str = theIn.readLine();
		
		
		//send msg
		DataOutputStream theOut = new DataOutputStream( client.getOutputStream());
		theOut.writeBytes(str + "\n");
		//System.out.println("MSG SENT : " + str);
		
		
		//receive msg
		BufferedReader receive = new BufferedReader(new InputStreamReader(client.getInputStream()) );
		String receiveMsg = receive.readLine();
		System.out.println("");	
		System.out.println("RESULT IS : " + receiveMsg);
		
		
		client.close();
		}
		catch (Exception e){
			System.out.println("myDNSClient: no execute");
		}
		
	}
}
