import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class test {
	final static String one = new String(" YOU FOUND ME. MY NAME IS ");
	final static String two = new String(" SEE MY FATHER. HIS NAME IS ");
	final static String three = new String(" SEE MY SON. HIS NAME IS ");
	final static String four = new String(" REQUESTED DOMAIN IS NOT EXIST. ");
	
	public static Properties loadFile (String fname) {
     	Properties prop = new Properties();
	try {
		FileInputStream in = new FileInputStream(fname);
		prop.load(in);
		in.close();
                
         } catch (IOException e) {
        	System.out.println("REQUESTED DOMAIN IS NOT EXIST "+ fname);
         }
         return prop;
	}
	
	public static String getHostName (String fname){
		String hostName = null;
		Properties prop = loadFile(fname);
		hostName = prop.getProperty(fname + ".name");
		return hostName;
	}
	
	
	public static String commentElement(String[]name, String str){ //find himself
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
	
/*	public static String findAnswer(String[] name, String str) {
		   int tempInt;
		   String answer = null, com = null;
		   boolean handedDownToChild = false;	
		   boolean handedDownToFather = false;	
		   boolean swap = false;
		   int i;
		   
		   String defaultHostName = getHostName("job.root.properties");
		   String thisHostName= getHostName(str + ".properties");
		   while ((tempInt = str.indexOf('.'))>-1){
			   if(thisHostName.equals(defaultHostName)){
					answer = new String(one + thisHostName );
			        break; 
			   }else if (!(thisHostName.equals(defaultHostName))){
				   String copyStr = str;
				   com = commentElement(name, copyStr);
				   tempInt = copyStr.indexOf('.');
			  			if (com!=null){
			  				if(tempInt > -1){
			  					String temChild = com;
			  					//System.out.println("common in child is " + temChild);
			  					answer = new String(three + temChild);
			  					handedDownToChild = true;
			  				//	break;
			  				} else if (!handedDownToChild) {
			  					String temFather = com;
			  					//System.out.println("common in father is " + temFather);
			  					answer = new String(two + temFather);
			  					handedDownToFather = true;
			  				//	break;
			  					} 
			  			}else {// No common element have been found
			  				while(tempInt > -1){
			  					try{
			  						//can it resolver by grandFather root
			  						int dot1 = copyStr.indexOf(".");
				  					int dot2 = copyStr.indexOf(".", dot1 + 1);;
			  						String grandFather = copyStr.substring(dot2 +1);
			  						if (grandFather == "root"){
			  							answer = new String(two + grandFather);
					  					handedDownToFather = true;
					  					break;
			  						}
			  					}catch (Exception e){
			  						answer= new String(four + str);
			  						break;
			  					}
			  					break;
			  				}
						}
			   		}
			   break;
		   }
		   return answer;
	}//function findAnswer*/
	
	public static void main(String[] args)
    {
		String[]name = {"job.root.name","root", "things.job.root","house.job.root"};
		String str = "root";
		
		int tempInt;
		   String answer = null, com=null;
		   boolean handedDownToChild = false;	
		   boolean handedDownToFather = false;	
		   boolean swap = false;
		   int i;
		   
		   Properties defaultProp = loadFile("job.root.properties");
		   String defaultHostName = defaultProp.getProperty("job.root.name");
		   							System.out.println(defaultHostName);
		   Properties thisProp = loadFile(str + ".properties");
		   String thisHostName = thisProp.getProperty(str + ".name");
		   								System.out.println(thisHostName);
		   String copyStr = str;
		   com = commentElement(name, copyStr);
		   								System.out.println(com);
		   tempInt = copyStr.indexOf('.');
		   
		   									System.out.println(tempInt);
		   
		   while ((tempInt = copyStr.indexOf('.'))>-1){
			 //requested server is the current server
			   if(thisHostName.equals(defaultHostName)){
					answer = new String(one + thisHostName );
										System.out.println(answer);
					break;
			   }else if (!(thisHostName.equals(defaultHostName))){
		  			if (com!=null){
		  				if(tempInt > -1){
		  					String temChild = com;
		  					//System.out.println("common in child is " + temChild);
		  					answer = new String(three + temChild);
		  																		System.out.println(answer);
																	
		  					handedDownToChild = true;
		  					break;
		  				}
		  				break;
		  			}else if (com == null){
	  						//can it resolver by grandFather root
	  						int dot1 = copyStr.indexOf(".");
		  					int dot2 = copyStr.indexOf(".", dot1 + 1);;
	  						String grandFather = copyStr.substring(dot1 +1);
	  													System.out.println("here "+grandFather);
	  						if (grandFather.equals("root")){
	  							answer = new String(two + grandFather);
	  																						System.out.println(answer);
			  					handedDownToFather = true;
			  					//break;
	  						}
	  						else{
	  							answer= new String(four + grandFather);
																		System.out.println(answer);
								break;
		  				}
			   		}
			   break;
		   }	
		  				
		  				while (((tempInt = copyStr.indexOf('.'))<=-1)&& com != null) {
			  					String temFather = com;
			  										System.out.println("common in father is " + temFather);
			  					answer = new String(two + temFather);
			  														System.out.println(answer);
			  					handedDownToFather = true;
			  					break;
			  					} 
    }//while
		   
		   
		   
		   
		   
    }//main 
}//class
				  					
				  					
				  					
				  					
				  					
				  					
				  					
				  					