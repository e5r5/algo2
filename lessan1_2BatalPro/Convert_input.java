package lessan1_2BatalPro;

import java.util.ArrayList;

class Edge{
	int v1,v2;
	double w;
	public Edge(int v1,int v2,double w){
		this.v1=v1;
		this.v2=v2;
		this.w=w;
	}
	@Override
	public String toString() {
		return "Edge [v1=" + v1 + ", v2=" + v2 + ", w=" + w + "]";
	}

}

public class Convert_input {

	//משנה קלט 
	public static Edge[] Array_to_EdgeArr (ArrayList<Integer> [] g){
		int sum=0;
		for(int i=0;i<g.length;i++){
			sum=sum+g[i].size();
		}

		int n = sum/2;
		Edge [] ans = new Edge [n] ;
		int x=0;
		for(int i=0;i<g.length;i++){
			for(int e: g[i]){
				if(i<e){
					ans[x++]=new Edge(i, e, 0);
				}
			}
		}
		return ans;
	} 
	public static ArrayList<Integer> [] EdgeArr_to_ArrayList(Edge[] g){
		int maxV=0;
		for(Edge e:g){
			int temp = Math.max(e.v1, e.v2);
			if(temp>maxV) maxV = temp;
		}
		maxV +=1;//כי מתחילים את הספירה מאפס
		ArrayList<Integer> [] ans = new ArrayList[maxV];
		for(int i=0;i<ans.length;i++){
			ans[i] = new ArrayList<Integer>();
		}
		for(Edge e: g){
			ans[e.v1].add(e.v2);
			ans[e.v2].add(e.v1);
		}
		return ans;
	}

	public static ArrayList<Integer> [] init1(){
		int n=9;
		ArrayList<Integer> g [] = new ArrayList[n];
		for(int i=0;i<n;i++){
			g[i] = new ArrayList<Integer>();
		}
		g[0].add(1);g[0].add(2);
		g[1].add(0);g[1].add(2);g[1].add(6);g[1].add(7);
		g[2].add(0);g[2].add(1);g[2].add(3);g[2].add(4);
		g[3].add(2);g[3].add(4);
		g[4].add(2);g[4].add(3);g[4].add(5);g[4].add(7);
		g[5].add(4);g[5].add(6);g[5].add(7);g[5].add(8);
		g[6].add(1);g[6].add(5);g[6].add(7);g[6].add(8);
		g[7].add(1);g[7].add(4);g[7].add(5);g[7].add(6);
		g[8].add(5);g[8].add(6);
		return g;
	}
	public static void main(String[] args) {

	}

}
