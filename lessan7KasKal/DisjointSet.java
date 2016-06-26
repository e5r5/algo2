package lessan7KasKal;

public class DisjointSet {

	int [] rank;
	int [] pernt;

	public DisjointSet(int V){
		this.rank =new int [V];
		this.pernt = new int [V];
		for(int i=0;i<V;i++){
			rank[i] = 0;
			pernt[i] = i;
		}
	}

	public boolean union(int x,int y){
		int rootX = find(x);
		int rootY = find(y);
		if(rootX==rootY)
			return false;
		if(rank[rootX] > rank[rootY]){
			pernt[rootY]=rootX;	
		}
		else if(rank[rootX] < rank[rootY]){
			pernt[rootX]=rootY;	
		}
		else{
			pernt[rootX]=rootY;
			rank[rootX]++;
		}
		return true;
	}

	private int find(int x) {
		int p = pernt[x];
		if(p==x)
			return x;
		else
			return pernt[x] = find(p);
	}
}
