package lessan9Huffman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class Huffman {

	char [] chars;
	int [] times;
	String [] codes;
	int n;
	String s ;
	int [][] mat;
	ArrayBlockingQueue<Integer> q1 ;//מחזיק את הקודקודים המקוריים של העץ
	ArrayBlockingQueue<Integer> q2 ;//מחזיק את הקודקודים  החדשים שיצרנו מהסכימה של כל שני בנים

	public Huffman(String s){
		this.s=s;
		chars = getCharsFromString(s);
		times = getTimesFromString(s);
		codes  = new String [n];
		mat = new int [2*n-1][4]; //0=sum L+R ; 1=Left ; 2 = Right ; 3=pre
		q1 = new ArrayBlockingQueue<Integer>(n);
		q2 = new ArrayBlockingQueue<Integer>(n);
		AlgoBuildTree();
		MakeCode();
	}
	public Huffman(int [] time , char[] let){
		this.n= time.length;
		this.times = time;
		this.chars = let;
		FixArr_sort();//רק אם המערך לא ממויין
		codes  = new String [n];
		mat = new int [2*n-1][4]; //0=sum L+R,newV,times ; 1=Left ; 2 = Right ; 3=pre
		q1 = new ArrayBlockingQueue<Integer>(n);
		q2 = new ArrayBlockingQueue<Integer>(n);
		AlgoBuildTree();
		MakeCode();
	}
	//אם המערך לא ממויין נמיין בצורה חד חד ערכית לאותיות
	public void FixArr_sort(){
		node [] arr = new node[n];
		for(int i=0;i<n;i++){
			arr[i] = new node(times[i], chars[i]);
		}
		Arrays.sort(arr);
		int [] temp1 = new int [n];
		char [] temp2 = new char[n];
 		for(int i=0;i<n;i++){
			temp1[i] = arr[i].nember;
			temp2[i] = arr[i].let;
		}
 		times = temp1;
 		chars = temp2;
	}
	private void PrintResult() {
		System.out.print("times:  " + Arrays.toString(times));
		System.out.print("   chars :   "+Arrays.toString(chars));
		System.out.println("code: " + Arrays.toString(codes));
	}

	public void printMatrix(){
		for(int i=0;i<mat.length;i++){
			System.out.println();
			for(int j=0;j<mat[0].length;j++){
				System.out.print(mat[i][j] +"  ");
			}
		}
	}
	private void AlgoBuildTree() {

		for(int i=0;i<n;i++){
			mat[i][0]=times[i];//איתחול עמודה ראשונה של התדירות
			q1.add(i);//הוספת האינדקסים לתור
			mat[i][1]=-1;
			mat[i][2]=-1;
		}

		//המקרה הראשון 
		int k=n;//החל מאן
		int left = q1.poll();//מוצאים מהתור אינקסים של הקודקודים עם התדירות הנמוכה 
		int right = q1.poll();
		mat[k][0]=mat[left][0]+mat[right][0];//התדירות של הקודקוד האבא של השתיים הראשונים הוא סכום התדירות שלהם 
		mat[k][1]=left;//מכירים לאבא=הקודקוד החדש שנוצר= את הבנים שלו
		mat[k][2]=right;
		mat[left][3]=k;//מכירים לבנים את האבא החדש שלהם
		mat[right][3]=k;
		q2.add(k);//את הקודקוד האבא החדש מכניסים לתור שתיים שהוא תור הקודקודים החדשים=מורכב מקודקודים שסכמנו
		k++;//בונים קודקוד החדש לפעם הבאה

		//שני התורים לא ריקים =אפשר להתחיל באלגוריתם לבניית העץ
		while(q1.size()+q2.size()>1){//מסיימים שבאחד מהתורים יש קודקוד אחד או זה או זה
			left = getMin();//מוצאים מהתור אינקסים של הקודקודים עם התדירות הנמוכה 
			right = getMin();
			mat[k][0]=mat[left][0]+mat[right][0];//התדירות של הקודקוד האבא של השתיים הראשונים הוא סכום התדירות שלהם 
			mat[k][1]=left;//מכירים לאבא=הקודקוד החדש שנוצר= את הבנים שלו
			mat[k][2]=right;
			mat[left][3]=k;//מכירים לבנים את האבא החדש שלהם
			mat[right][3]=k;
			q2.add(k);//את הקודקוד האבא החדש מכניסים לתור שתיים שהוא תור הקודקודים החדשים=מורכב מקודקודים שסכמנו
			k++;//
		}
		mat[2*n-1-1][3]=-1;//אין אבא לשורש
	}

	private int getMin() {//בודקים ב2 התורים ומוצאים את ה2 המינימלים=שני הקודקודים המינימלים יהיו הבנים
		if(q1.size()==0) return q2.poll(); //אם הראשון ריק אז קיים אחרון בשני=נוציא אותו
		if(q2.size()==0) return q1.poll();
		if(mat[q1.peek()][0]<mat[q2.peek()][0])//בודקים את הערכים ומחזירים את האינקס המינימלי
			return q1.poll();
		else 
			return q2.poll();
	}
	private void MakeCode() {
		for(int i=0;i<n;i++){//לכל אות מוצאים את הקוד הבינארי
			String ans = "";
			int v=i;//מתחילים מהאינקס של האות והולכים לאבא כל פעם= רואים מאיפה הבאנו עד שנגיע לאינקס האחרון שהוא השורש
			int p;
			while(v!=2*n-1-1 ){//עד שנגיע לשורש באינקס שתי אן פחות אחד אבל מתחילים אפס אז שוב פחות אחד
				p=mat[v][3];
				if(mat[p][1]==v)
					ans = "0" + ans;
				else if (mat[p][2]==v)
					ans = "1" + ans;
				v=p;
			}
			codes[i]=ans;
		}
	}


	private int[] getTimesFromString(String s) {
		int [] latar = new int [26];	
		int [] ans = new int [n];
		for(int i=0;i<s.length();i++){
			latar[s.charAt(i)-'a']++;
		}
		int x=0;
		for(int i=0;i<latar.length;i++){
			if(latar[i]!=0){
				ans[x]=latar[chars[x]-'a'];
				x++;}
		}
		return ans;
	}

	private char[] getCharsFromString(String s) {
		ArrayList<Character> temp = new ArrayList<Character>();
		for(int i=0;i<s.length();i++){
			if(!temp.contains(s.charAt(i))){
				temp.add(s.charAt(i));
			}
		}
		n=temp.size();
		char [] ans = new char[n];
		for(int i=0;i<ans.length;i++)
			ans[i]=temp.get(i);
		return ans;
	}

	class node implements Comparable<node>{
		int nember;
		char let;
		public node(int ne, char c){
			this.nember = ne;
			this.let = c;
		}
		@Override
		public int compareTo(node x) {
			if(this.nember<x.nember)return -1;
			if(this.nember>x.nember)return 1;
			else return 0;
		}
	}

	public static void main(String[] args) {
		int [] time = {5,9,12,13,16,45};
		char[] let = {'f','e','c','b','d','a'};
		Huffman h = new Huffman(time,let);
		h.PrintResult();
		int [] time2 = {13,9,12,45,16,5};
		char[] let2 = {'b','e','c','a','d','f'};
		Huffman h2 = new Huffman(time2,let2);
		h2.PrintResult();
	}

}
