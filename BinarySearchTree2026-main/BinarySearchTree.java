import java.util.*;

/**
 * This BinarySearchTree object defines a reference based binary search tree
 * 
 * @author  
 * @version 
 */

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T>
{
    protected BinaryNode<T> root;      // reference to the root of this BST

    // Creates an empty Binary Search Tree object
    public BinarySearchTree()
    {
        root = new BinaryNode<>(null);
    }

    // Returns true if this BST is empty; otherwise, returns false.
    public boolean isEmpty()
    {
        return root.getInfo() == null;
    }

    // Returns the number of elements in this BST.
    public int size()
    {
        int count = 1;
        if(root.getLeft() != null) {
            count += sizeHelper(root.getLeft());
        }
        if(root.getRight() != null) {
            count += sizeHelper(root.getRight());
        }
        return count;
    }

    // Recursive helper method for size
    private int sizeHelper(BinaryNode<T> node) {
        int count = 1;
        if(node.getLeft() != null) {
            count += sizeHelper(node.getLeft());
        }
        if(node.getRight() != null) {
            count += sizeHelper(node.getRight());
        }
        return count;
    }

    // Adds element to this BST. The tree retains its BST property.
    public void add (T element)
    {
        BinaryNode<T> currentNode = root;
        boolean notAdded = true;
        // First addition
        if(root.getInfo() == null) {
            root.setInfo(element);
            notAdded = false;
        }
        // While loop
        while(notAdded) {
            // Checking left branch and equals
            if(element.compareTo(currentNode.getInfo()) <= 0) {
                if(currentNode.getLeft() == null) {
                    currentNode.setLeft(new BinaryNode<T>(element));
                    notAdded = false;
                }
                else {
                    currentNode = currentNode.getLeft();
                }
            }
            // Checking right branch
            else {
                if(currentNode.getRight() == null) {
                    currentNode.setRight(new BinaryNode<T>(element));
                    notAdded = false;
                }
                else {
                    currentNode = currentNode.getRight();
                }
            }
        }
    }

    // Returns true if this BST contains an element e such that 
    // e.compareTo(element) == 0; otherwise, returns false.
    public boolean contains (T element)
    {
        if(root.getInfo() == null) {
            return false;
        }
        return containsHelper(element, root);
    }

    // Recursive helper method for contains
    private boolean containsHelper(T element, BinaryNode<T> node) {
        if(element.compareTo(node.getInfo()) == 0) {
            return true;
        }
        // Left node
        else if(element.compareTo(node.getInfo()) < 0) {
            if(node.getLeft() == null) {
                return false;
            }
            else {
                return containsHelper(element, node.getLeft());
            }
        }
        // right node
        else {
            if(node.getRight() == null) {
                return false;
            }
            else {
                return containsHelper(element, node.getRight());
            }
        }
    }

    // Returns a graphical representation of the tree
    public String toString()
    {
        return toString(root, 0);
    }

    private String toString(BinaryNode<T> tree, int level)
    {
        String str = "";
        if (tree != null)
        {
            str += toString(tree.getRight(), level + 1);
            for (int i = 1; i <= level; ++i)
                str = str + "| ";
            str += tree.getInfo().toString() + "\n";
            str += toString(tree.getLeft(), level + 1);
        }
        return str;
    }

    // Returns a list of elements from a preorder traversal
    public List<T> preorderTraverse()
    {
        List<T> list = new ArrayList<>();
        preorderHelper(root, list);
        return list;
    }
    // Recursive helpermethod for preorderTraverse
    private void preorderHelper(BinaryNode<T> node, List<T> list) {
        // Print
        list.add(node.getInfo());
        // Left
        if(node.getLeft() != null) {
            preorderHelper(node.getLeft(), list);
        }
        // Right
        if(node.getRight() != null) {
            preorderHelper(node.getRight(), list);
        }
    }

    // Returns a list of elements from an inorder traversal
    public List<T> inorderTraverse()
    {
        List<T> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }
    // Recursive helpermethod for inorderTraverse
    private void inorderHelper(BinaryNode<T> node, List<T> list) {
        // Left
        if(node.getLeft() != null) {
            inorderHelper(node.getLeft(), list);
        }
        // Print
        list.add(node.getInfo());
        // Right
        if(node.getRight() != null) {
            inorderHelper(node.getRight(), list);
        }
    }

    // Returns a list of elements from a postorder traversal
    public List<T> postorderTraverse()
    {
        List<T> list = new ArrayList<>();
        postorderHelper(root, list);
        return list;
    }
    // Recursive helper method for postorderTraverse
    private void postorderHelper(BinaryNode<T> node, List<T> list) {
        // Left
        if(node.getLeft() != null) {
            postorderHelper(node.getLeft(), list);
        }
        // Right
        if(node.getRight() != null) {
            postorderHelper(node.getRight(), list);
        }
        // Print
        list.add(node.getInfo());
    }

    // Removes an element e from this BST such that e.compareTo(element) == 0
    public void remove(T element)
    {
        // Edge case of empty tree
        if(root.getInfo() == null) {
            return;
        }

        // Find the target node
        BinaryNode<T> parent = null;
        BinaryNode<T> current = root;

        while(!current.getInfo().equals(element)) {
            // Go left
            if(element.compareTo(current.getInfo()) < 0 && current.getLeft() != null) {
                parent = current;
                current = current.getLeft();
            }
            // Go right
            else if(element.compareTo(current.getInfo()) > 0 && current.getRight() != null) {
                parent = current;
                current = current.getRight();
            }
            // Element does not exist
            else {
                return;
            }
        }

        // Removing time

        // Is a leaf node
        if(current.getLeft() == null && current.getRight() == null) {
            if(parent == null) {
                root = null;
            }
            else if(parent.getLeft() == current) {
                parent.setLeft(null);
            }
            else {
                parent.setRight(null);
            }
        }

        // Has one left child
        else if(current.getLeft() != null && current.getRight() == null) {
            if(parent == null) {
                root = current.getLeft();
            }
            else if(parent.getLeft() == current) {
                parent.setLeft(current.getLeft());
            }
            else {
                parent.setRight(current.getLeft());
            }
        }

        // Has one right child
        else if(current.getLeft() == null && current.getRight() != null) {
            if(parent == null) {
                root = current.getRight();
            }
            else if(parent.getLeft() == current) {
                parent.setLeft(current.getRight());
            }
            else {
                parent.setRight(current.getRight());
            }
        }

        // Has two children
        else {
            // Find the inorder successor (smallest node in right subtree)
            BinaryNode<T> sParent = current;
            BinaryNode<T> successor = current.getRight();

            while(successor.getLeft() != null) {
                sParent = successor;
                successor = successor.getLeft();
            }

            // Replace current's value with successor's value
            current.setInfo(successor.getInfo());

            // Remove the successor from its original position
            if(sParent == current) {
                sParent.setRight(successor.getRight());
            }
            else {
                sParent.setLeft(successor.getRight());
            }
        }
    }

    // Restructures this BST to be optimally balanced
    public void balance()
    {
        if(isEmpty()) {
            return;
        }

        List<T> elements = inorderTraverse();
        root = balanceHelper(elements, 0, elements.size() - 1);
    }

    private BinaryNode<T> balanceHelper(List<T> elements, int low, int high)
    {
        if(low > high) {
            return null;
        }

        int middle = low + (high - low) / 2;
        BinaryNode<T> node = new BinaryNode<>(elements.get(middle));
        node.setLeft(balanceHelper(elements, low, middle - 1));
        node.setRight(balanceHelper(elements, middle + 1, high));
        return node;
    }
}