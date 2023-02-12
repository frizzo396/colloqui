package com.accenture.interview.utils.password;

/**
 * The Class PasswordUtils.
 */
public class PasswordUtils {

   /**
    * Instantiates a new password utils.
    */
   private PasswordUtils() {

   }
	
   /**
    * Checks if is upper.
    *
    * @param ch the ch
    * @return true, if is upper
    */
   private static boolean isUpper(char ch) {
        return (ch >= 'A' && ch <= 'Z');
    }

    /**
     * Checks if is numeric.
     *
     * @param ch the ch
     * @return true, if is numeric
     */
    private static boolean isNumeric(char ch) {
        return (ch >= '0' && ch <= '9');
    }	
	
    // La nuova password deve rispettare i seguenti vincoli: 
    /**
     * Check new password.
     *
     * @param newPassword the new password
     * @return true, if successful
     */
    // 8 caratteri di lunghezza, almeno una lettera maiuscola, almeno un numero
    public static boolean checkNewPassword(String newPassword) {
		boolean res = false;
		
		if (newPassword.length() >= 8) {			
			int numCount = 0;
	        int upperCharCount = 0;
	        
	        for (int i = 0; i < newPassword.length(); i++) {
	            char ch = newPassword.charAt(i);
	            if (isNumeric(ch)) numCount++;	            
	            if (isUpper(ch)) upperCharCount++;
	        }
	        if (numCount > 0 && upperCharCount > 0) {
	        	res = true;
	        } else {
	        	res = false;
	        }
		}		
		return res;
	}

   /**
    * Check passoword equality.
    *
    * @param passoword       the passoword
    * @param currentPassword the current password
    * @return true, if successful
    */
   public static boolean checkPassowordEquality(String passoword, String currentPassword) {
      return passoword.equals(currentPassword);
   }

}
