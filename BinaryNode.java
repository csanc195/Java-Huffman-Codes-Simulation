/*
 * Authors: CARLOS SANCHEZ VILA  && DANIEL GONZALEZ 
 */
import java.util.Stack;

public class BinaryNode<T>
{
    
    // create an empty node
    public BinaryNode() 
    {
        this(null, null, null);
    }
    
    // create a node with given value and children
    public BinaryNode(T theElement, BinaryNode<T> lt, BinaryNode<T> rt)
    {
        element = theElement;
        left = lt;
        right = rt;
    }
    
    // return the element
    public T getElement()
    {
        return element;
    }
    
    // return the left child
    public BinaryNode<T> getLeft()
    {
        return left;
    }
    
    // return the right child
    public BinaryNode<T> getRight()
    {
        return right;
    }
    
    // set the element to a given value
    // @param x is the new value
    public void setElement( T x)
    {
        element = x;
    }
    
    // set the left child
    // @param t is the left child
    public void setLeft(BinaryNode<T> t)
    {
        left = t;
    }
    
    // set the right child
    // @param t is the right child
    public void setRight(BinaryNode<T> t)
    {
        right = t;
    }
    
    // @return the size of the subtree at node t 
    public static <T> int size( BinaryNode<T> t)
    {
        if ( t == null)
            return 0;
        else
            return 1 + size(t.left) + size(t.right);
    }
    
    // @return the height of the subtree of node t
    public static <T> int height( BinaryNode<T> t)
    {
        if (t == null)
            return -1;
        else 
            return 1 + Math.max(height(t.left), height(t.right));
    }

    // find if an item occurs in the tree
    // @param inq is the inquiry
    public BinaryNode<T> find(T inq)
    {
        if (element.equals(inq))
            return this;
        BinaryNode<T> out = null;
        if ( left != null)
        {
            out = left.find(inq);
        }    
        if ( out != null)
            return out;
        else if ( right != null)
            out = right.find(inq);
        return out;
    }    
    
    // create a duplicate tree
    // @return the root of the duplicate tree
    public BinaryNode<T> duplicate()
    {
        BinaryNode<T> root = new BinaryNode<T>(element,null,null);
        if ( left != null)
            // create a duplicate tree for the left subtree
            root.left = left.duplicate();
        if (right != null)
            root.right = right.duplicate();
        return root;
    }
    
    
    // print the tree elements in preorder
    public void printPreOrder()
    {
        System.out.print(element + "\n");
        if (left != null)
            left.printPreOrder();
        if (right != null)
            right.printPreOrder();
    }
    
   
    // print the tree elements in postorder // iterativeInorder
    public void iterativePreOrder()
    {
      
        Stack<BinaryNode<T>> s = new Stack<BinaryNode<T>>();
        BinaryNode<T> current = this;
        while (current != null || !s.empty())
        {
            if (current != null)
            {
                System.out.println(current.element);
                // process it, put it in the stack, and go left
                s.push(current);
                current = current.left;
            }
            else // pop the stack and go right
            {
                current = s.pop();
                current = current.right;
            }
        }    
    }
   
    public static <T> BinaryNode<T> prePlusIn(T[] pre, T[] in)
    {
        if ( pre.length != in.length)
            throw new IllegalArgumentException();
        BinaryNode<T> node  = prePlusIn(pre, 0, pre.length-1, in, 0, in.length -1);
        return node;

    }

    // contruct a tree from the prorder slice pre[lp:hp] and inorder slice in[li:hi]
    // we assume that the 2 slices are the same length
    private static <T> BinaryNode<T> prePlusIn( T[] pre, int lp, int hp, T[] in, int li, int hi)
    {

        if ( lp > hp)
            return null;
        else if (lp == hp)
            return new BinaryNode<T>(pre[lp],null,null);
        else
        {
            BinaryNode<T>  node = new BinaryNode<T>(pre[lp],null, null);
            // search for pre[lp] in in[li:hi]
            int j = li;
            for (; j <= hi; j++)
            {
                if ( pre[lp].equals(in[j]))
                    break;
            }

            if (j > hi)
                throw new IllegalArgumentException("We cannot construct the tree");
            // the length of the left subtree
            node.setLeft(prePlusIn(pre,lp+1, lp + j -li, in, li, j-1));
            node.setRight(prePlusIn(pre,lp + j -li + 1, hp, in, j+1, hi));
            return node;
        }
    }

    // find the parent of a given node in this tree
    // if n =null throw an illegal argument exception
    // if n is the root or does not occur in the tree return null.
    public BinaryNode<T> parent(BinaryNode<T> n)
    {
        if ( n == null)
            throw new IllegalArgumentException("The argument is null");
        BinaryNode<T> p = null;
        if (n == this) // the root has no parent
            return null;
        return findParent(n);
    }

    // find the parent of n != null
    // we know that this != n
    private BinaryNode<T> findParent(BinaryNode<T> n)
    {
        BinaryNode<T> p = null;

        // check if this is the parent
        if (left == n || right == n)
            return this;

        // check if n occurs in the left subtree
        if (left!= null)
            p = left.parent(n);
        // check if n was found in the left subtree
        if (p!= null)
            return p;

        // n was not found in the left subtree, so check the right one
        if (right!= null)
            p = right.parent(n);
        return p;
    }
   
        
    // print in post order
    public void printPostOrder()
    {
        if (left != null)
            left.printPostOrder();
        if (right != null)
            right.printPostOrder();
        System.out.println(element);
    }
   
    
    // print the tree elements in inorder
    public void printInOrder()
    {
        if (left != null)
            left.printInOrder();
        System.out.println(element);
        if (right != null)
            right.printInOrder();
    }
    public String toString(){
    	return "Element: " + "\n Data: " + this.getElement().toString() + 
    			"Left: " + getLeft().toString() + "Right: " + getRight().toString();
    }
   
    // the fields
    private T element;
    private BinaryNode<T> left;
    private BinaryNode<T> right;

    
}
