package PL2;
import java.util.Scanner;
public class InputValidator {
	public static int getValidInt(Scanner input,String message) {
		int value;
		while(true) {
			System.out.println(message);
			if(input.hasNextInt()) {
				value=input.nextInt();
				input.nextLine();
				return value;
			}else {
				System.out.println("Incorrect please enter a correct number");
				input.nextLine();
			}
		}
	}
	
	
	public static String getValidString(Scanner input, String message) {
	    String value;
	    while (true) {
	        System.out.print(message);
	        value = input.nextLine().trim(); 
	        if (!value.isEmpty()) {            
	            return value;                  
	        } else {
	            System.out.println("Please enter a valid non-empty string."); // رسالة خطأ
	        }
	    }
	}

	}


