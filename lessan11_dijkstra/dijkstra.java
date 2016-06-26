package lessan11_dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;


class Edge2{
	int v1,v2;
	double w;
	public Edge2(int v1,int v2,double w){
		this.v1=v1;
		this.v2=v2;
		this.w=w;
	}
	@Override
	public String toString() {
		return "Edge [v1=" + v1 + ", v2=" + v2 + ", w=" + w + "]";
	}
}
class Edge implements Comparable<Edge>{
	int to;
	double w;

	public Edge(int to,double w){
		this.to=to;
		this.w=w;
	}

	@Override
	public int compareTo(Edge o) {
		return Double.compare(w, o.w);
	}
}
class Node implements Comparable<Node>{
	int to;
	double chip_price_until_me;

	public Node(int to,double Wpum){
		this.to = to;
		this.chip_price_until_me=Wpum;
	}

	@Override
	public int compareTo(Node o) {
		return Double.compare(chip_price_until_me, o.chip_price_until_me);
	}
}
/////////////////////////////////////////////////////////////////////////////////////////////////
public class dijkstra {

	ArrayList<Edge> [] g ;
	ArrayList<Edge> [] back ;
	double [] dist;//הכי זול עד קודקוד מספר האינקדס
	int start ;

	public dijkstra(Edge2[] g,int start){
		this.start=start;

		//מציאת מספר הקודקודים
		int maxV=0;
		for(Edge2 e:g){
			if(e!=null){
				int temp = Math.max(e.v1, e.v2);
				if(temp>maxV) maxV = temp;}
		}
		maxV +=1;
		//איתחול
		this.g= new ArrayList[g.length];
		this.back= new ArrayList[g.length];
		dist = new double [maxV];

		for(int i=0;i<this.g.length;i++){
			this.g[i] = new ArrayList<Edge>();
			this.back[i] = new ArrayList<Edge>();
		}
		//הכנסת הערכים לגרף
		for(int i=0;i<g.length;i++){

			int from = g[i].v1;
			int to = g[i].v2;
			double w = g[i].w;

			this.g[from].add(new Edge(to, w));
			this.g[to].add(new Edge(from, w));
			this.back[to].add(new Edge(from, w));
		}	
	}

	public dijkstra(ArrayList<Node> [] g,int start){
		this.start=start;
		//איתחול
		dist = new double [g.length];
		this.g = new ArrayList[g.length];
		this.back= new ArrayList[g.length];
		for(int i=0;i<g.length;i++){
			this.g[i] = new ArrayList<Edge>();
			this.back[i] = new ArrayList<Edge>();
		}
		//הכנסה לגרף
		for(int i=0;i<g.length;i++){
			for(Node n : g[i]){
				int from = i;
				int to = n.to;
				double w = n.chip_price_until_me;
				this.g[from].add(new Edge(to, w));
				this.g[to].add(new Edge(from, w));
				this.back[to].add(new Edge(from, w));
			}
		}
	}



	public void algo(){

		for(int i=0;i<dist.length;i++){
			dist[i] = Double.POSITIVE_INFINITY;
		}
		dist[start] = 0;
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(new Node(start, -1));
		//
		while(!pq.isEmpty()){
			Node min= pq.poll();
			for(Edge cur : g[min.to]){
				if(dist[min.to] + cur.w < dist[cur.to] ){
					dist[cur.to] = dist[min.to] + cur.w;
					pq.add(new Node(cur.to, dist[cur.to]));
				}
			}
		}
	}

	public void print(){
		System.out.println(Arrays.toString(dist));
	}

	public String GetPath(int end){
		String ans = "";
		int index=0;
		while(end!=start){
			for(Edge e: g[end]){
				if( Math.abs(dist[e.to] - (dist[end] - e.w) )<0.001 ){
					ans  = (end+1) +"=>" + ans;
					end = e.to;
					break;
				}	
			}
		}
		ans  = (start+1) +"=>" + ans;
		return ans  ;
	}
	public static ArrayList<Node> [] getG(){
		int n=6;
		ArrayList<Node> [] g = new ArrayList[n];
		for(int i=0;i<n;i++){
			g[i] = new ArrayList<Node>();
		}
		g[0].add(new Node(5, 14));g[0].add(new Node(2, 9));g[0].add(new Node(1, 7));
		g[1].add(new Node(3, 15));;g[1].add(new Node(2, 10));
		g[2].add(new Node(5, 2));g[2].add(new Node(3, 11));
		g[3].add(new Node(4, 6));
		g[4].add(new Node(5, 9));

		return g;
	}
	public static Edge2 [] getG1(){
		int n = 9 ;
		Edge2 [] g = new Edge2[n];
		g[0] = new Edge2(0, 5, 14);
		g[1] = new Edge2(5, 4, 9);
		g[2] = new Edge2(4, 3, 6);
		g[3] = new Edge2(3, 1, 15);
		g[4] = new Edge2(1, 0, 7);
		g[5] = new Edge2(2, 0, 9);
		g[6] = new Edge2(2, 5, 2);
		g[7] = new Edge2(2, 3, 11);
		g[8] = new Edge2(2, 1,10);

		return g;
	}
	public static void main(String[] args) {
		dijkstra d = new dijkstra(getG(), 0);
		dijkstra d1 = new dijkstra(getG1(), 0);
		d.algo();
		d.print();
		d1.algo();
		d1.print();
		System.out.println(d.GetPath(4));
		System.out.println(d1.GetPath(4));
	}

}
