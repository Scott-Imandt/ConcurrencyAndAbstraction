import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T>{
	
	private String name;
	private TreeNode<T> root;
	
	public BinarySearchTree(String name){
		root = null;
		this.name = name;
	}
	
	public boolean addAll(List<T> newlist) {
		//newlist.sort((Comparator<? super T>) Comparator.naturalOrder());
		
		newlist.forEach(x -> this.root = insert(root, x));
		
		return true;
	
	}
	
	private TreeNode<T> insert(TreeNode<T> root , T x) {
		
		if(root == null) {
			root = new TreeNode<T>(x);
			return root;
		}
		
		if(x.compareTo(root.data) <= -1){
			root.lc = insert(root.lc, x);	
		}
		
		else if(x.compareTo(root.data) >= 1) {
			root.rc = insert(root.rc, x);
		}
		
		return root;
	
	}
	
	public List<T> inOrderRec(TreeNode<T> root, List<T> TreeNodeList) {
		
		
		if (root != null) {
            inOrderRec(root.lc,TreeNodeList);
            TreeNodeList.add(root.data);
            inOrderRec(root.rc, TreeNodeList);
        }
		
		;
		return TreeNodeList;
	}
	
	
	
	
	@Override
	public String toString() {
		
		return "["+name+"] " + root.toString();
	}
	

	@Override
	public Iterator<T> iterator() {
		List<T> temp = new ArrayList<T>(); 
		
		 return inOrderRec(root, temp).iterator();
		
	}
}

class TreeNode<T>{
	public T node;
	public TreeNode<T> lc;
	public TreeNode<T> rc;
	public  T data;
	
	public TreeNode(T data) {
		this.data = data;
		lc = null;
		rc = null;
	}
	@Override
	public String toString() {
		
		String temp = data.toString()+"";
		
		if(lc != null) {
			temp += " L:(" + lc.toString() +")";
		}
		
		if(rc !=null) {
			temp += " R:(" + rc.toString() + ")";
		}
		
		return temp;

	}
}
