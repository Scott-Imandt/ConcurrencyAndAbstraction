import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T>, Runnable {

	private String name;
	private TreeNode<T> root;

	public BinarySearchTree(String name) {
		root = null;
		this.name = name;
	}

	public boolean addAll(List<T> newlist) {
		// newlist.sort((Comparator<? super T>) Comparator.naturalOrder());

		newlist.forEach(x -> this.root = insert(root, x));

		return true;

	}

	private TreeNode<T> insert(TreeNode<T> root, T x) {

		if (root == null) {
			root = new TreeNode<T>(x);
			return root;
		}

		if (x.compareTo(root.data) <= -1) {
			root.lc = insert(root.lc, x);
		}

		else if (x.compareTo(root.data) >= 1) {
			root.rc = insert(root.rc, x);
		}

		return root;

	}

	public List<T> inOrderRec(TreeNode<T> root, List<T> TreeNodeList) {

		if (root != null) {
			inOrderRec(root.lc, TreeNodeList);
			TreeNodeList.add(root.data);
			inOrderRec(root.rc, TreeNodeList);
		}

		;
		return TreeNodeList;
	}

	public TreeNode<T> getRoot() {
		return root;
	}

	public static <T extends Comparable<T>> List<T> merge(List<BinarySearchTree<T>> bstlist) {
		List<Producer<T>> threads = new ArrayList<Producer<T>>();

		Singleton s = Singleton.getInstance();

		bstlist.stream().forEach(x -> threads.add((new Producer<T>(x.inOrderRec(x.root, new ArrayList<T>())))));

		Thread temp = new Thread() {
			@Override
			public void run() {
				Work<T> w = new Work<T>(threads);
			}

		};

		temp.start();

		try {
			temp.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return s.output;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {

		return "[" + name + "] " + root.toString();
	}

	@Override
	public Iterator<T> iterator() {
		List<T> temp = new ArrayList<T>();

		return inOrderRec(root, temp).iterator();

	}

}

class Work<T extends Comparable<T>> {

	List<Producer<T>> p;
	T min;
	Producer<T> minMin;
	boolean check = true;

	Work(List<Producer<T>> p) {
		this.p = p;
		p.forEach(x -> x.start());

		while (check != false) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			check = check();
		}
	}

	public synchronized boolean check() {
		Singleton s = Singleton.getInstance();

		alive();

		p.forEach(x -> {
			if (x != null) {
				min = x.peekData();
				minMin = x;
			}
		});

		if (min == null)
			return false;

		p.forEach(x -> {
			if (x != null && min.compareTo(x.peekData()) >= 0) {
				min = x.peekData();
				minMin = x;
			}
		});

		s.output.add(minMin.consumeData());
		min = null;
		minMin = null;
		return true;
	}

	public void alive() {

		for (int i = 0; i < p.size(); i++) {
			if (p.get(i) != null && p.get(i).peekData() == null)
				p.set(i, null);
		}
	}

}

class Producer<T> extends Thread {

	private List<T> dataList;
	private final List<T> que = new ArrayList<T>();

	private boolean exit = false;

	private static final int MAX_SIZE = 1;

	public Producer(List<T> l) {
		dataList = l;
	}

	@Override
	public void run() {

		try {
			while (exit != true) {
				this.produceData();
			}
		} catch (Exception e) {
		}

	}

	public synchronized void produceData() {
		while (que.size() == MAX_SIZE && exit != true) { // while que is full wait
			try {
				wait();
			} catch (InterruptedException e) {
				return;
			}
		}

		if (0 < dataList.size()) {
			que.add(dataList.get(0));
			// System.out.println("Producer Produced data");
			notify();
		}

		if (0 == dataList.size()) {
			exit = true;
		}
	}

	public synchronized T peekData() {
		int count = 0;
		while (que.size() == 0) {
			try {
				Thread.sleep(10);
				count++;
				if (count == 3)
					return null;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			;
		}
		return que.get(0);
	}

	public synchronized T consumeData() {

		notify();
		while (que.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		dataList.remove(0);
		// System.out.println("Consumer Consumed data");
		return que.remove(0);
	}

}

class Singleton {

	public static Singleton obj = null;
	public static List output = null;

	private Singleton() {

	}

	public static  Singleton getInstance() {

		if (obj == null)
			;

		synchronized (Singleton.class) {

			if (obj == null) {

				obj = new Singleton();
				output = new ArrayList();
			}
		}

		return obj;
	}

}

class TreeNode<T> {
	public T node;
	public TreeNode<T> lc;
	public TreeNode<T> rc;
	public T data;

	public TreeNode(T data) {
		this.data = data;
		lc = null;
		rc = null;
	}

	@Override
	public String toString() {

		String temp = data.toString() + "";

		if (lc != null) {
			temp += " L:(" + lc.toString() + ")";
		}

		if (rc != null) {
			temp += " R:(" + rc.toString() + ")";
		}

		return temp;

	}
}
