package lessan6BFS;

import java.util.ArrayList;

public class TestBFS {

	/**
	 * @param args
	 */
	

	public static void main(String[] args) {
		BFS b = new BFS(InitGraph.initGraph1());
		System.out.println(b.getPath(0, 2));
		System.out.println("is bipartite? " + b.isBipartite());
		System.out.println("is connected? " + b.isConnected());
		
		System.out.println(b.getAllComponents());
	}

}
