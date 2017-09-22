package test;

public class FindWord {

	static String name="naagarro";
    public static void findMatch(String input) {
        boolean result = false;
        for(int i=0; i<name.length(); i++) {
            String tmp = input;
            for(int j=0; j<input.length(); j++) {
                if(name.charAt(i)==input.charAt(j)) {
                    input = input.substring(j+1);
                    break;
                }
                    
            }
            if(!input.equals("") && input.equals(tmp)) {
                result=false;
                break;
            }
            else
                result=true;
        }
        if(result)
            System.out.println("Equal");
        else
            System.out.println("Not Equal");
    }
    
    public static void findMatch1(String input, String name) {
        boolean result = false;
        char [] temp = new char[name.length()];
        int count =0;
        for(int i=0; i<name.length(); i++) {
            for(int j=0; j<input.length(); j++) {
                if(name.charAt(i)==input.charAt(j)) {
                	temp[count++]= input.charAt(j);
                    input = input.substring(j+1);
                    break;
                }
                    
            }
            if(temp[i]!=name.charAt(i)){
            	result =false;
            	break;
            }
            if(new String(temp).equals(name)) {
                result=true;
                break;
            }
            else
                result=false;
        }
        if(result)
            System.out.println("Equal");
        else
            System.out.println("Not Equal");
    }
    
    
    
    public static void main(String[] args) {
        findMatch("aankjhaagoorahggrjkro");
        findMatch1("aankjhaagoorahggrjkro",name);
    }

}
