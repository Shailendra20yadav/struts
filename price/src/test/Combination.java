package test;

public class Combination {

public static void findSubArray(String input){
        
        for(int i=0; i<input.length(); i++) {
            System.out.print(input.charAt(i)+", ");
            String subStr=""+input.charAt(i);
            for(int j=i+1; j<input.length(); j++) {
                subStr+=input.charAt(j);
                System.out.print(subStr.equals("")?"":subStr+", ");
            }
        }
    }
    
    public static void main(String[] args) {
        findSubArray("abcde");
    }

}
