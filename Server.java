/**
 * This a simulation of DNS server, written in java
 * Created by LE Thu Huong
 * THIS IS SERVER DNS, ITS DATA CAME FROM job.root.properties
 * THIS SERVER IS ALWAYS LISTENING AND CREATE THREAD TO ANSWER EACH CLIENT REQUEST
 */

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
 
// Server class ORIGINAL
public class Server 
{
	
    public static void main(String[] args) throws IOException 
    {
    	int number = 5002;
        // server is listening on port 5002
        ServerSocket server = new ServerSocket(number);
        
        // running infinite loop for getting
        // client request
        while (true) 
        {
            Socket client = null;
             
            try
            {
                // socket object to receive incoming client requests
                client = server.accept();
                 
                System.out.println("A new client is connected : " + client);
                 
                // obtaining input and out streams
                DataInputStream receive = new DataInputStream(client.getInputStream());
                DataOutputStream send = new DataOutputStream(client.getOutputStream());
                 
                System.out.println("Assigning new thread for this client");
 
                // create a new thread object
                Thread t = new ClientHandler(client, receive, send);
 
                // Invoking the start() method
                t.start();
                 
            }
            catch (Exception e){
                client.close();
                e.printStackTrace();
            }
        }
    }
}
 
// ClientHandler class
class ClientHandler extends Thread 
{
	final String one = new String(" YOU FOUND ME. MY NAME IS ");
	final String two = new String(" SEE MY FATHER. HIS NAME IS ");
	final String three = new String(" SEE MY SON. HIS NAME IS ");
	final String four = new String(" REQUESTED DOMAIN IS NOT EXIST. ");
		String answer = null;
    final DataInputStream receive;
    final DataOutputStream send;
    final Socket client;
    private String[] defaultName, defaultPort, thisName;
 
    /**
     * This is the constructor
     * @param client : client socket
     * @param receive : get question from client
     * @param send : send question to client
     */
    public ClientHandler (Socket client, DataInputStream receive, DataOutputStream send) 
    {
        this.client = client;
        this.receive = receive;
        this.send = send;
    }
 
    
    /**
	 * function help to load file, accordingly to client request
	 * @param fname : string receive from client server
	 * @return the loaded file
	 */
	public Properties loadFile (String fname) {
     	Properties prop = new Properties();
	try {
		FileInputStream in = new FileInputStream(fname);
		prop.load(in);
		in.close();
                
         } catch (IOException e) {
        	//System.out.println("REQUESTED DOMAIN IS NOT EXIST "+ fname);
         }
         return prop;
	}
	
	/**
	 * function read name from file and store it into an array
	 * @param fname : string receive from client server
	 * @return an array of name
	 */

	public String[] loadName (String fname){ 
		String[] DNSname = null;
		Properties prop = loadFile(fname);
		List<String> aList = new ArrayList<String>();
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String a = (String) e.nextElement();
			aList.add(a);
			DNSname = aList.toArray(new String [0]);
           /* for (int i =0; i<= DNSname.length; ++ i){
            	System.out.println("name is: "+ i + " "+ DNSname[i]+ "\n");// BO SUNG DE XEM ARRAY
				}*/
            }
		return DNSname;
		}
	
	/**
	 * function read port from file and store it into an array
	 * @param fname : string receive from client server
	 * @return an array of port number
	 */

		
	public String[] loadPort (String fname){ 
		String[] DNSPort= null;
		Properties prop = loadFile(fname);
		List<String> aList = new ArrayList<String>();
		List<String> bList = new ArrayList<String>();
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String a = (String) e.nextElement();
			aList.add(a);
			String b= prop.getProperty(a);
			bList.add(b);
			DNSPort = bList.toArray(new String [0]);
		}
		return DNSPort;
	}
	
	/**
	 * Function help to find out a common Element of question and array of data
	 * @param name : data stored in an array from file
	 * @param str  : question from client
	 * @return
	 */
	public String commentElement(String[]name, String str){ //find himself
		int i;
		String common = null;
		String[] copy = name;
		for(i=0;i<copy.length;i++){
            if(copy[i].equals(str)){
            	common = copy[i];
            	//System.out.println("common is "+ name[i]);
            	break;
            }
		}
		return common;
	}
	
	/**
	 * Function find out the answer to client question and return 4 answer
	 * @param name : data of default array of main server, stored in an array, read from file
	 * @param str  : client question
	 * @return
	 */
	
	public String findAnswer(String[] name, String str) {
	   int tempInt;
	   String answer = null, com=null;
	   boolean handedDownToChild = false;	
	   boolean handedDownToFather = false;	
	   
	   Properties defaultProp = loadFile("job.root.properties");
	   String defaultHostName = defaultProp.getProperty("job.root.name");
	   
	   Properties thisProp = loadFile(str + ".properties");
	   String thisHostName = thisProp.getProperty(str + ".name");
	   
	   //make a copy and play with it
	   String copyStr = str;
	   com = commentElement(name, copyStr);
	   //splip the question to identify father and son
	   tempInt = copyStr.indexOf('.');
	   
	   
	   while ((tempInt = str.indexOf('.'))>-1){
		   //requested server is the current server
		   if(thisHostName.equals(defaultHostName)){
				answer = new String(one + thisHostName );
				break;
		   }else if (!(thisHostName.equals(defaultHostName))){
	  			if (com!=null){
	  				if(tempInt > -1){
	  					String temChild = com;
	  					answer = new String(three + temChild);
	  					handedDownToChild = true;
	  					break;
	  				}
	  				break;
	  			}else if (com == null){
  						//can it resolver by grandFather root
  						int dot1 = copyStr.indexOf(".");
	  					int dot2 = copyStr.indexOf(".", dot1 + 1);;
  						String grandFather = copyStr.substring(dot1 +1);
  						if (grandFather.equals("root")){
  							answer = new String(two + grandFather);
		  					handedDownToFather = true;
		  					break;
  						}
  						else{
  							answer= new String(four + grandFather);
							break;
	  				}
	  			}
	   }	
	   }
	  				
		while (tempInt == -1 && com != null) {
			String temFather = com;
			answer = new String(two + temFather);
			handedDownToFather = true;
			break;
			} 
	   return answer;
	}//function findAnswer
		
	
	/**
	 * Override run() method from Thread class
	 */
@Override
public void run() 
{
    String question,defaultHostName,thisHostName;
    String toAnswer;
    String home = new String ("job.root.properties");
    Properties myDNSPro,defaultPro;
    String[] defaultName, defaultPort, thisName,thisPort;
    
    while (true) 
    {
        try {
        	 //load default file and info
        	defaultPro = loadFile(home);
            defaultName = loadName(home);
            defaultPort = loadPort(home);
 
                // Ask user what he wants
            send.writeUTF("What are you looking for? <format> <<job.root>>  \n"+
                          "Type Exit to terminate connection.");
             
            // receive the answer from client
            question = receive.readUTF();
             
            if(question.equals("Exit"))
            { 
                System.out.println("Client " + this.client + " sends exit...");
                System.out.println("Closing this connection.");
                this.client.close();
                System.out.println("Connection closed");
                break;
            }
             
   		 	
            // load the client requested file
            myDNSPro = loadFile(question + ".properties");
            thisName = loadName(question + ".properties");
            thisPort = loadPort(question + ".properties");
            
            // find the answer
            toAnswer = findAnswer(defaultName, question);
            System.out.println(toAnswer);
            
            
            // send the answer
            send.writeUTF(toAnswer);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    try
    {
        // closing resources
            this.receive.close();
            this.send.close();
             
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}//END HERE
