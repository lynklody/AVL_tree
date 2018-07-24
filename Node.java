// Similar to the Node class we used for the earlier binary tree example
// but this time we let the data be an integer rather than an Item/String.
public class Node
{
  protected Node left;
  protected Node right;
  protected Node parent;
  protected int data;

  public Node(int value)
  {
    left = null;
    right = null;
    parent = null;
    data = value;
  }

  public Node(Node n)
  {
    left = n.left;
    right = n.right;
    parent = n.parent;
    data = n.data;
  }

  public int getData()
  {
    return data;
  }

  public boolean equals(Node n)
  {
	return data == n.data;
  }

  public int compare(Object o)
  {
    return this.getData() - ((Node) o).getData();
  }

  public String toString()
  {
    String build = "node value = ";
    build += data;

    build += " left = ";

    if (left == null)
      build += "null ";
    else
      build += left.data + " ";

     build += " right = ";

    if (right == null)
      build += "null ";
    else
      build += right.data + " ";

    build += " parent = ";
    if (parent == null)
      build += "null\n";
    else
      build += parent.data + "\n";

    return build;
  }
}