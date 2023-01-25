package com.accenture.interview.utils.checkerror.interviewer;

public class CheckNewPassword {
	
    public static boolean is_Upper_Letter(char ch) {
        return (ch >= 'A' && ch <= 'Z');
    }

    public static boolean is_Numeric(char ch) {
        return (ch >= '0' && ch <= '9');
    }	
	
    // La nuova password deve rispettare i seguenti vincoli: 
    // 8 caratteri di lunghezza, almeno una lettera maiuscola, almeno un numero
	public static boolean checkNewPassword(String newPassword) {
		boolean res = false;
		
		if (newPassword.length() >= 8) {			
			int numCount = 0;
	        int upperCharCount = 0;
	        
	        for (int i = 0; i < newPassword.length(); i++) {
	            char ch = newPassword.charAt(i);
	            if (is_Numeric(ch)) numCount++;	            
	            if (is_Upper_Letter(ch)) upperCharCount++;
	        }
	        if (numCount > 0 && upperCharCount > 0) {
	        	res = true;
	        } else {
	        	res = false;
	        }
		}		
		return res;
	}

}
