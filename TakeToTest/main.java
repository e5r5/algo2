package TakeToTest;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

class Edge implements Comparable<Edge>{

	int v1,v2;
	double w;
	public Edge(int v1,int v2,double w){
		this.v1=v1;
		this.v2=v2;
		this.w=w;
	}
	public Edge(Edge e){
		this.v1=e.v1;
		this.v2=e.v2;
		this.w=e.w;
	}
	@Override
	public int compareTo(Edge e) {
		if(this.w<e.w)return -1;
		if(this.w>e.w)return 1;
		else return 0;
	}
	@Override
	public String toString() {
		return "Edge [v1=" + v1 + ", v2=" + v2 + ", w=" + w + "]";
	}

}
public class main {
	/////////////////////////////////////////////////////////////matrix...............................
	class Bottle {
		int n,m,size;
		boolean [][] h;
		String [][] path;
		public Bottle(int n1,int m1){
			this.n = n1;
			this.m = m1;
			size = (n+1)* (m+1);
			h = new boolean [size][size];
			path = new String[size][size];
			buildMatrix();
		}

		private void buildMatrix() {
			for(int i=0;i<n+1;i++){
				for(int j=0;j<m+1;j++){
					int index = index(i,j);
					//איזה צעדים אפשריים ממצב מסויים של i,j?
					h[index][index(i,0)] = true;//למלא קצת את השמאלי ולרוקן את הימיני
					h[index][index(0,j)] = true;//מלא קצת את הימיני ולרוקן את השמאלי
					h[index][index(n,j)] = true;//למלא לגמרי את השמאלי ויש משהו בימיני
					h[index][index(i,m)] = true;//למלא לגמרי  את הימיני ויש משהו בשמאלי
					h[index][index(Math.max(0,i+j-m),Math.min(m, i+j))] = true;//אסור שיהיה מינוס מים ואסור שיהיה יותר מהקיבולת
					h[index][index(Math.min(i+j, n),Math.max(0,i+j-n))] = true;//סמטרי לשפוך מאם ןלאן 
					path[i][j] = "";
				}
			}
		}
		private void buildPath() {
			for(int i=0;i<n+1;i++){
				for(int j=0;j<m+1;j++){
					int index = index(i,j);
					//איזה צעדים אפשריים ממצב מסויים של i,j?
					path[index][index(i,0)] = "("+i+","+j+")->"+"("+i+","+0+");";//למלא את השמאלי ולרוקן את הימיני
					path[index][index(0,j)] ="("+i+","+j+")->"+"("+0+","+j+");";;//מלא את הימיני ולרוקן את השמאלי
					path[index][index(n,j)] = "("+i+","+j+")->"+"("+n+","+j+");";//למלא את השמאלי ויש משהו בימיני
					path[index][index(i,m)] = "("+i+","+j+")->"+"("+i+","+m+");";;//למלא את השמאלי ויש משהו בימיני
					int a =index(Math.max(0,i+j-m),Math.min(m, i+j));
					path[index][a] = "("+i+","+j+")->"+"("+iIndex(a)+","+jIndex(a)+");";//אסור שיהיה מינוס מים ואסור שיהיה יותר מהקיבולת
					a = index(Math.min(i+j, n),Math.max(0,i+j-n));
					path[index][a] = "("+i+","+j+")->"+"("+iIndex(a)+","+jIndex(a)+");";
				}
			}
		}
		// return liter in the big bottle
		private int iIndex(int index){	
			return index/(m+1);
		}
		// return liter in the small bottle
		private int jIndex(int index){	
			return index%(m+1);
		}

		//הופך מיקום במטריצה למיקום במערך חד מימדי שהמיקום במערך חד מימדי זה כל האפשרויות של מילוי הבקבוקים
		public int index(int i,int j){	
			return (m+1)*i+j; //מספר העמודות פלוס אחד כפול המיקום המיקום של השורות פלוס עמודות
			//דומה ל i*m+j
		}
		public boolean get_on_this_pasation(int v){
			if(h[0][index(0,v)]||h[0][index(v, 0)])
				return true;
			else 
				return false;
		}
		public boolean get_on_this_pasation(int x,int y){
			if(h[0][index(x,y)]||h[0][index(y, x)])
				return true;
			else
				return false;
		}
		public void printMatrix(){
			for(int i=0;i<h.length;i++){
				System.out.println();
				for(int j=0;j<h[0].length;j++){
					System.out.print(h[i][j] + "  ");
				}
			}
			System.out.println();
		}
	}
	/////////////
	//מציאת המסלול המינימלי שהמשקל על הקודקודים ולא צלעות
	public static double [][] distFromVtoE(boolean [][] mat,double [][] matPrice,double [] price){
		//השמה לצלעות את המחירים
		for(int i=0;i<mat.length;i++){
			for(int j=0;j<mat[0].length;j++){
				if(i==j){ 
					matPrice[i][j] = 0;
					continue;}
				if(mat[i][j])
					matPrice[i][j] = price[i] + price[j];
				else
					matPrice[i][j]  = Double.POSITIVE_INFINITY;
			}
		}
		//	printMat(matPrice);
		//הפעלת האלגו
		fwPrice(matPrice);
		//	printMat(matPrice);
		//תיקון הסטיה כי חלק מהקודקודים נספרו פעמיים
		for(int i=0;i<mat.length;i++){
			for(int j=0;j<mat[0].length;j++){
				if(i==j) 
					matPrice[i][j]=0;
				else 
					matPrice[i][j] = (price[i] + price[j] + matPrice[i][j]) / 2 ;
			}
		}
		return matPrice;
	}
	//מחירים על הצלעות מעדכן את המטריצה למחירים המינימלים בהנחה שעל האלכסון יש אפסים
	public static void fwPrice(double [][] mat){
		for(int i=0;i<mat.length;i++)
			mat[i][i] = 0;
		//
		int n = mat.length;
		for(int k=0;k<n;k++){
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					mat[i][j] = Math.min((mat[i][j]) , mat[i][k]+mat[k][j]);
				}
			}
		}
	}
	public static int HowManyComponents(boolean matAfterFW[][]) {
		//	fw(matAfterFW);
		//	if(isCashir(matAfterFW))return 1;
		int count = 0;
		int dim = matAfterFW.length;
		int comps[] = new int[dim];
		for (int i=0; i<dim; i++) {
			if (comps[i]==0) count++;
			for (int j=i; j<dim; j++) {
				if (comps[j]==0 && matAfterFW[i][j]) comps[j] = count;
			}
		}
		String cs[] = new String[count];
		for (int i=0; i<count; i++) {
			cs[i] = "";
		}
		for (int i=0; i<dim; i++) 
			cs[comps[i]-1] = cs[comps[i]-1]+ (i+1) +"\t";
		for (int i=0; i<count; i++) 
			System.out.println(cs[i].toString());
		return count;
	}
	/////////////////
	//////////////////////////////////////משנה קלט /////////////////////////////////////////////////////////////
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
	//////////////////////////////////////משנה קלט /////////////////////////////////////////////////////////////
	//Best
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean Is_all_neg(int [] arr){//O(n)
		for(int i=0;i<arr.length;i++){
			if(arr[i]>0)
				return false;
		}
		return true;
	}
	public static int Return_max(int [] arr){//O(n)
		int max = Integer.MIN_VALUE;
		for(int i=0;i<arr.length;i++){
			if(arr[i]>max)
				max = arr[i];
		}
		return max;
	}
	public static boolean Is_only_one_pos(int [] arr){//O(n)
		int timePos =0;
		for(int i=0;i<arr.length;i++){
			if(arr[i]>0)
				timePos++;
		}
		if(timePos==1)
			return true;
		else 
			return false;
	}

	public static int Return_only_one_pos(int [] arr){//O(n)
		for(int i=0;i<arr.length;i++){
			if(arr[i]>0)
				return arr[i];
		}
		return Integer.MAX_VALUE;
	}

	//תכנות דינמי
	public static int Best_dinami(int [] arr){//O(n^2)
		int [][] mat = new int [arr.length][arr.length];
		mat[0][0] = arr[0];
		for(int i=1;i<arr.length;i++){
			mat[i][i] = arr[i];
		//	mat[0][i] = mat[0][i-1] + arr[i];
		}
		for(int i=0;i<arr.length;i++){
			for(int j=i+1;j<arr.length;j++){
				mat[i][j] =mat[i][j-1] + arr[j]; 
			}
		}
		int max = Integer.MIN_VALUE,x=0,y=0;
		for(int i=0;i<mat.length;i++){
			for(int j=i;j<mat[0].length;j++){
				if(mat[i][j]> max){
					max = mat[i][j];
					x = i;
					y=j;
				}
			}
		}

		System.out.println(x + "---" + y);
		return max;
	}


	public static int Best(int [] arr){//O(n)
		//מקרה קצה שכולם שליליים
		if(Is_all_neg(arr))
			return Return_max(arr);
		//מקרה קצה שיש רק אחד חיובי
		if(Is_only_one_pos(arr))
			return Return_only_one_pos(arr);

		int tempstart=0,start=0,end=0;
		int sum = 0;
		int max=0;
		int farstPos = 0;
		//מציאת האיבר הראשון החיובי
		for(int i=0;i<arr.length;i++){
			if(arr[i]>0){
				farstPos = i;
				break;
			}
		}
		//תחילת האלגוריתם
		for(int i=farstPos;i<arr.length;i++){
			sum = sum + arr[i];
			//אם הסכום שלילי נאפס את הסכום ואת נק ההתחלה
			if(sum<=0){
				sum=0;
				tempstart=i+1;

			}
			//אם הגדלנו את הסכום נעדכן את המקסימום
			else if(sum>max){
				max= sum;
				end = i;
				start = tempstart;
			}

		}
		System.out.println(start + "---" + end);
		return max;
	}

	public static int BestZikli(int [] arr){
		int [] copyArr =new int [arr.length];
		int sum = 0;
		for(int i=0;i<arr.length;i++){
			copyArr[i] = -1*arr[i];
			sum = sum + arr[i];
		}
		return Math.max(Best(arr), Best(copyArr) + sum );
	}

	public static int[] bestInterval(int[] a){
		int i=0, ans[] = null;
		while (i<a.length && a[i]<=0) 
	            {i++;}
	        if (i == a.length){// a[i]<=0, i=1,...,a.length
	            int maxIndex = 0;
	            for (int j=1; j<a.length; j++){
	                if (a[j] > a[maxIndex]) 
	                    maxIndex = j;
	            }
	            ans = new int[1];
	            ans[0] = a[maxIndex];
	        } else {
	            int  endMax = i+1, count = 0, countMax = 1, bestCount = 1;
	            double maxSum=a[i], sum = 0;
	            while(i<a.length){
	                sum = sum + a[i];
	                count++;
	                if (sum <= 0){
	                    sum = 0;
	                    count = 0;
	                } else if (sum > maxSum){
	                    maxSum = sum;
	                    countMax = count;
	                    bestCount = countMax;
	                    endMax = i+1;
	                } else if(sum == maxSum){
	                    if (count < bestCount){
	                        bestCount = count;
	                        endMax = i+1;
	                    }
	                }
	                i++;
	            }
	            ans = new int[bestCount];
	            int start = endMax - bestCount;
	            for (int j = 0; j < bestCount; j++) {
	                ans[j] = a[start+j];
	            }
	        }
	        return ans;
	    }
	public static boolean isWay(int[] a, int[] b) {
		int n = a.length;
		int[] c = new int[n];
		for (int i = 0; i < n; i++) {
			c[i] = a[i] - b[i];
		}
		int[] best = bestCycle(c);
		int sum = 0;
		for (int i = 0; i < n; i++) {
			sum += c[(best[1]+i)%n];
			if(sum < 0) return false;
		}
		return true;
	}

	public static int[] bestCycle(int[] arr) {
		int n = arr.length;
		int sum = 0;
		int[] neg_arr = new int[n];
		for (int i = 0; i < n; i++) {
			sum += arr[i];
			neg_arr[i] = -arr[i];
		}
		int[] best1 = best(arr);
		int[] best2 = best(neg_arr);
		if(best1[0] < 0 || best1[0] >= sum + best2[0]) {
			return best1;
		}
		else {
			return new int[] {sum + best2[0], (best2[2] + 1)%n , best2[1] - 1};
		}
	}

	private static int[] best(int[] arr) {
		int n = arr.length;
		int max = Integer.MIN_VALUE;
		int s_i = 0, s_index = -1, e_index = -1;
		int sum = 0;
		for (int i = 0; i < n; i++) {
			sum += arr[i];
			if(sum > max) {
				max = sum;
				s_index = s_i;
				e_index = i;
			}
			if(sum < 0) {
				sum = 0;
				s_i = i + 1;
			}
		}
		return new int[] {max, s_index, e_index};
	}
	/**
	 * 'Best' algorithm for matrix - the best rectangle with maximum sum
	 * Using BEST algorithm
	 * Complexity: O(n*m^2)
	 * @param matrix
	 * @return [max sum, first i_index, first j_index, last i_index, last j_index]
	 */
	public static int[] bestMatrixEREZ(int[][] mat) {
		int si_index = -1, ei_index = -1, sj_index = -1, ej_index = -1;
		int n = mat.length;
		int m = mat[0].length;
		int max = Integer.MIN_VALUE;
		int[][] help = new int[n][m+1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				help[i][j+1] = help[i][j] + mat[i][j];
			}
		}
		for (int i = 0; i < m; i++) {
			for (int j = i; j < m; j++) {
				int[] temp = new int[n];
				for (int k = 0; k < n; k++) {
					temp[k] = help[k][j+1] - help[k][i];
				}
				int[] best = bestEREZ(temp);
				if(best[0] > max) {
					max = best[0];
					si_index = best[1];
					ei_index = best[2];
					sj_index = i;
					ej_index = j;
				}
			}
		}
		return new int[] {max, si_index, sj_index, ei_index, ej_index};
	}

	public static int[] bestEREZ(int[] arr) {
		int n = arr.length;
		int max = Integer.MIN_VALUE;
		int s_i = 0, s_index = -1, e_index = -1;
		int sum = 0;
		for (int i = 0; i < n; i++) {
			sum += arr[i];
			if(sum > max) {
				max = sum;
				s_index = s_i;
				e_index = i;
			}
			if(sum < 0) {
				sum = 0;
				s_i = i + 1;
			}
		}
		return new int[] {max, s_index, e_index};
	}
	class bast1_matrix {
		int [][] mat;
		Point p1, p2;
		int n,m;
		public bast1_matrix(int [][] mat1){
			this.mat = mat1;
			p1 = new Point();
			p2 = new Point();
			n = mat.length;
			m = mat[0].length;
		}

		public void bast_matrix_full(){
			System.out.println("bast matrix full on O((n*m)^3) ~~ O(n^6): ");
			int max=-1;
			for(int i=0;i<n;i++){
				for(int j=0;j<m;j++){
					for(int p=i;p<n;p++){
						for(int q=j;q<m;q++){
							int temp = sum_matrix(i,j,p,q);
							if(temp>max){
								max = temp;
								p1.setLocation(i, j);	
								p2.setLocation(p, q);
							}
						}
					}
				}
			}
			System.out.println("the max is: " + max);
			System.out.println("point 1 : " + p1.toString());
			System.out.println("point 2 : " + p2.toString());
		}


		public int sum_matrix(int is,int js,int ie,int je){
			int sum=0;
			for(int i = is;i<=ie;i++){
				for(int j=js;j<=je;j++){
					sum = sum+mat[i][j];
				}
			}
			return sum;
		}


		public void bast_matrix_beter(){
			//יצירת מטריצת העזרה הדינמית שתשמור את כל הסכומים על ידי הכלה והדחה
			System.out.println("bast matrix better on O((n*m)^2) ~~ O(n^4): ");
			int [][] H = new int [n][m];
			H[0][0] = mat[0][0];
			for(int i=1;i<n;i++)
				H[i][0] = H[i-1][0] + mat[i][0];
			for(int j=1;j<m;j++)
				H[0][j] = H[0][j-1] + mat[0][j];

			for(int i=1;i<n;i++){
				for(int j=1;j<m;j++){
					H[i][j] = H[i-1][j] + H[i][j-1] + mat[i][j] - H[i-1][j-1];
				}
			}
			/////
			int max = Integer.MIN_VALUE;
			int ii,jj,pp,qq;
			for(int i=0;i<n;i++){
				for(int j=0;j<m;j++){
					for(int p=i;p<n;p++){
						for(int q=j;q<m;q++){
							int temp = HhlaAndHdaha(H,i,j,p,q);
							if(temp>max){
								max = temp;
								p1.setLocation(i, j);
								p2.setLocation(p, q);	
							}
						}
					}
				}
			}

			System.out.println("the max is: " + max);
			System.out.println("point 1 : " + p1.toString());
			System.out.println("point 2 : " + p2.toString());

		}

		public void bast_matrix_beterWithBlackPoint(int [][] matrix, Point p1Black,Point p2Black){//O(n^4)

			int n= matrix.length;
			int m = matrix[0].length;
			Point p1 = new Point(),p2= new Point();
			int [][] mat = new int[n+1][m+1];
			int [][] H = new int [n][m];
			for(int i=0;i<n;i++){
				for(int j=0;j<m;j++){
					mat[i][j]=matrix[i][j];
				}
			}
			if((p1Black.x>=0 && p1Black.y>=0 &&p2Black.y>=0 && p2Black.x>=0)){
				for(int i=p1Black.x;i<=p2Black.x;i++){
					for(int j=p1Black.y;j<=p2Black.y;j++){
						mat[i][j]=-999999;
					}
				}
			}
			//יצירת מטריצת העזרה הדינמית שתשמור את כל הסכומים על ידי הכלה והדחה

			H[0][0] = mat[0][0];
			for(int i=1;i<n;i++)
				H[i][0] = H[i-1][0] + mat[i][0];
			for(int j=1;j<m;j++)
				H[0][j] = H[0][j-1] + mat[0][j];

			for(int i=1;i<n;i++){
				for(int j=1;j<m;j++){
					H[i][j] = H[i-1][j] + H[i][j-1] + mat[i][j] - H[i-1][j-1];
				}
			}
			/////
			int max = Integer.MIN_VALUE;
			int ii,jj,pp,qq;
			for(int i=0;i<n;i++){
				for(int j=0;j<m;j++){
					for(int p=i;p<n;p++){
						for(int q=j;q<m;q++){
							int temp = HhlaAndHdaha(H,i,j,p,q);
							if(temp>max){
								max = temp;
								p1.setLocation(i, j);
								p2.setLocation(p, q);	
							}
						}
					}
				}
			}

			System.out.println("the max is: " + max);
			System.out.println("point 1 : " + p1.toString());
			System.out.println("point 2 : " + p2.toString());

		}
		public  int HhlaAndHdaha(int[][] h, int i, int j, int p, int q) {
			if(i==0 && j==0)
				return h[p][q];
			else if(i==0 && j>0)
				return h[p][q] - h[p][j-1];
			else if(i>0 && j==0)
				return h[p][q] - h[i-1][q];
			else 
				return h[p][q] -  h[i-1][q]- h[p][j-1] + h[i-1][j-1];
		}
	}
	///////////////////////////////////////////////////endBest//////////////////////////

	///////////////////////////////////////////BFS//////////////////////////////////////////
	/**
	 * Breadth-first search
	 * Complexity: O(|V|+|E|)
	 */
	class BFS{
		ArrayList<Integer> [] G;
		int [] coler;
		int [] pre ;
		double [] dis ;
		int [] partsOfG;
		int numParts=0;
		int n;
		int start;
		final int white =0,black=2,gray=1;	
		boolean HasCircel=false;

		public BFS(ArrayList<Integer> [] G ){
			this.G=G;
			this.n = G.length;
			coler = new int [n];
			pre = new int [n];
			dis= new double [n];
			partsOfG = new int [n];
		}
		public void initAll(int start1){
			HasCircel=false;
			this.start = start1;
			for(int i=0;i<n;i++){
				dis[i]=Double.POSITIVE_INFINITY;
				pre[i] = -1;
				coler[i]=0;
			}
		}
		public void algoBFS(int start){
			initAll(start);
			//תחילת האלגו
			ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(n);
			dis[start]=0;//המרחק מההתחלה לעצמו הוא אפס
			queue.add(start);//נוסיף את ההתחלה לתור איתחול 
			coler[start]=gray;//נשנה לו את הצבע לאפור כי הכנסנו אותו לתור
			while(!queue.isEmpty()){
				int tempV = queue.poll();//נוציא אחד מהתור
				for(int u : G[tempV]){//נעבור על השכנים של הקודקוד הזמני
					//אם הגעתי לקודקוד שעברתי עליו וגם לא הגעתי אליו מהאבא של ווי כי החיפוש לרוחב אז הגעתי מכיוון אחר כלומר יש מעגל
					if(coler[u]!=white && u!=pre[tempV])
						HasCircel = true;
					if(coler[u]==white){//אם לא נגענו בו והצבע שלו ללבן
						coler[u]=gray;//נשנה לאפור
						pre[u]=tempV;//נודיע על אבא חדש
						dis[u]=dis[tempV] + 1 ;//המרחק יהיה אותו מרחק של הזמני פלוס אחד
						queue.add(u);}//נוסיף את השכן שעבדנו עליו לתור
				}
//				else if(dis[u]==dis[v]+1 )//אם מספר המסלולים שווים של האבא פלוס אחד ושל הבן
//					ans[u]= ans[u] + ans[v];//אז יש עוד דרך להגיע ליו מצד שני אז בכום המסלולים הוא סכום שני הדרכים
			
				coler[tempV] = black;//נשנה את הצבע לשחור 
			}

		}

		public String GetPath(int s,int e){
			algoBFS(s);
			String ans = "" ;
			if (coler[e]==white) return null;//הוא לא קשיר אין מסלול אליו
			if (e==s) return ans = ans + s;
			else{
				ans = ans + e; //נתחיל מהסוף =נכניס לתשובה את האינקדדס האחרון
				int EndTemp = pre[e];
				while (pre[EndTemp] != -1){
					ans = EndTemp + "->" + ans;
					EndTemp = pre[EndTemp];
				}
			}
			return ans = s + "->" + ans;
		}
		//אם יש מעגל איזוגי הוא לא דו צדדי כל השאר כן
		//כל עץ הוא גרף דו צדדי
		public boolean isBipartite (){
			if(isTree())return true;//כל עץ הוא מעגל דו צדדי אך לא להפך
			initAll(0);
			int division[] = new int[n];
			ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(n);
			dis[start] = 0;
			coler[start] = gray;
			division[start] = 1;//מתחילים לצבוע בצבע אחד את ההתחלה
			queue.add(start);//מכניסים לתור את האינקס הראשון ההתחלתי
			while(!queue.isEmpty()){
				int tempV = queue.poll();//מוציאים קודקוד ראשון ווי
				for(int u : G[tempV]){//ועוברים על שכניו שהם יו
					if (division[u] == division [tempV]){//אם לווי יש אותו מספר כמו לשכן שלו הגרף לא דו צדדי
						return false;	
					}
					else if (coler[u] == white){//אם זה פעם ראשונה שאני פוגש את יו
						dis[u] = dis[tempV]+1;//המרחק שלו הוא אחד יותר מהמרחק של האבא שלו ווי
						pre[u] = tempV;//האבא שלו זה ווי
						coler[u] = gray;//ומשנים לו את הצבע לאפור
						division[u] = 3 - division[tempV];//  וצובעים את השכן יו במספר שתיים= כל השכנים של ווי בשתיים, ווי עצמו באחד
						queue.add(u);//מוסיפים את יו לתור
					}
				}
				coler[tempV] = black;//סיימנו עם כל שכני ווי אפשר לשנות לשחור
			}
			return true;
		}
		public boolean IsCircel(){
			algoBFS(0);
			return HasCircel;
		}
		public boolean isTree(){
			int deg =0;
			int number_of_Vitrix = n;
			if(number_of_Vitrix < 2) return false; //בעץ חייבים להיות לפחות שני קודקודים
			for(int i=0;i<n;i++){
				deg = deg + G[i].size();
			}
			if(deg%2!=0)return false; //התנאי ההכרחי לגרפים הוא שמספר הדרגות זוגי
			int edge = deg/2;
			if(edge!=number_of_Vitrix - 1 )return false;//התנאי על עצים אומר שמספר הקודקודים פחות אחד שווה למספר הצלעות
			if(IsCircel()) return false;
			return true;
		}
		public boolean isConnected(){
			algoBFS(start);
			for(int i=0;i<n;i++){
				if(dis[i]==Double.POSITIVE_INFINITY){
					return false;
				}
			}
			return true;
		}

		public int GetNemberOfConnected(){
			fillParts();	
			return numParts;
		}
		//נפעיל את האלגוריתם ונבדוק שאין מרחק אינסוף =אם יש מרחק כזה אז יש עוד רכיב קשירות
		//כלומר אני נותן מספר לכל רכיב קשירות ומעלה אותו במידת הצורך כל אינסוף שאני פוגש
		private void fillParts() {
			while (hasNextComponent()){//כל עוד יש עוד רכיבי קשירות בגרף
				numParts++;//נעלה את הרכיבי קשירות
				algoBFS(start);//נפעיל את האלגוריתם מהמקום שלא היה קשיר
				for (int i = 0; i < partsOfG.length; i++) {
					if (dis[i]!=Double.POSITIVE_INFINITY)//אם הוא לא אינסוף אז הקודקוד הזה חלק מהגרף
						partsOfG[i] = numParts;//נכניס את מספר רכיבי הקשירות שמצאתי				
				}
			}
		}
		private boolean hasNextComponent(){
			for (int i = 0;  i < partsOfG.length; i++) {
				if(partsOfG[i] == 0) {
					start = i;
					return true;
				}
			}
			return false;
		}
		public ArrayList<Integer>[] returnAllParts(){
			ArrayList<Integer>[] ans = new ArrayList[GetNemberOfConnected()];
			for (int i = 0; i < ans.length; i++) {
				ans[i] = new ArrayList<Integer>();
			}
			for (int i = 0; i < partsOfG.length; i++) {
				ans[partsOfG[i]-1].add(i);
			}
			for (int i = 0; i < ans.length; i++) {
				System.out.print( i+ ": "+ans[i].toString());
				System.out.println();
			}
			return ans;
		}
		public double GetDimater(){
			initAll(0);
			if(n==2)return 1;
			if(n<2) return -1;
			algoBFS(0);
			algoBFS(GetMaxDistIndex());
			return dis[GetMaxDistIndex()];
		}
		public int GetMaxDistIndex(){
			int ans=0;
			double max = -1;
			for(int i=0;i<dis.length;i++){
				if(dis[i]>max){
					max= dis[i];
					ans=i;
				}
			}
			return ans;
		} 
	}
	/////////////////////////////////////////////////////////////BFS..//////////////////////////////
	/////////////////////////////////////////////////////////////DFS..//////////////////////////////
	public void dfs(ArrayList<Integer> [] G) {
		final int white =0,black=2,gray=1;
		int n = G.length;
		int time=0;
		int [] color = new int [n];
		int [] first = new int [n];
		int [] last = new int [n];
		int [] pre = new int [n];
		boolean Circel = false;
		Arrays.fill(pre, -1);
		for(int i=0;i<n;i++){//לכל הקודקודים בגרף נפעיל את האלגוריתם
			if(color[i]==white){
				recorsyaDFS(G,i, white, black, gray, Circel, color, pre, first , last, time);
			}
		}
	}
	//החלק הרקורסיבי
	public void recorsyaDFS(ArrayList<Integer> [] G,int v,int white,int black,int gray,boolean Circel,int [] color,int [] pre,int [] first ,int [] last,int time) {
		first[v] = ++time;//נרשום את הכניסה לעומק
		color[v]=gray;//נשנה את הצבע של הקודקוד שעובדים עליו
		for(int u : G[v]){//נעבור על השכנים של ווי דרך הקודקוד יו
			//אם ישר מעגל אז נראה קודקוד שעברנו עליו כבר וגם! שלא הגענו דרכו כלומר האבא של ווי לא יו כי באנו מיו
			if(color[u]==gray && pre[v]!=u)
				Circel = true;

			if(color[u]==white){
				pre[u]=v;
				recorsyaDFS(G,u, white, black, gray, Circel, color, pre, first , last, time);
			}
		}
		last[v]=++time;//סיימנו לעבוד על ווי אז נרשום את היציאה מהעומק
		color[v]=black;//סיימנו לעבוד על ווי אז נשנה לו את הצבע לשחור
	}

	/////////////////////////////////////////////////////////////DFS..//////////////////////////////
	///.//////////////////////////////////////////////////////מעגל אויילר
	/**
	 * Finding Euler Path/Cycle in graph and returns array list contains the path or null if there is no path
	 * Complexity: O(|E|) - if we save for each edges a flag for visiting instead of remove
	 */
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
	///.//////////////////////////////////////////////////////מעגל אויילר
	//שריפת עלים
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
	//////////////////////////////////////////////////////////////////////////////////
	public class Huffman {//On

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
			//	chars = getCharsFromString(s);
			//	times = getTimesFromString(s);
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
			//FixArr_sort();//רק אם המערך לא ממויין
			codes  = new String [n];
			mat = new int [2*n-1][4]; //0=sum L+R,newV,times ; 1=Left ; 2 = Right ; 3=pre
			q1 = new ArrayBlockingQueue<Integer>(n);
			q2 = new ArrayBlockingQueue<Integer>(n);
			AlgoBuildTree();
			MakeCode();
		}
		//אם המערך לא ממויין נמיין בצורה חד חד ערכית לאותיות
		//		public void FixArr_sort(){
		//			node [] arr = new node[n];
		//			for(int i=0;i<n;i++){
		//				arr[i] = new node(times[i], chars[i]);
		//			}
		//			Arrays.sort(arr);
		//			int [] temp1 = new int [n];
		//			char [] temp2 = new char[n];
		//	 		for(int i=0;i<n;i++){
		//				temp1[i] = arr[i].nember;
		//				temp2[i] = arr[i].let;
		//			}
		//	 		times = temp1;
		//	 		chars = temp2;
		//		}
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
	}
	////////////////////////////////////////קבלת מערך דרגות צריך לבדוק אם זה עץ או גרף חוקי
	public static ArrayList<Integer>[] GetTree(int [] deg2){
		if(!IsTree(deg2)) return null; //התנאים ההכרחיים לקיום עץ
		//איתחול
		int n = deg2.length;
		ArrayList[] tree = new ArrayList[n];
		int [] deg = new int [n];
		for(int i=0;i<n;i++){
			tree[i] = new ArrayList<Integer>();
			deg[i] = deg2[i];
		}
		if(n==2){
			tree[1].add(0);
			tree[0].add(1);
			return tree;
		}
		//ממינים ומוצאים את קבוצת הקודקודים השונים מ1 הסטופ הוא הראשון השווה לאחד
		int stop=0;
		sort(deg);
		stop=0;
		for(int i=0;i<n;i++){
			if(deg[i]==1){
				stop=i;
				break;
			}
		}
		//תחילת האלגוריתם
		int v=0,nextv=1;
		while(v<stop){//כל עוד לא סיימנו את קבוצת הקודקודים הגדולה מ1
			for(int i=0;i<deg[v];i++){//נעבור כמספר הדרגה	
				tree[v].add(nextv);//נוסיף לעץ
				tree[nextv].add(v);
				nextv++;//נעלה את השכן הבא
			}
			deg[v++]=0;//הוספתי כמספר הדרגה אז לווי יש את כל השכנים אז נאפס את השכנים שלו
			sort(deg);//נמיין שוב מהגדול לקטן
		}

		return tree;
	}
	private static void sort(int[] arr) {
		for(int i=0;i<arr.length;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i]<arr[j]){
					int t = arr[i];
					arr[i]=arr[j];
					arr[j]=t;
				}
			}
		}
	}
	public static boolean IsTree(int [] deg){
		int n = deg.length;
		int sum = 0;
		for(int i=0;i<n;i++){
			sum = sum + deg[i];
			if(deg[i]>=n)return false;//לא יתכן דרגה שגדולה ממספר הקודקודים או שווה לה
		}
		if(sum%2!=0) return false; //התנאי היסודי של גרפים= סכום כל הדרגות הוא זוגי
		if(sum !=2*(n-1))return false;//בעץ סכום הדרגות שווה לפעמיים מספר הצלעות=ומספר הצלעות שווה לאן פחות אחד
		return true;
	}
	/**
	 * @param array of degrees
	 * @return if the degrees array represents a graph
	 * Complexity: O(log n * n^2)
	 */
	public static boolean isGraph(int[] degrees) {
		int n = degrees.length;
		int sum = 0;
		for (int i = 0; i < n; i++) {
			sum += degrees[i];
		}
		if(sum % 2 != 0) {
			return false;
		}
		Arrays.sort(degrees);
		for (int i = n-1; i >= 0; i--) {
			if(degrees[i] != 0) {
				int d = degrees[i];
				if(i-d < 0) return false;
				degrees[i] = 0;
				for (int j = i-1; j >= i-d; j--) {
					if(degrees[j] == 0) return false;
					degrees[j]--;
				}
				Arrays.sort(degrees,0,i-1);
			}
		}
		return true;
	}
	/////////////////////////////////////////////////////gas////////////////////////////////
	/**
	 * @param a - the gas station capacity
	 * @param b - the fuel that we waste in the way
	 * @return if there is a way to complete the road
	 * Complexity: O(n)
	 */

	//////////////////////////////////////////////////////////////////////////////
	class Disjoint_Set {

		int [] pernt;
		int [] rank;
		public Disjoint_Set(int size){
			pernt = new int[size];
			rank = new int [size];
			for(int i=0;i<size;i++){//כל קודקוד הוא האבא של עצמו כברירת מחדל
				pernt[i]=i;
				rank[i]=0;
			}

		}
		public int find(int v){
			int p = pernt[v];
			if(p==v){//אם האבא שווה לווי אז אנחנו בשורש 
				return v;
			}
			else{
				return pernt[v]=find(p);//אחרת לא הגענו לשורש אז האבא של ווי זה פי
			}
		}
		public boolean union(int x,int y){
			int rootx = find(x);//הופכים את תת העץ שיהיה מחובר לשורש
			int rooty= find(y);

			if(rootx==rooty)//אם התתי עצים מחוברים באותו שורש אין צורך לאחד כי הם מאוחדים כבר
				return false;

			//צריך לעדכן את הדרגות לפי הדרגה הקטנה יותר,שלא יהיה עומק בעץ
			if(rank[rootx]>rank[rooty]){
				pernt[rooty]=rootx;// האבא של הקודקוד עם הדרגה הנמוכה יותר מתחבר לקודקוד עם הדרגה הגבוהה יותר
			}
			else if(rank[rootx]<rank[rooty]){
				pernt[rootx]=rooty;
			}
			else{//אם הדרגות שוות נחבר מישהו שלא משנה לשני ונעלה לו את הדרגה כי חיברנו אליו עוד תת עץ
				pernt[rootx]=rooty;
				rank[rootx]++;
			}
			return true;
		}
	}

	/**
	 * Kruskal's algorithm for MST
	 * Complexity: O(|E|log|V|) - using disjointsets
	 */
	class Kaskal {
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
	}
	public static boolean Ismorfizim(ArrayList<Integer>[] G1 ,ArrayList<Integer>[] G2 ){
		if(G1.length!=G2.length)return false;//מספר קודקודים שונה
		int n = G1.length;
		int [] arr1 = new int [n];
		int [] arr2 = new int [n];
		int sum1=0,sum2=0;
		for(int i=0;i<n;i++){
			arr1[i] = G1[i].size();
			arr2[i]= G2[i].size();
			sum1 = sum1 + arr1[i];
			sum2 = sum2 + arr2[i];
		}
		if(sum1!=sum2)return false;//סכום הדרגות שונה גם מספר הצלעות שונה
		Arrays.sort(arr1);
		Arrays.sort(arr2);//השוואת דרגות שיהיו זהות
		for(int i=0;i<n;i++){
			if(arr1[i]!=arr2[i])
				return false;
		}
		//int [] c1 = fireAlgo(G1);//השוואת מרכזים וקוטר
		//int [] c2 = fireAlgo(G2);
		//for(int i=0;i<c1.length;i++)
		//if(c1[i]!=c2[i])return false;
		return true;
	}
	//////////////////////////////////////////////////////////////////
	public static ArrayList<Integer>[] initGraph4(){//connected graph with circle
		int size = 6;
		ArrayList<Integer>[] graph = new ArrayList[size];
		for (int i = 0; i < size; i++) {
			graph[i] = new ArrayList<Integer>();
		}
		graph[0].add(1); graph[0].add(3); 
		graph[1].add(0); graph[1].add(4);  graph[1].add(2); 
		graph[2].add(1); graph[2].add(5); 
		graph[3].add(0); graph[3].add(4); 
		graph[4].add(1); graph[4].add(3); graph[4].add(5); 
		graph[5].add(2); graph[5].add(4); 
		return graph;
	}
	public static ArrayList<Integer>[] initGraph5(){//K4
		int size = 4;
		ArrayList<Integer>[] graph = new ArrayList[size];
		for (int i = 0; i < size; i++) {
			graph[i] = new ArrayList<Integer>();
		}
		graph[0].add(1); graph[0].add(2); graph[0].add(3); 
		graph[1].add(0); graph[1].add(3); graph[1].add(2); 
		graph[2].add(0); graph[2].add(3); graph[2].add(1); 
		graph[3].add(1); graph[3].add(2); graph[3].add(0); 
		return graph;
	}
	public static ArrayList<Integer>[] initGraph6(){//P5
		int size = 5;
		ArrayList<Integer>[] graph = new ArrayList[size];
		for (int i = 0; i < size; i++) {
			graph[i] = new ArrayList<Integer>();
		}
		graph[0].add(1); 
		graph[1].add(0); graph[1].add(2);
		graph[2].add(1); graph[2].add(3);
		graph[3].add(2); graph[3].add(4);
		graph[4].add(3);
		return graph;
	}
    public static int TotalTime(ArrayList<Integer> band, int n){
        
        if (n < 3)
            return band.get(n - 1);
        else if (n == 3)
            return band.get(0) + band.get(1) + band.get(2);
        else
        {
            int temp1 = band.get(n - 1) + band.get(0) + band.get(n - 2) + band.get(0);
            int temp2 = band.get(1) + band.get(0) + band.get(n - 1) + band.get(1);

            if (temp1 < temp2)
                return temp1 + TotalTime(band, n - 2);
            else if (temp2 < temp1)
                return temp2 + TotalTime(band, n - 2);
            else
                return temp2 + TotalTime(band, n - 2);
        }
    }
	/////////////////////////////////////////////
	//המסלול הכי ארוך בגרף עובר דרך מרכז העץ
	//כל עץ הוא גרף דוצ=גרף דוצ אממ כל המעגלים באורך זוגי=רק מעגל באורך אי זוגי דופק
	//מעגל אוילר אמממ כל הדרגות זוגיות
	//גרף שמספר הצלעות גדול או שווה למספר הקודקודים קיים מעגל
	//מספר השרפות פלוס אחד שווה לרדיוס
}
