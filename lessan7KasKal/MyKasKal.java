package lessan7KasKal;

import java.util.Arrays;

class Edge implements Comparable<Edge>{
	int v1,v2;
	double w;
	public Edge(int v1,int v2,double w){
		this.v1=v1;
		this.v2=v2;
		this.w=w;
	}
	@Override
	public int compareTo(Edge e) {
		if(this.w<e.w)return -1;
		if(this.w>e.w)return 1;
		return 0;
	}
	@Override
	public String toString() {
		return "Edge [v1=" + v1 + ", v2=" + v2 + ", w=" + w + "]";
	}

}

public class MyKasKal {

	Edge [] g; 
	int V;
	Edge [] tree;
	DisjointSet ds ;
	public MyKasKal(Edge [] G){
		this.g=G;
		int maxVal=-999;
		for(Edge e:g){
			if(maxVal<Math.max(e.v1, e.v2))
				maxVal = Math.max(e.v1, e.v2);
		}
		this.V=maxVal;
		V++;
		ds = new DisjointSet(V);
		tree = new Edge[V-1];
		Algo();
	}

	private void Algo() {
		Arrays.sort(g);
		int i=0;
		int x=0;
		while(x<V-1){	
			if(ds.union(g[i].v1, g[i].v2)){
				tree[x++] = new Edge(g[i].v1, g[i].v2, g[i].w);
			}	
			i++;
		}
	}

	public void printTree(){
		int sum=0;
		for(Edge e: tree){
			System.out.println(e.toString());
			sum+=e.w;
		}
		System.out.println("sum: " + sum);
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
		MyKasKal k = new MyKasKal(init2());
		k.printTree();


	}

}


