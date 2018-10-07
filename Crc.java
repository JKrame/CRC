package crc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;



public class Crc {
	

	public static void main(String[] args) throws Exception 
	{
		
		long poly = 0B10000010110001001L;
		int r = 16;

		System.out.println("-----Menu-------");
		System.out.println("1.Calculate CRC");
		System.out.println("2.Verify CRC");
		System.out.println("3.Exit");

		@SuppressWarnings("resource")
		Scanner choice = new Scanner(System.in);

		while(1<2)
		{
		 int option = choice.nextInt();
	        switch (option) 
	        {
	            case 1:  
	            			System.out.println("Enter the filename to be operated on");
	            			Scanner filename = new Scanner(System.in);
	            			String userIn = filename.nextLine();

	            			String message = new String(LoadData(userIn));
	            			int mLength = message.length();
	            			
	            			System.out.println(" ");
	            			System.out.println(" ");
	            			
	            			System.out.println("The input file (hex): ");
	            			System.out.println(message);
	            			System.out.println(" ");
	            			
	            			message = HexToBin(message);
	            			
	            			System.out.println("The input file (bin): ");
	            			binaryPrint(message, 0);
	            			System.out.println(" ");
	            			System.out.println(" ");
	            			
	            			System.out.println("******** Appending zeroes ********");
	            			System.out.println(" ");
	            			
	            			StringBuilder tempMessage = new StringBuilder();
	            			tempMessage.append(message);
	            			
	            			for(int i=0; i < r; i++)
	            				{
	            				tempMessage.append("0");
	            				}
	            			
	            			message = tempMessage.toString();
	            			
	            			System.out.println("New binary digit: ");
	            			binaryPrint(message, 0);
	            			
	            			System.out.println(" ");
	            			System.out.println(" ");

	            			String result = calculateCRC(message, poly, 0, "");
	            				            			
	            			long intResult = Long.parseLong(result);
	            			
	            			System.out.println(" ");
	            			System.out.println(" ");

	            			
	            			System.out.println("FINAL RESULT = " + intResult);

	            			String hexresult = ToHex(result);
	            			System.out.println("HEX RESULT = " + hexresult);
	            			
	            			SaveData(userIn, hexresult.toUpperCase());
	            			System.out.println("CRC has been appended to the end of the input file");
	            			System.out.println("Reading input file again: " + LoadData(userIn));
	            			System.out.println("Closing input file");
	            			
	            			break;
	            case 2:  		            
			            System.out.println("Enter the filename to be operated on");
		    			Scanner filenameVer = new Scanner(System.in);
		    			String userInVer = filenameVer.nextLine();
		
		    			String messageVer = new String(LoadData(userInVer));
		    			System.out.println(" ");
		    			System.out.println(" ");
		    			
		    			System.out.println("The input file (hex): ");
		    			System.out.println(messageVer);
		    			System.out.println(" ");
		    			
		    			messageVer = HexToBin(messageVer);
		    			
		    			System.out.println("The input file (bin): ");
		    			binaryPrint(messageVer, 0);
		    			System.out.println(" ");
		    			System.out.println(" ");
		            	
		    			boolean CRCresult = false;
		    			int i = 0 ;
		    			if(verifyCRC(messageVer, poly, 0, "", CRCresult)==true){
		    				i = 1;
		    			}
		    			
		    			System.out.print("Did the CRC check pass? (Yes or No)");

		    			switch(i)
		    			{
		    			case 1: System.out.println(" Yes");
		    					break;
		    			case 0: System.out.println(" No");
		    					break;
		    			}
		    			
	                     break;
	            case 3:	System.exit(0);
	            		break;
	            default: System.out.println("Invalid option");
                break;

             }
	        
	        System.out.println(" ");
			System.out.println("-----Menu-------");
			System.out.println("1.Calculate CRC");
			
			System.out.println("2.Verify CRC");
			System.out.println("3.Exit");
		}
		
		
		
		
		
		//String binaryVal = HexToBin(message);
		
		/*String poly = String.valueOf(polynomial);
		
		//in case the binary length of the file is longer than the polynomial we have to XOR it against
		if(binaryVal.length()>poly.length())
		{
			poly = correctLength(binaryVal, poly);
		}
		
		
	
		
	*/
		

	}

	static String LoadData(String userIn) throws Exception
	 {
	    
		File input = new File( userIn );  
		Scanner in = new Scanner(input);
		
		StringBuilder tempsb = new StringBuilder();
		
		while (in.hasNext()) 
		{
		       
				//Add words to StringBuilder
		        tempsb.append(in.next());
		        
		}
		
			//scan Plain Text into StringHolder and remove all unwanted characters
		String hexValue = tempsb.toString();
		
		if(isValidHex(hexValue)==false)
		{
			System.out.println("This was not a valid hex value.");
			System.exit(0);
		}
		in.close();
		return(hexValue);
		
	   }
	
	static boolean isValidHex(String hexadecimal)
	{
		for ( int i = 0 ; i < hexadecimal.length() ; i++ )
		{
			if ( Character.digit(hexadecimal.charAt(i), 16) == -1 )
			{
            return false;
            }
        }
		
		return true;
	}
	

	static String HexToBin(String hexadecimal)
	{		 
		 StringBuilder binarySB = new StringBuilder();

		 for(int i = 0; i < hexadecimal.length(); i++)
		 {
			 
			 hexadecimal.toUpperCase();
			 char c = hexadecimal.charAt(i);
			 
			 switch(c)
			 {
				 case '1': binarySB.append("0001"); break;
				 case '2': binarySB.append("0010"); break;
				 case '3': binarySB.append("0011"); break;
				 case '4': binarySB.append("0100"); break;
				 case '5': binarySB.append("0101"); break;
				 case '6': binarySB.append("0110"); break;
				 case '7': binarySB.append("0111"); break;
				 case '8': binarySB.append("1000"); break;
				 case '9': binarySB.append("1001"); break;
				 case 'A': binarySB.append("1010"); break;
				 case 'B': binarySB.append("1011"); break;
				 case 'C': binarySB.append("1100"); break;
				 case 'D': binarySB.append("1101"); break;
				 case 'E': binarySB.append("1110"); break;
				 case 'F': binarySB.append("1111"); break;
			 }
		 }
		 
		 String binaryString = binarySB.toString();
		 
		 return (binaryString);
	}
	
	static void binaryPrint(String message, int x)
	{
		if(message.length()==0)
			return;
		
		if(x==32)
		{
			System.out.println(" ");
			x=0;
		}
		
		x+=4;
		
		StringBuilder tempMessage = new StringBuilder(message);
		int i = tempMessage.length() - 4;
				
		while(i>0)
		{
			tempMessage.insert(i, " ");
			i -=4;
		}
				
		System.out.print(tempMessage);
		
		return;
	}
	
	static String calculateCRC(String message, long poly, int i, String result)
	{
		if(i ==  message.length())
			return result;
		
		StringBuilder CRCsb = new StringBuilder();
		
		if(result.isEmpty() == false)
		{			
			CRCsb.append(result);
		}
		
		long tempResult;
		
		String polynomial = Long.toString(poly, 2);
		
		while(CRCsb.length() != polynomial.length())
		{
			CRCsb.append(message.charAt(i));
			i++;
		}
		
		String CRCstring = CRCsb.toString();
		System.out.println(CRCstring + " XOR " + polynomial);
		
		long CRCmessage = Long.parseLong(CRCstring,2);
		long CRCpoly = Long.parseLong(polynomial, 2);
		
		tempResult = CRCmessage^CRCpoly;
		int forPrint = (int) tempResult;

		
		result = Integer.toBinaryString(forPrint);
		
		result = calculateCRC(message, poly, i, result);
		
		
		
		
		return result;
	}
	
	static String ToHex(String res)
	{
		
		long decimal = Integer.parseInt(res, 2);
		res = Long.toString(decimal, 16);
		
		return res;
	}
	
	static void SaveData(String userIn, String result) throws Exception
	 {
	    
		File input = new File( userIn );  
		Scanner in = new Scanner(input);
		
		StringBuilder tempsb = new StringBuilder();
		
		while (in.hasNext()) 
		{
		       
				//Add words to StringBuilder
		        tempsb.append(in.next());
		        
		}
		in.close();
		
		tempsb.append(result);
			//scan Plain Text into StringHolder and remove all unwanted characters
		String hexValue = tempsb.toString();
		
		if(isValidHex(hexValue) == false)
		{
			System.out.println("This was not a valid hex value.");
			System.exit(0);
		}
		
		try{
			FileWriter filewriter = new FileWriter(userIn, true);
			BufferedWriter buffwriter = new BufferedWriter(filewriter);
			PrintWriter write = new PrintWriter(buffwriter);
			write.print(result);
			write.close();
		}
		catch(IOException e){
			
		}
	   }
			
	//method for confirming that user has entered a valid hex value
	static boolean verifyCRC(String message, long poly, int i, String result, boolean CRCpass)
	{
		long CRCcheck;
		if(i >=  message.length())
			return CRCpass;
		
		StringBuilder CRCsb = new StringBuilder();
		
		if(result.isEmpty() == false)
		{			
			CRCsb.append(result);
		}
		
		String polynomial = Long.toString(poly, 2);

		while(CRCsb.length() != polynomial.length() && i!=message.length())
		{
			CRCsb.append(message.charAt(i));
					
			i++;

		}
	

		
		String CRCstring = CRCsb.toString();

		System.out.println(CRCstring + " XOR " + polynomial);
		
		long CRCmessage = Long.parseLong(CRCstring,2);
		long CRCpoly = Long.parseLong(polynomial, 2);
		long tempResult;
	
		if(i==message.length() && CRCmessage==CRCpoly)
			{
			CRCpass = true;
			}
		else CRCpass = false;
			
		
		tempResult = CRCmessage^CRCpoly;
		int forPrint = (int) tempResult;
		StringBuilder tempResultxfer = new StringBuilder();
		tempResultxfer.append(tempResult);
		
		result = Integer.toBinaryString(forPrint);
		CRCcheck = Long.parseLong(CRCstring);
		

		
		CRCpass = verifyCRC(message, poly, i, result, CRCpass);
		
		
		
		
		return CRCpass;
	}

}
	