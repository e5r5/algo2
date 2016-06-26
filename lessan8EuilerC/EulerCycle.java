package lessan8EuilerC;

import java.util.ArrayList;
import java.util.Stack;

public class EulerCycle { //(O(n))!!
	ArrayList<Integer> [] Gtemp;
	ArrayList<Integer> [] g;
	String ans = "";
	int [] deg;
	int n;
	Stack<Integer> st;
	boolean HasEulerCycle;

	public EulerCycle(ArrayList<Integer> [] G1) {
		this.g = G1;
		this.n = G1.length;
		deg = new int [n];
		st = new Stack<Integer>();
		Gtemp = new ArrayList[n];
		//בגרף קשיר אם ורק אם כל הדרגות זוגיות הוא מעגל אויילר
		
		for(int i=0;i<deg.length;i++){
			deg[i] = g[i].size();
			if(deg[i]%2!=0)System.out.println("no Euler Cycle!!");;
			Gtemp[i] = new ArrayList<Integer>();
			for(int j : g[i]){//deep copy to grath
				Gtemp[i].add(j);
			}

		}
		Algo();
	}

	private void Algo() {
		int start = 0;
		st.push(start);
		while(!st.isEmpty()){
			int v = st.peek();
			if(deg[v]==0){//אם הדרגה של ווי אפס אז סיימנו עם הקודקוד הזה לא נעבור עליו יותר כי אין יציאה ממנו
				ans = ans + "=>" + v;
				st.pop();
			}
			else{
				int u = Gtemp[v].get(0);//לוקחים את השכן של ווי שהוא יו
				st.add(u);//מוסיפים את יו למחסנית
				deg[v]--;//מורדים את הדרגות לשניהם
				deg[u]--;
				Gtemp[u].remove((Integer)v);//מוחקים מהגרף הזמני
				Gtemp[v].remove((Integer)u);
				v=u;//ממשיכים מיו את התהליך והריצה על הגרף
			}
		}
	}
	
	public static void EulerCycle2(ArrayList<Integer> [] g){
		//לבדוק שהגרף קשיר
		int n = g.length;
		int [] deg = new int [n];
		Stack<Integer> st = new Stack<Integer>();
		ArrayList<Integer> [] Gtemp = new ArrayList[n];
		//בגרף קשיר אם ורק אם כל הדרגות זוגיות הוא מעגל אויילר
		String ans = "";
		for(int i=0;i<deg.length;i++){
			deg[i] = g[i].size();
			if(deg[i]%2!=0)System.out.println("no Euler Cycle!!");;
			Gtemp[i] = new ArrayList<Integer>();
			for(int j : g[i]){//deep copy to grath
				Gtemp[i].add(j);
			}
		}
		int start = 0;
		st.push(start);
		while(!st.isEmpty()){
			int v = st.peek();
			if(deg[v]==0){//אם הדרגה של ווי אפס אז סיימנו עם הקודקוד הזה לא נעבור עליו יותר כי אין יציאה ממנו
				ans = ans + "=>" + v;
				st.pop();
			}
			else{
				int u = Gtemp[v].get(0);//לוקחים את השכן של ווי שהוא יו
				st.add(u);//מוסיפים את יו למחסנית
				deg[v]--;//מורדים את הדרגות לשניהם
				deg[u]--;
				Gtemp[u].remove((Integer)v);//מוחקים מהגרף הזמני
				Gtemp[v].remove((Integer)u);
				v=u;//ממשיכים מיו את התהליך והריצה על הגרף
			}
		}
		System.out.println(ans);
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
		EulerCycle e = new EulerCycle(init1());
		System.out.println(e.ans);
		EulerCycle2(init1());
	}

}
