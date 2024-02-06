import java.io.File;     
import java.io.FileNotFoundException;
import java.util.*;

public class Node extends Hashtable<String, Double[]> implements Comparator<Node>{
	public String node;
	public Double cost;
	public Node(String src, Double s) {
		this.node = src;
		this.cost = s;
	}
	public Node() {
		
	}
	
	public void addinput(String file) throws FileNotFoundException {
		 File f = new File(file);
		 Scanner s = new Scanner(f);
		 while(s.hasNext()) {
		 String line = s.nextLine();
		 String [] arr = line.split("\t");
		 
		 if(arr[0].equals("i") ) {
			 Double[] temp = new Double[2];
			 temp[0] = Double.valueOf(arr[2]);
			 temp[1] = Double.valueOf(arr[3]);
			 this.put(arr[1], temp);
		 }
	}
}
	public void addist(String vertice, Double dist) {
		Double[]temp = this.get(vertice);
		temp[2] = dist;
		this.put(vertice, temp);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Node n = new Node();
		n.addinput("monroe.txt");
		Set<String> set = n.keySet();
		Iterator<String> i = set.iterator();
		for(Iterator<String> k=i; k.hasNext();) {
			System.out.println(k.next());
		}
		System.out.println("Size is " + set.size());
	}
	@Override
	public int compare(Node o1, Node o2) {
		// TODO Auto-generated method stub
		if(o1.cost<o2.cost) {
			return -1;
		}
		if(o1.cost > o2.cost) {
			return 0;
		}
		return 0;
	}


}


