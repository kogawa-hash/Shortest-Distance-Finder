import java.util.*;    
import java.io.*;

public class Edge extends Hashtable<String, String[]>{
	
    public int cost;
    
    public Edge(int cost)
    {
        this.cost = cost;
    }
    
    public Edge()
    {
       
    }
  
	 public void addinput(String file) throws FileNotFoundException {
		 File f = new File(file);
		 Scanner s = new Scanner(f);
		 while(s.hasNext()) {
			 String line = s.nextLine();
			 String [] arr = line.split("\t");
			 
			 if(arr[0].equals("r") ) {
				 String[] temp = new String[2];
				 temp[0] = arr[2];
				 temp[1] = arr[3];
				 this.put(arr[1], temp);
			 
			 }
		 }
	 }

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Edge e = new Edge();
		e.addinput("monroe.txt");
		Set<String> set = e.keySet();
		Iterator<String> i = set.iterator();
		for(Iterator<String> k=i; k.hasNext();) {
			System.out.println(k.next());
		}
		System.out.println(set.size());
	}

}
