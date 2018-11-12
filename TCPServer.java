
import java.io.*;
import java.net.*;
import java.util.*;


public class TCPServer {

	public static void main (String args[])
	{
		
		final String one = new String(" YOU FOUND ME. MY NAME IS ");
		      String two = new String(" SEE MY FATHER. HIS NAME IS ");
		      String three = new String(" SEE MY SON. HIS NAME IS ");
		      String four = new String(" REQUESTED DOMAIN IS NOT EXIST. ");
	      
			Properties myDNSPro = new Properties();
			InputStream input = null;
			
		try{
			System.out.println("myDNSServer is waiting for client request");
			ServerSocket server = new ServerSocket(5001);
			
			while(true){
				Socket client = server.accept();

				
				/* receive msg from client
	             * s : localhost ; x : searching domain 
	             */
				BufferedReader theIn = new BufferedReader
						(new InputStreamReader
								(client.getInputStream()) );
				String strSX = theIn.readLine();
				String []sx = strSX.split("-");
		        String s = sx[0];
		        String x = sx[1];
												
		
				
				try{
		        	  input = new FileInputStream(s + ".properties");
		        	  InputStream searchingX = new FileInputStream (x + ".properties");
		        	  
		        	  myDNSPro.load(input);
		        	  
		        	  String parentDNSKey = myDNSPro.getProperty(s + ".parent");

		        	  String []childDNSKey = myDNSPro.getProperty(s + ".child").split(",");
		        	  
		        	  String ipAdd = myDNSPro.getProperty(s + ".ip");
		        	  String ipAddParent = myDNSPro.getProperty(parentDNSKey + ".ip");
		        	  String ipAddChild0 = myDNSPro.getProperty(childDNSKey[0] + ".ip");
		        	  String ipAddChild1 = myDNSPro.getProperty(childDNSKey[1] + ".ip");
		        	  
		        	  DataOutputStream sendMsg = new DataOutputStream(client.getOutputStream());
		        	  
		        	  //Case 1
	        	  if ( input != null && (s.equals(x)) ){
	        		  String name = myDNSPro.getProperty(s + ".name");
	        		  String send1 = new String(new String (one.concat(name)+ " with IP is: ").concat(ipAdd));
	        		  sendMsg.writeBytes(send1);
	        		  break;
		        	  }
		        	  
		        	  //Case 2
	        	  else if( input != null && (!(s.equals(x))) && (parentDNSKey.equals(x)) ) {
	        		  
	        		  String send2 = new String(new String (two.concat(parentDNSKey) + " with IP is: ").concat(ipAddParent));
	        		  sendMsg.writeBytes(send2);
	        		  break;
	        		  
        		  //Case 3
	        	  }else if ( input != null && ((!s.equals(x))) &&( (childDNSKey[0].equals(x)) || (childDNSKey[1].equals(x))) ){
	        		  if(childDNSKey[0].equals(x)) {
		        		  String send3 = new String(new String (three.concat(childDNSKey[0])+ " with IP is: ").concat(ipAddChild0));
		        		  sendMsg.writeBytes(send3);
		        		  break;
	        		  }
	        		  else if (childDNSKey[1].equals(x)) {
	        			  String sendTHREE = new String(new String (three.concat(childDNSKey[1])+ " with IP is: ").concat(ipAddChild1));
		        		  sendMsg.writeBytes(sendTHREE);
		        		  break;  
	        		  }
	        		  
        		  //Case 4
	        	  }else if ( input == null || searchingX == null ){
	        		  String send4 = new String (four.concat(x));
	        		  sendMsg.writeBytes(send4);
	        		  break;
	        	  }
		        	  
		        	  
		          }catch (IOException e) {
		      		System.out.println("Can not open properties file");
		          }finally {
						if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
						}
						break;
						}
		          }
		
				break;
			}//while
			server.close();	
        }
		catch(Exception e)
    	{
    		System.out.println("myDNSServer: no execute");
    	}
		
		
	}
}

