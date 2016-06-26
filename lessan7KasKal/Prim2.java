package lessan7KasKal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
/**
 * 
 * @author  Sefi Erlih
 *
 */

public class Prim2 {
	int start;
	ArrayList<Node>[] t;
	ArrayList<edge> mst;
	int[] father;
	boolean[] passed;
	int[] key;
	int  Mstsum;
	
	public Prim2(ArrayList<Node>[] g,int start){
		Mstsum=0;
		t=g.clone();
		this.start=start;
		passed=new boolean[g.length];
		father=new int[g.length];
		key=new int[g.length];
		mst=new ArrayList<edge>();
		Clean();
	}
	
	public void Clean(){
		for (int i = 0; i < father.length; i++) {
			father[i]=-1;
			key[i]=Integer.MAX_VALUE;
			passed[i]=false;
		}
	}
	
	public void Mst(){
		PriorityQueue<Node> q=new PriorityQueue<Node>();
		key[start]=0;
		q.add(new Node(start,0));
		int nedge=0;
		while (!q.isEmpty() && nedge< t.length){
			Node p=q.remove();
			int f=p.vertex;
			for ( Node n:t[f]){
				int son=n.vertex;
				int w=n.whigt;
				if (!passed[son])
					if (w<key[son]){
						key[son]=w;
						father[son]=f;
						q.remove(n);
						q.add(n);
					}
			}
			
			
			passed[f]=true;
			if (f!=start){
			mst.add(new edge(f,father[f],key[f]));
			Mstsum+=key[f];
			}
			nedge++;
		}
	}
	
	
	public static ArrayList<Node>[] init1(){
		int numOfVertexes = 9;
		ArrayList<Node>[] graph = new ArrayList[numOfVertexes];
		for (int i=0; i<numOfVertexes; i++) {
			graph[i] = new ArrayList<Node>();
		}
		// vertex: (adjacency vertex, key) 
		// a - first vertex:
		graph[0].add(new Node(1,4)); graph[0].add(new Node(7,8)); 
		// b - second vertex:
		graph[1].add(new Node(0,4)); graph[1].add(new Node(2,8)); graph[1].add(new Node(7,11)); 
		// c - third vertex:
		graph[2].add(new Node(1,8)); graph[2].add(new Node(3,7)); graph[2].add(new Node(5,4)); graph[2].add(new Node(8,2)); 
		// d - 4-th vertex:
		graph[3].add(new Node(2,7)); graph[3].add(new Node(4,9)); graph[3].add(new Node(5,14)); 
		// e - 5-th vertex:
		graph[4].add(new Node(3,9)); graph[4].add(new Node(5,10)); 
		// f - 6-th vertex:
		graph[5].add(new Node(2,4)); graph[5].add(new Node(3,14)); graph[5].add(new Node(4,10)); graph[5].add(new Node(6,2)); 
		// f - 7-th vertex:
		graph[6].add(new Node(5,2)); graph[6].add(new Node(7,1)); graph[6].add(new Node(8,6)); 
		// f - 8-th vertex:
		graph[7].add(new Node(0,8)); graph[7].add(new Node(1,11)); graph[7].add(new Node(6,1)); graph[7].add(new Node(8,7)); 
		// f - 9-th vertex:
		graph[8].add(new Node(2,2)); graph[8].add(new Node(6,6)); graph[8].add(new Node(7,7)); 
		return graph;
	}
	
	
	public static ArrayList<Node>[] init2(){
		int numOfVertexes = 3;
		ArrayList<Node>[]graph = new ArrayList[numOfVertexes];
		for (int i=0; i<numOfVertexes; i++) {
			graph[i] = new ArrayList<Node>();
		}
		// vertex: (adjacency vertex, key) 
		// a - first vertex:
		graph[0].add(new Node(1,3)); graph[0].add(new Node(2,4)); 
		// b - second vertex:
		graph[1].add(new Node(0,3)); graph[1].add(new Node(2,4)); 
		// c - third vertex:
		graph[2].add(new Node(0,4)); graph[2].add(new Node(1,4)); 
		return graph;
	}
	
	public void Print(){
		for (int i = 0; i < mst.size(); i++) {
			System.out.println(mst.get(i));
		}
		System.out.println("father: " +Arrays.toString(father));
		System.out.println("key: " +Arrays.toString(key));
		System.out.println("sum: "+Mstsum);
	}
	public static ArrayList<Node>[] init3(){
		int numOfVertexes = 4;
		ArrayList<Node>[] graph = new ArrayList[numOfVertexes];
		for (int i=0; i<numOfVertexes; i++) {
			graph[i] = new ArrayList<Node>();
		}
		graph[0].add(new Node(1,5)); graph[0].add(new Node(2,4)); graph[0].add(new Node(3,2));
		graph[1].add(new Node(0,5)); graph[1].add(new Node(3,1));
		graph[2].add(new Node(0,4));
		graph[3].add(new Node(0,2));  graph[3].add(new Node(1,1)); 
		// vertex: (adjacency vertex, key) 
		return graph;
	}
	public static void main(String[] args) {
		Prim2 r=new Prim2(init1(),0);
		r.Mst();
		r.Print();
	}
	
}
