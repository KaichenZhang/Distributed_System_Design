package auxiliary;

import java.util.Random;
import java.util.UUID;

public class RandomString {

    public String getRandomString(int length) {  
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
  
        for (int i = 0; i < length; ++i) {  
            int number = random.nextInt(52);// [0,52)  
            sb.append(str.charAt(number));  
        }  
        return sb.toString();  
    }  
  
    public String getRandomStringNum(int length) {  
        String str = "0123456789";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
  
        for (int i = 0; i < length; ++i) {  
            int number = random.nextInt(10);// [0,10)  
            sb.append(str.charAt(number));  
        }  
        return sb.toString();  
    }  
    
  
    /*public static void main(String[] args) {  
        System.out.println(getRandomString(32)); 
        System.out.println(getRandomStringNum(32));

    } 
    */ 
}
