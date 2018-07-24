/** Implementation of a binary search tree.
 *  We need to modify this implementation so that it represents an AVL
 *  tree as well.  We need to preserve this property:  when a node has
 *  2 children, their heights differ by at most 1.
 *  This will affect the way we do inserts and deletes.
 */
import java.util.NoSuchElementException;

public class Tree
{
  private Node root;

  public Tree()
  {
    root = null;
  }

  /** Need to find the appropriate place in the tree to add this value.
   *  But when we find it, need to be aware of its parent, and whether it
   *  should be a left or right child.
   */
  public void add(Node newNode)
  {
//	  System.out.println("newNode= "+newNode);
    if (root == null)
    {
      root = newNode;
    }
    else
    {
      Node current = root;
      boolean added = false;
      while (! added)
      {
        // should we go left?
        if (newNode.compare(current) < 0)
        {
          if (current.left == null)
          {
            current.left = newNode;
            current.left.parent = current;     // I'm my child's parent.
            added = true;
          }
          else
            current = current.left;
        }
	
        // otherwise we go right:
        else
        {
//        	System.out.println("current= "+current);
//        	System.out.println("right= "+current.right);
          if (current.right == null)
          {
        	
            current.right = newNode;
            current.right.parent = current;     // I'm my child's parent.
            added = true;
//            System.out.println("check current= "+current);
          }
          else
          {
            current = current.right;
//            System.out.println("current changed to= "+current);
          }
        }
      }

      // Need to handle AVL tree property.
      // ADD CODE HERE
      // Copied from class notes:
      // From w (current node we add) up to the root, 
      //   look for a node z that is unbalanced.
      //   Unbalanced means its 2 children have heights differing by 2+,
      //   or the case that z's height is 2+ but has only 1 child.
      // Let y be z's taller child.
      // Let x be y's taller child.  If a tie, choose x to be w's ancestor.
      // 
      // Refer to x,y,z as a,b,c -- where abc is an inorder traversal
      //   (ascending values).  Refer to the subtrees of x,y,z as T1-T4
      //   where these are also in an inorder/ascending relationship.
      // Replace the subtree rooted at z with a new subtree rooted at b.
      //   It's children are a and c; its grandchildren are T1-T4.

      Node w = newNode;
      Node n = w;
      int heightdiff = 0;
      int hl = 0;
      int hr = 0;
      Node n0 = null;
//      System.out.printf("Before looking for unbalanced node, here is the tree info.\n%s\n",
//    		            this.toStringVerbose());
      while (Math.abs(heightdiff) < 2 && n.parent != null)
      {
    	  if (!n.equals(root))
    	  {
//    	  System.out.println(this.toString());
    	  n = n.parent;
    	  int heightL = findHeight(n.left);
    	  int heightR = findHeight(n.right);
    	  hl = heightL;
    	  hr = heightR;
    	  heightdiff = heightL - heightR;
//    	  System.out.println("heightdiff= "+hl + "-" + hr +"="+heightdiff);
//    	  n0 = n;
//    	  System.out.println("n= "+n);
    	  }
    	  else
    		  break;
      }
//      System.out.printf("After looking for unbalanced node, n = %s\n", n.toString());
      if (Math.abs(heightdiff) >= 2)
      {
//    	  System.out.println( "unbalanced node: "+n );
	      // find z
	      Node z = n;
//	      System.out.println("z= "+z.getData());
	      Node y = null;
	      Node x = null;
    	  // find y
    	  if (hl > hr && z.left != null)
    	  {
    		  y = new Node(z.left);
    	  }
    	  else if (hl < hr && z.right != null)
    	  {
    		  y = new Node(z.right);
    	  }
    	  else // hl = hr. when two heights are equal.
    	  {
    		  if (isAncestor(w,z.left))
    		  {
    			  y = new Node(z.left);
    		  }
    		  else if (!isAncestor(w,z.left))
    		  {
    			  y = new Node(z.right);
    		  }
    	  }
//    	  System.out.println("y= "+y.getData());
    	  // find x
    	  if (y != null)
    	  {
	    	  int hxl = findHeight(y.left);
	    	  int hxr = findHeight(y.right);
	    	  if (hxl > hxr && y.left != null)
	    	  {
	    		  x = new Node(y.left);
	    	  }
	    	  else if (hxl < hxr && y.right != null)
	    	  {
	    		  x = new Node(y.right);
	    	  }
	    	  else if (hxl == hxr) // when two heights are equal.
	    	  {
	    		  if (y.left != null && isAncestor(w,y.left))
	    		  {
	    			  x = new Node(y.left);
	    		  }
	    		  else if (y.right != null)
	    		  {
	    			  x = new Node(y.right);
	    		  }
	    	  }
//	    	  System.out.println("x= "+x.getData());
	    	  if (x != null)
	    	  {
			      // find a, b, c
			      Node a = null;
			      Node b = null;
			      Node c = null;
			      Node t1 = null, t2 = null, t3 = null, t4 = null;
			      
			      int xd = x.getData();
			      int yd = y.getData();
			      int zd = z.getData();
			      
			      
			      if (xd < yd && xd > zd)     // z<x<y
			      {
			    	  a = new Node(z);
			    	  b = new Node(x);
			    	  c = new Node(y);
			    	  t1 = ((z.left == null) ? null: new Node(z.left));
			    	  t2 = ((x.left == null) ? null: new Node(x.left));
			    	  t3 = ((x.right == null) ? null: new Node(x.right));
			    	  t4 = ((y.right == null) ? null: new Node(y.right));
			      }
			      else if (xd < zd && xd > yd)    // y<x<z
			      {
			    	  a = new Node(y);
			    	  b = new Node(x);
			    	  c = new Node(z);
			    	  t1 = ((y.left == null) ? null: new Node(y.left));
			    	  t2 = ((x.left == null) ? null: new Node(x.left));
			    	  t3 = ((x.right == null) ? null: new Node(x.right));
			    	  t4 = ((z.right == null) ? null: new Node(z.right));
			      }
			      else if (yd < xd && xd > zd)    // z<y<x
			      {
			    	  a = new Node(z);
			    	  b = new Node(y);
			    	  c = new Node(x);
			    	  t1 = ((z.left == null) ? null: new Node(z.left));
			    	  t2 = ((y.left == null) ? null: new Node(y.left));
			    	  t3 = ((x.left == null) ? null: new Node(x.left));
			    	  t4 = ((x.right == null) ? null: new Node(x.right));
			      }
			      else if (yd < zd && yd > xd)    // x<y<z
			      {
			    	  a = new Node(x);
			    	  b = new Node(y);
			    	  c = new Node(z);
			    	  t1 = ((x.left == null) ? null: new Node(x.left));
			    	  t2 = ((x.right == null) ? null: new Node(x.right));
			    	  t3 = ((y.right == null) ? null: new Node(y.right));
			    	  t4 = ((z.right == null) ? null: new Node(z.right));
			      }
		
			      
			      // replace
			      if (z.parent != null && z == z.parent.left)
			      {
			    	  find(z).parent.left = b;
			      }
			      else if (z.parent != null && z == z.parent.right)
			      {
			    	  find(z).parent.right = b;
			      }
			      else    // z.parent == null
			      {
			    	  root = b;
			      }
			      // find b's parent by searching from the tree
			      Node n1 = root;
			      Node prev = null;
			      while (true)
			      {
			        if (n1 == null || b.equals(n1))
			          break;
			        else if (b.compare(n1) < 0)
			        {
			          prev = n1;
			          n1 = n1.left;
			        }
			        else
			        {
			          prev = n1;
			          n1 = n1.right;
			        }
			      }
			      find(b).parent = prev;
		    	  find(b).left = a;
		    	  a.parent = find(b);
		    	  find(b).right = c;
		    	  c.parent = find(b);
		    	  
		    	  a.left = t1;
		    	  if (t1 != null)
		    	  {
		    		  t1.parent = find(a);
		    	  }
		    	  a.right = t2;
		    	  if (t2 != null)
		    	  {
		    		  t2.parent = find(a);
		    	  }
		    	  c.left = t3;
		    	  if (t3 != null)
		    	  {
		    		  t3.parent = find(c);
		    	  }
		    	  c.right = t4;
//		    	  System.out.println("t4="+t4);
		    	  if (t4 != null)
		    	  {
		    		  t4.parent = find(c);
		    	  }
//		    	  System.out.println("a,b,c= "+a.getData()+b.getData()+c.getData());
//		    	  System.out.println("a= "+a);
//		    	  System.out.println("b= "+b);
//		    	  System.out.println("c= "+c);
	    	  }
    	  }
      }
    }

    // We're done inserting.  See what we have so far.
    System.out.printf("%s\n", this.toString());
  }    

  public boolean isAncestor(Node start, Node n) // is n one of the ancestors of start?
  {
	  if (n == null)
	  {
		  return false;
	  }
	  while (!start.equals(root))
	  {
		  if (start.equals(n))
		  {
			  return true;
		  }
		  start = start.parent;
	  }
	  return false;
  }
  
  
  
  /** find - You might want to implement this method too:
   * Return the node (if any) having the specified key value,
   * Begin the search at the specified (Node) starting point.
   */


  /** findHeight - needed by AVL restructuring
   */
  int height = 0;

  public int findHeight(Node n)
  {
	  int heightL = -1;
	  int heightR = -1;
	  if (n == null)
	  {
		  return -1; // empty tree
	  }
	  if (n.left == null && n.right == null)
	  {
		  return 0;
	  }
	  if (n.left != null)
	  {
		  heightL = findHeight (n.left) + 1;
//		  System.out.println("heightL= "+heightL);
	  }
	  if (n.right != null)
	  {
		  heightR = findHeight(n.right) + 1;
//		  System.out.println("heightR= "+heightR);
	  }
	  // return the larger height
	  if (heightL > heightR)
	  {
		  System.out.println("heightL= "+heightL + ",for" + n.getData());
		  return heightL;
	  }
	  else
	  {
		  System.out.println("heightR= "+heightR + ",for" + n.getData());
		  return heightR;
	  }
  }



  /** findTreeHeight - use findHeight() to find height of the root node;
   *  i.e. height of the tree.
   */
  public int findTreeHeight()
  {
	  return findHeight(root);
  }
  


  /** For removing an element, there are 3 cases.  If the victim is a
   *  leaf node, we can just get rid of it.  But if it has one child,
   *  that child gets promoted to the victim's position.  If the victim has
   *  2 children, then we have to promote the inorder predecessor (we could
   *  have done successor alternatively) to the victim's position.
   */
  public void remove(Node victim)
  {
    Node position = find(victim);

    System.out.println("remove():  The victim is at position " + position);

    if (position == null)
      throw new NoSuchElementException("value " + victim.data);

    // First case - leaf node
    if (position.left == null && position.right == null)
    {
      // Tell parent that its child is gone.
      if (position.equals(position.parent.left))
        position.parent.left = null;
      else 
        position.parent.right = null;

      // Because of garbage collection, nothing left pointing at position.
      // So probably unnecessary to explicitly kill it.
      // position = null;
    }

    // Second case - one child.
    // Don't forget to set the victim's child's parent to be victim's parent.
    else if (position.left == null)
    {
      position.right.parent = position.parent;
      if (position.equals(position.parent.left))
        position.parent.left = position.right;
      else
        position.parent.right = position.right;
    }
    else if (position.right == null)
    {
      position.left.parent = position.parent;
      if (position.equals(position.parent.left))
        position.parent.left = position.left;
      else
        position.parent.right = position.left;
    }

    // Third case - 2 children.  Need to find the inorder predecessor
    // node and move its value to this position.  The inorder predecessor
    // can be found by going left once, then right as far as necessary.
    else
    {
      Node pred = position.left;
      while (pred.right != null)
        pred = pred.right;

      position.data = pred.data;

      // Now we need to delete the pred node since its value was just copied.
      // This should take care of the case where pred has a left child that
      // shouldn't be lost.
      // Need to make the pred's parent believe the truth.
      // And need to set the parent of the pred's child = pred's parent.
      if (pred.left != null)
      {
        pred.parent.right = pred.left;
        pred.left.parent = pred.parent;
      }
      else
        pred.parent.right = null;
    }
  }

  // Find an element (to remove).
  public Node find(Node target)
  {
    Node current = root;
    while (true)
    {
      if (current == null || target.equals(current))
        break;
      else if (target.compare(current) < 0)
        current = current.left;
      else 
        current = current.right;
    }

    return current;
  }


  public Node findMin()
  {
    Node node;
    for (node = root; node != null && node.left != null; node = node.left)
      ;
    return node;
  }

  public Node findMax()
  {
    Node node;
    for (node = root; node != null && node.right != null; node = node.right)
      ;
    return node;
  }

  // Normally, toString takes no parameter, but we want one that recursively
  // traverses the tree, so we should have a version that takes a parameter,
  // starting with the root.
  public String toStringVerbose()
  {
    return toStringVerbose(root);
  }

  // Print out all the elements of the BST in preorder.
  public String toStringVerbose(Node n)
  {
    String build = "";
    if (n == null)
      return build;

    build = "node = " + n.data + ", left = ";
    if (n.left == null)
      build += "null ";
    else
      build += "" + n.left.data + " ";

    build += ", right = ";
    if (n.right == null)
      build += "null";
    else
      build += "" + n.right.data + " ";

    build += ", parent = ";
    if (n.parent == null)
      build += "null\n";
    else
      build += "" + n.parent.data + "\n";

    build += toStringVerbose(n.left);
    build += toStringVerbose(n.right);

    return build;
  }

  /** toString - Let's make the tree look more like a tree.
         1111111111222222222233333333334444444444555555555566666666667777777777
1234567890123456789012345678901234567890123456789012345678901234567890123456789
                                       nn
                   nn                                      nn
         nn                  nn                  nn                  nn
    nn        nn        nn        nn        nn        nn        nn        nn
 nn   nn   nn   nn   nn   nn   nn   nn   nn   nn   nn   nn   nn   nn   nn   nn
   */
  public String toString()
  {
    int [] a = new int[32];
    setArray(root, a, 1);
    
    StringBuilder sb = new StringBuilder();
    sb.append("The first 5 levels of the tree:\n");
    sb.append(String.format("%41s\n", makePretty(a[1])));
    sb.append(String.format("%21s%40s\n", makePretty(a[2]), makePretty(a[3])));
    
    sb.append(String.format("%11s", makePretty(a[4])));
    for (int i = 5; i <= 7; ++i)
      sb.append(String.format("%20s", makePretty(a[i])));
    sb.append("\n");

    sb.append(String.format("%6s", makePretty(a[8])));
    for (int i = 9; i <= 15; ++i)
      sb.append(String.format("%10s", makePretty(a[i])));
    sb.append("\n");

    sb.append(String.format("%3s", makePretty(a[16])));
    for (int i = 17; i <= 31; ++i)
      sb.append(String.format("%5s", makePretty(a[i])));
    sb.append("\n");

    return sb.toString();
  }

  // Allow us to print nothing in case of 0.
  // Allocate 2 digits for the number.
  public String makePretty(int n)
  {
    if (n == 0)
      return "  ";
    else
      return String.format("%2d", n);
  }

  /** setArray - Recursive approach.  Let's put the values from the AVL
   *  tree into an array, with a maximum of 5 levels.  I want to be able
   *  to index the tree locations with 1 at the root, 2 and 3 at the next
   *  level, etc., so that the children are always at 2i and 2i+1.
   *  So, we need to allocate space for 32 elements.  [0] will not be
   *  used, and 5 levels of the tree will need to go up to [31].
   *  The 5th level is [16] thru [31].  The max # levels ensures that we will
   *  have enough room to print them within 80 columns.
   *  Let's assume that the entire array is set to 0 representing null.
   */
  public void setArray(Node n, int [] a, int i)
  {
    if (n == null)
      return;

    a[i] = n.data;

    // No more recursion if deep enough
    if (i >= 16)
      return;

    setArray(n.left, a, 2*i);
    setArray(n.right, a, 2*i+1);
  }
}