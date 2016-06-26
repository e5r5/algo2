package lessan7KasKal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;


public class Prim {
	class Node {
		int id,weight;
		public Node(int id, int weight) {
			this.id = id;
			this.weight = weight;
		}
	}
	class Edge {
		int v1,v2,weight;
		public Edge(int v1,int v2, int weight) {
			this.v1 = v1;
			this.v2 = v2;
			this.weight = weight;
		}
	}
	private Edge [] tree;
	private ArrayList<Node> graph[];
	private int[] parent,color,weight;
	private static final int inf = Integer.MAX_VALUE;

	public Prim(ArrayList<Node> g[]) {
		graph = g;
		int n = graph.length;
		tree = new Edge[n-1]; //creating MST
		Arrays.fill(weight, inf);
		Arrays.fill(parent, -1);
		findMST(0);
		makeMST();
	}

	private void makeMST() {
		int size = 0;
		for (int i = 0; i < graph.length; i++) {
			if(parent[i]!=-1){
				tree[size++] = new Edge(i,parent[i], weight[i]);
			}
		}

	}

	private Edge[] getTree(){
		return tree;
	}

	private void findMST(int source) {
		PriorityQueue<Node> q = new PriorityQueue<>();
		q.add(new Node(source,0));
		color[source] = 1;
		weight[source] = 0;
		while(!q.isEmpty()){
			Node u = q.poll();
			for (Node n : graph[u.id]) {
				if(color[n.id] == 0 ){
					color[n.id] = 1;
					parent[n.id] = u.id;
					weight[n.id] = u.weight;
					q.add(new Node(u.id,u.weight));
				}
				else if(color[n.id] == 1){
					if(weight[n.id]>u.weight){
						parent[n.id] = u.id;
						weight[n.id] = u.weight;
					}
				}
			}
			color[u.id] = 2;
		}
	}
}
