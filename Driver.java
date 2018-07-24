public class Driver 
{ 
	public static void main(String [] args) 
	{ 
		Tree t = new Tree();
		int [] input = { 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16 }; 
		for (int i = 0; i < input.length; ++i) 
		{
			t.add(new Node(input[i])); 
		}
		System.out.printf("Here is tree (verbose):\n%s\n", t.toStringVerbose()); 
		System.out.printf("A little prettier format:\n%s\n", t.toString());
		System.out.printf("The height is %d\n", t.findTreeHeight()); 
		// We're not testing removals. } } 
	}
}