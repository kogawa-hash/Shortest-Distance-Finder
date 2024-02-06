import javax.swing.*; 
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph extends JPanel {
	Node intersection;
	Edge roads;
	ArrayList<String> adj = new ArrayList<String>();
	
	ArrayList<Double> la;
	ArrayList<Double> lo;
	HashMap<String,int[]> h;

	public ArrayList<String> path = new ArrayList<String>();
	public PriorityQueue<String> pq;
	public ArrayList<String> ints = new ArrayList<String>();
	public Double dist[];
	
	Double ladist;  //laditude distance
	
	Double lasmal; //laditude smallest
	
	Double lodist; //lontitude distance
	
	Double losmal;  //londtitude smallest

	public Set<String> settled;
	
	public Graph() {
		intersection = new Node();
		roads = new Edge();
		la = new ArrayList<Double>();
		lo = new ArrayList<Double>();
		
		ladist = 0.0;
		lasmal = 0.0;
		h = new HashMap<String,int[]>();
		losmal = 0.0;
		lodist = 0.0;
		settled = new HashSet<String>();
		pq = new PriorityQueue<String>();
	}

	public void dijkstra(String source) {//determines the path followed to get from start to finish in the shortest distance
		String u = null;
		
		for(int i = 0; i < dist.length; i++){
			dist[i] = Double.MAX_VALUE;
		}

		pq.add(source);
		
		path.add(source);
		
		System.out.println("path is " + path);
		
		dist[ints.indexOf(source)] = 0.0;

		while(settled.size() != dist.length){
			
			if(pq.isEmpty()){
				return;
			}

			 u = pq.remove();
			 

			if(settled.contains(u)){
				continue;
			}

			settled.add(u);

			adjList(u);
			closestint(u);
		}
	}
	
	private double distance(double lat1, double lon1, double lat2, double lon2) {
		  double theta = lon1 - lon2;
		  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515;
		  return (dist);
	}
	
	private double deg2rad(double deg) {
		  return (deg * Math.PI / 180.0);
	}
	
	private double rad2deg(double rad) {
		  return (rad * 180.0 / Math.PI);
	}

	public void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D) g;
		
		Iterator<String> ite = intersection.keySet().iterator();
		for(Iterator<String> itera = ite; itera.hasNext();) {
			String temp = itera.next();
			Double[] intersect =intersection.get(temp);
			Double lat = intersect[0];  //first lattitude
			lat = (lat-lasmal)/ladist; 
			
			lat = lat*750; 
			
			Double lon = Math.abs(intersect[1]); //absolute value of first longtitude
			lon = (lon-losmal)/lodist;
			
			lon = lon*750;
			
			Double x,y,	deltax, deltay, refy, refx;
			x = (lat-lon-750)/-2;
			y=-x+750;
			deltay = y - lat;    //lattitude is y 
			deltax = lon - x;
			refy = y + deltay;
			refx = x - deltax;
			
			int[] temparray = new int[2];
			temparray[0] = refy.intValue(); 
			temparray[1] = refx.intValue();
			h.put(temp, temparray);
			gg.fillOval(refy.intValue(), refx.intValue(), 2, 2);
			
		}
		
		Iterator<String> Ei = roads.keySet().iterator();
		for (Iterator<String>i = Ei;i.hasNext();) {
			String temp = i.next();
			String first = roads.get(temp)[0];
			String second = roads.get(temp)[1];
			int x_first = h.get(first)[0];
			int y_first = h.get(first)[1];
			int x_second =  h.get(second)[0];
			int y_second = h.get(second)[1];
			gg.drawLine(x_first, y_first, x_second, y_second);
		} 
	}

	public void adjList(String str){
		adj = new ArrayList<String>();
		Iterator<String> i = roads.keySet().iterator();
		for(Iterator<String> iter = i; iter.hasNext();){
			String temp = iter.next();
			if(roads.get(temp)[0].equals(str)){
				adj.add(roads.get(temp)[1]);
			}else if(roads.get(temp)[1].equals(str)){
				adj.add(roads.get(temp)[0]);
			}
		}
	}
	
	public void closestint(String str){
		Double edge, newdist = -1.0;

			for(int i = 0; i<adj.size(); i++){

				String temp = adj.get(i);
				if(!settled.contains(temp)){
					Double latd = intersection.get(temp)[0];
					Double lotd = intersection.get(temp)[1];
					double store = distance(intersection.get(str)[0], intersection.get(str)[1], latd, lotd);
					edge = store;
					
					newdist = dist[ints.indexOf(str)] + edge;

					if(newdist < dist[ints.indexOf(temp)]){
						dist[ints.indexOf(temp)] = newdist;
					}
					pq.add(temp);
					
				}
				
			}
	}

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Graph g = new Graph();
		g.roads.addinput("monroe.txt");
		g.intersection.addinput("monroe.txt");
		Iterator<String> it = g.intersection.keySet().iterator();
		for(Iterator<String> ite = it; ite.hasNext();) {
			String temp = ite.next();
			g.ints.add(temp);
			Double[] doub = g.intersection.get(temp);
			g.la.add(doub[0]);
			g.lo.add(Math.abs(doub[1]));  //convert to absolute value
		}
		g.dist = new Double[g.ints.size()];
		Collections.sort(g.la); //sort la and lo in order
		Collections.sort(g.lo);
		
		g.lasmal = g.la.get(0);    					//scale x axis
		Double bigLA = g.la.get(g.la.size()-1);
		g.ladist = bigLA - g.lasmal;
		
		g.losmal = g.lo.get(0);  					//scale y axis
		Double bigLO = g.lo.get(g.lo.size()-1);
		g.lodist = bigLO - g.losmal;
		
		JFrame frame = new JFrame("Graph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(800,800);
		frame.add(g);
	
		String start = "i15";
		
		System.out.println("Shortest path from node: ");

		g.dijkstra(start); //you can change the source to any. 
		for(int i = 0; i < g.dist.length; i++)
			System.out.println(" Source from " + start +  " to " + g.ints.get(i) + " is: " + g.dist[g.ints.indexOf(g.ints.get(i))] + " miles. ");
	}
	
}

