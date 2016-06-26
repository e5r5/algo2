package lessan5Fire;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Fire {


	public static void fireAlgo(ArrayList<Integer> [] tree){
		int n = tree.length;
		int radius=1;
		int [] deg = new int [n];
		ArrayList<Integer> leaves = new ArrayList<Integer>();
		//איתחול 
		for(int i=0;i<n;i++){
			deg[i] = tree[i].size();//מחייל את מערך הדרגות של כל קודקוד לפי האינקס
			if(deg[i]==1)//אם הוא עלה תוסיף אותו
				leaves.add(i);
		}
		//תחילת האלגוריתם
		while(n>2){
			ArrayList<Integer> temp_leaves = new ArrayList<Integer>();
			for(int lev : leaves){
				deg[lev]=0;//שורף פעם אחת 
				for(int update_neubard : tree[lev]){//מעדכן את הדרגות של השכנים
					if(deg[update_neubard] > 0){
						deg[update_neubard] --;
						if(deg[update_neubard] == 1){//אם אחרי העידכון והורדת הדרגה הוא עלה נכניס אותו למערךרשימה השני
							temp_leaves.add(update_neubard);
						}
					}
				}
				n--;//נוריד פה את אן כי לא לכל קודקוד נשרוף רק לעלים רוב 	
			}
			radius++;//הרדיוס גודל כי שרפנו ולא הגענו למרכז
			leaves = temp_leaves; //עידכון מערך העלים 
		}
		System.out.println((leaves));
		//סוף האלגוריתם
		if(n==2){ //אם יש 2 מרכזים
			System.out.println("c1 is: " + leaves.get(0));
			System.out.println("c2 is: " + leaves.get(1));
			System.out.println("radius is: " + radius);
			System.out.println("diameter: " + (2*radius -1));
		}
		else{ //קיים מרכז אחד
			System.out.println("c1 is: " + leaves.get(0));
			System.out.println("radius is: " + radius);
			System.out.println("diameter: " + (2*radius));
		}
	}
	public static ArrayList<Integer>[] init(){
		int numOfVertexes = 7;
		ArrayList<Integer>[] tree = new ArrayList[numOfVertexes];
		for (int i=0; i<numOfVertexes; i++)
		{
			tree[i] = new ArrayList<Integer>();
		}
		tree[0].add(1);//1->2
		////////////
		tree[1].add(0);//2->1
		tree[1].add(2);//2->3
		tree[1].add(4);//2->5
		///////////
		tree[2].add(1);//3->2
		tree[2].add(3);//3->4
		/////////////
		tree[3].add(2);//4->3
		////////////
		tree[4].add(1);//5->2
		tree[4].add(5);//5->6
		////////////
		tree[5].add(4);//6->5
		tree[5].add(6);//6->7
		////////////
		tree[6].add(5);//7->6
		return tree;
	}

	public static void main(String[] args) {

		fireAlgo(init());
	}

}
