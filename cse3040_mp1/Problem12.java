package cse3040_HW3;

public class Problem12 {
	public static void main(String[] args) {
		SubsequenceChecker.check("supercalifragilisticexpialidocious", "pads");
		SubsequenceChecker.check("supercalifragilisticexpialidocious", "padsx");
	}
}

class SubsequenceChecker{
	static void check(String str1, String str2) {
		int j = 0;
		int[] ary = new int[str2.length()];
		
	    for(int i = 0; i < str1.length(); i++){
	      if(str2.charAt(j) == str1.charAt(i)){
	    	  ary[j] = i;
	    	  ++j;
	      }
	      if(j == str2.length()){
	    	  System.out.println(str2 + " is a subsequence of " + str1);
	    	  for(int k=0; k<ary.length; k++) {
	    		  if(k==ary.length-1) System.out.println(ary[k]);
	    		  else System.out.print(ary[k] + " ");
	    		}
	    	  
	    	  return;
	      }
	    }
	    System.out.println(str2 + " is not a subsequence of " + str1);
	    return;
	}
}