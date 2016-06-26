package lessan7KasKal;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

//class Edge implements Comparable<Edge>{
//
//	int v1,v2;
//	double w;
//	public Edge(int v1,int v2,double w){
//		this.v1=v1;
//		this.v2=v2;
//		this.w=w;
//	}
//	public Edge(Edge e){
//		this.v1=e.v1;
//		this.v2=e.v2;
//		this.w=e.w;
//	}
//	@Override
//	public int compareTo(Edge e) {
//		if(this.w<e.w)return -1;
//		if(this.w>e.w)return 1;
//		else return 0;
//	}
//	@Override
//	public String toString() {
//		return "Edge [v1=" + v1 + ", v2=" + v2 + ", w=" + w + "]";
//	}
//
//}
/**
 * Kruskal's algorithm for MST
 * Complexity: O(|E|log|V|) - using disjointsets
 */
public class Kaskal {


	Edge [] g ;
	Edge [] tree;
	int v=0;
	Disjoint_Set ds;

	public Kaskal(ArrayList<Integer> [] g){
		int sizeEdge = 0;
		for(int i=0;i<g.length;i++){
			sizeEdge += g[i].size();
		}
		Edge [] arr = new Edge[sizeEdge];
		int x=0;
		for(int i=0;i<g.length;i++){
			for(int j: g[i]){
				if(i<j){
					arr[x++] = new Edge(i, j, j);
				}
			}
		}
		this.g = arr;
		v= g.length;
		ds = new Disjoint_Set(v);
		tree = new Edge[v-1];
		Algo();

	}
	public Kaskal(Edge [] g1){
		this.g = g1;
		for(int i=0;i<g.length;i++){
			if(v<Math.max(g[i].v1, g[i].v2))
				v= Math.max(g[i].v1, g[i].v2);}
		v++;//אם מתחילים מאפס אז פלוס אחד לקודקודים
		ds = new Disjoint_Set(v);
		tree = new Edge[v-1];
		Algo();
	}

	private void Algo() {
		Arrays.sort(g);
		int c = 0,i=0;//אי עובר על גיי וסי עובר על צלעות העץ כאשר מספר הצלעות שווה לעץ נעצור כי  
		while(c<v-1){//לא שווה כי מתחילים את סי מאפס כאשר נגיע למספר הצלעות בעץ נעצור
			if(ds.union(g[i].v1, g[i].v2)){
				tree[c++]=new Edge(g[i]);
			}
			i++;
		}
	}
	public void printTree(){
		for(Edge e: tree){
			System.out.println(e.toString());
		}
	}
	public double sumTree(){
		double ans=0;
		for(Edge e : tree){
			ans = ans + e.w;
		}
		return ans;
	}
	public static Edge[] init1(){
		int n = 10;
		Edge[] graph = new Edge[n];
		graph[0] = new Edge(0, 1, 19);
		graph[1] = new Edge(0, 7, 6);
		graph[2] = new Edge(0, 3, 25);
		graph[3] = new Edge(1, 2, 9);
		graph[4] = new Edge(2, 3, 14);
		graph[5] = new Edge(3, 4, 21);
		graph[6] = new Edge(3, 5, 2);
		graph[7] = new Edge(3, 7, 11);
		graph[8] = new Edge(5, 6, 8);
		graph[9] = new Edge(6, 7, 17);
		return graph;
	}

	public static Edge[] init2(){
		Edge[] graph  = new Edge[14];	
		graph[0] = new Edge(0,1,4);	// 1-st edge
		graph[1] = new Edge(1,2,8);	// 2-st edge
		graph[2] = new Edge(2,3,7);	// 3-st edge
		graph[3] = new Edge(3,4,9);	// 4-st edge
		graph[4] = new Edge(4,5,10);// 5-st edge
		graph[5] = new Edge(5,6,2);// 6-st edge
		graph[6] = new Edge(6,7,1);// 7-st edge
		graph[7] = new Edge(7,0,8);// 8-st edge
		graph[8] = new Edge(1,7,11);// 9-st edge
		graph[9] = new Edge(2,8,2);// 10-st edge
		graph[10] = new Edge(2,5,4);// 11-st edge
		graph[11] = new Edge(3,5,14);// 12-st edge
		graph[12] = new Edge(6,8,6);// 13-st edge
		graph[13] = new Edge(7,8,7);// 14-st edge
		return graph;
	}




	public static void main(String[] args) {
		Kaskal k = new Kaskal(init2());
		k.printTree();
		System.out.println(k.sumTree());

	}

}
