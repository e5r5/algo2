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
					//���� ����� ������� ���� ������ �� i,j?
					h[index][index(i,0)] = true;//���� ��� �� ������ ������ �� ������
					h[index][index(0,j)] = true;//��� ��� �� ������ ������ �� ������
					h[index][index(n,j)] = true;//���� ����� �� ������ ��� ���� ������
					h[index][index(i,m)] = true;//���� �����  �� ������ ��� ���� ������
					h[index][index(Math.max(0,i+j-m),Math.min(m, i+j))] = true;//���� ����� ����� ��� ����� ����� ���� ��������
					h[index][index(Math.min(i+j, n),Math.max(0,i+j-n))] = true;//����� ����� ��� ���� 
					path[i][j] = "";
				}
			}
		}
		private void buildPath() {
			for(int i=0;i<n+1;i++){
				for(int j=0;j<m+1;j++){
					int index = index(i,j);
					//���� ����� ������� ���� ������ �� i,j?
					path[index][index(i,0)] = "("+i+","+j+")->"+"("+i+","+0+");";//���� �� ������ ������ �� ������
					path[index][index(0,j)] ="("+i+","+j+")->"+"("+0+","+j+");";;//��� �� ������ ������ �� ������
					path[index][index(n,j)] = "("+i+","+j+")->"+"("+n+","+j+");";//���� �� ������ ��� ���� ������
					path[index][index(i,m)] = "("+i+","+j+")->"+"("+i+","+m+");";;//���� �� ������ ��� ���� ������
					int a =index(Math.max(0,i+j-m),Math.min(m, i+j));
					path[index][a] = "("+i+","+j+")->"+"("+iIndex(a)+","+jIndex(a)+");";//���� ����� ����� ��� ����� ����� ���� ��������
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

		//���� ����� ������� ������ ����� �� ����� ������� ����� �� ����� �� �� ��������� �� ����� ��������
		public int index(int i,int j){	
			return (m+1)*i+j; //���� ������� ���� ��� ���� ������ ������ �� ������ ���� ������
			//���� � i*m+j
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
	//����� ������ �������� ������ �� ��������� ��� �����
	public static double [][] distFromVtoE(boolean [][] mat,double [][] matPrice,double [] price){
		//���� ������ �� �������
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
		//����� �����
		fwPrice(matPrice);
		//	printMat(matPrice);
		//����� ����� �� ��� ���������� ����� ������
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
	//������ �� ������ ����� �� ������� ������� ��������� ����� ��� ������� �� �����
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
	//////////////////////////////////////���� ��� /////////////////////////////////////////////////////////////
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
		maxV +=1;//�� ������� �� ������ ����
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
	//////////////////////////////////////���� ��� /////////////////////////////////////////////////////////////
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

	//����� �����
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
		//���� ��� ����� �������
		if(Is_all_neg(arr))
			return Return_max(arr);
		//���� ��� ��� �� ��� �����
		if(Is_only_one_pos(arr))
			return Return_only_one_pos(arr);

		int tempstart=0,start=0,end=0;
		int sum = 0;
		int max=0;
		int farstPos = 0;
		//����� ����� ������ ������
		for(int i=0;i<arr.length;i++){
			if(arr[i]>0){
				farstPos = i;
				break;
			}
		}
		//����� ���������
		for(int i=farstPos;i<arr.length;i++){
			sum = sum + arr[i];
			//�� ����� ����� ���� �� ����� ��� �� ������
			if(sum<=0){
				sum=0;
				tempstart=i+1;

			}
			//�� ������ �� ����� ����� �� ��������
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
			//����� ������ ����� ������� ������ �� �� ������� �� ��� ���� �����
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
			//����� ������ ����� ������� ������ �� �� ������� �� ��� ���� �����

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
			//����� �����
			ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(n);
			dis[start]=0;//����� ������� ����� ��� ���
			queue.add(start);//����� �� ������ ���� ������ 
			coler[start]=gray;//���� �� �� ���� ����� �� ������ ���� ����
			while(!queue.isEmpty()){
				int tempV = queue.poll();//����� ��� �����
				for(int u : G[tempV]){//����� �� ������ �� ������� �����
					//�� ����� ������� ������ ���� ��� �� ����� ���� ����� �� ��� �� ������ ����� �� ����� ������ ��� ����� �� ����
					if(coler[u]!=white && u!=pre[tempV])
						HasCircel = true;
					if(coler[u]==white){//�� �� ����� �� ����� ��� ����
						coler[u]=gray;//���� �����
						pre[u]=tempV;//����� �� ��� ���
						dis[u]=dis[tempV] + 1 ;//����� ���� ���� ���� �� ����� ���� ���
						queue.add(u);}//����� �� ���� ������ ���� ����
				}
//				else if(dis[u]==dis[v]+1 )//�� ���� �������� ����� �� ���� ���� ��� ��� ���
//					ans[u]= ans[u] + ans[v];//�� �� ��� ��� ����� ��� ��� ��� �� ���� �������� ��� ���� ��� ������
			
				coler[tempV] = black;//���� �� ���� ����� 
			}

		}

		public String GetPath(int s,int e){
			algoBFS(s);
			String ans = "" ;
			if (coler[e]==white) return null;//��� �� ���� ��� ����� ����
			if (e==s) return ans = ans + s;
			else{
				ans = ans + e; //����� ����� =����� ������ �� �������� ������
				int EndTemp = pre[e];
				while (pre[EndTemp] != -1){
					ans = EndTemp + "->" + ans;
					EndTemp = pre[EndTemp];
				}
			}
			return ans = s + "->" + ans;
		}
		//�� �� ���� ������ ��� �� �� ���� �� ���� ��
		//�� �� ��� ��� �� ����
		public boolean isBipartite (){
			if(isTree())return true;//�� �� ��� ���� �� ���� �� �� ����
			initAll(0);
			int division[] = new int[n];
			ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(n);
			dis[start] = 0;
			coler[start] = gray;
			division[start] = 1;//������� ����� ���� ��� �� ������
			queue.add(start);//������� ���� �� ������ ������ �������
			while(!queue.isEmpty()){
				int tempV = queue.poll();//������� ������ ����� ���
				for(int u : G[tempV]){//������� �� ����� ��� ��
					if (division[u] == division [tempV]){//�� ���� �� ���� ���� ��� ���� ��� ���� �� �� ����
						return false;	
					}
					else if (coler[u] == white){//�� �� ��� ������ ���� ���� �� ��
						dis[u] = dis[tempV]+1;//����� ��� ��� ��� ���� ������ �� ���� ��� ���
						pre[u] = tempV;//���� ��� �� ���
						coler[u] = gray;//������ �� �� ���� �����
						division[u] = 3 - division[tempV];//  ������� �� ���� �� ����� �����= �� ������ �� ��� ������, ��� ���� ����
						queue.add(u);//������� �� �� ����
					}
				}
				coler[tempV] = black;//������ �� �� ���� ��� ���� ����� �����
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
			if(number_of_Vitrix < 2) return false; //��� ������ ����� ����� ��� ��������
			for(int i=0;i<n;i++){
				deg = deg + G[i].size();
			}
			if(deg%2!=0)return false; //����� ������ ������ ��� ����� ������ ����
			int edge = deg/2;
			if(edge!=number_of_Vitrix - 1 )return false;//����� �� ���� ���� ����� ��������� ���� ��� ���� ����� ������
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
		//����� �� ��������� ������ ���� ���� ������ =�� �� ���� ��� �� �� ��� ���� ������
		//����� ��� ���� ���� ��� ���� ������ ����� ���� ����� ����� �� ������ ���� ����
		private void fillParts() {
			while (hasNextComponent()){//�� ��� �� ��� ����� ������ ����
				numParts++;//���� �� ������ ������
				algoBFS(start);//����� �� ��������� ������ ��� ��� ����
				for (int i = 0; i < partsOfG.length; i++) {
					if (dis[i]!=Double.POSITIVE_INFINITY)//�� ��� �� ������ �� ������� ��� ��� �����
						partsOfG[i] = numParts;//����� �� ���� ����� ������� ������				
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
		for(int i=0;i<n;i++){//��� ��������� ���� ����� �� ���������
			if(color[i]==white){
				recorsyaDFS(G,i, white, black, gray, Circel, color, pre, first , last, time);
			}
		}
	}
	//���� ���������
	public void recorsyaDFS(ArrayList<Integer> [] G,int v,int white,int black,int gray,boolean Circel,int [] color,int [] pre,int [] first ,int [] last,int time) {
		first[v] = ++time;//����� �� ������ �����
		color[v]=gray;//���� �� ���� �� ������� ������� ����
		for(int u : G[v]){//����� �� ������ �� ��� ��� ������� ��
			//�� ��� ���� �� ���� ������ ������ ���� ��� ���! ��� ����� ���� ����� ���� �� ��� �� �� �� ���� ���
			if(color[u]==gray && pre[v]!=u)
				Circel = true;

			if(color[u]==white){
				pre[u]=v;
				recorsyaDFS(G,u, white, black, gray, Circel, color, pre, first , last, time);
			}
		}
		last[v]=++time;//������ ����� �� ��� �� ����� �� ������ ������
		color[v]=black;//������ ����� �� ��� �� ���� �� �� ���� �����
	}

	/////////////////////////////////////////////////////////////DFS..//////////////////////////////
	///.//////////////////////////////////////////////////////���� ������
	/**
	 * Finding Euler Path/Cycle in graph and returns array list contains the path or null if there is no path
	 * Complexity: O(|E|) - if we save for each edges a flag for visiting instead of remove
	 */
	public static void EulerCycle2(ArrayList<Integer> [] g){
		//����� ����� ����
		int n = g.length;
		int [] deg = new int [n];
		Stack<Integer> st = new Stack<Integer>();
		ArrayList<Integer> [] Gtemp = new ArrayList[n];
		//���� ���� �� ��� �� �� ������ ������ ��� ���� ������
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
			if(deg[v]==0){//�� ����� �� ��� ��� �� ������ �� ������� ��� �� ����� ���� ���� �� ��� ����� ����
				ans = ans + "=>" + v;
				st.pop();
			}
			else{
				int u = Gtemp[v].get(0);//������ �� ���� �� ��� ���� ��
				st.add(u);//������� �� �� �������
				deg[v]--;//������ �� ������ ������
				deg[u]--;
				Gtemp[u].remove((Integer)v);//������ ����� �����
				Gtemp[v].remove((Integer)u);
				v=u;//������� ��� �� ������ ������ �� ����
			}
		}
		System.out.println(ans);

	}
	///.//////////////////////////////////////////////////////���� ������
	//����� ����
	public static void fireAlgo(ArrayList<Integer> [] tree){
		int n = tree.length;
		int radius=1;
		int [] deg = new int [n];
		ArrayList<Integer> leaves = new ArrayList<Integer>();
		//������ 
		for(int i=0;i<n;i++){
			deg[i] = tree[i].size();//����� �� ���� ������ �� �� ������ ��� ������
			if(deg[i]==1)//�� ��� ��� ����� ����
				leaves.add(i);
		}
		//����� ���������
		while(n>2){
			ArrayList<Integer> temp_leaves = new ArrayList<Integer>();
			for(int lev : leaves){
				deg[lev]=0;//���� ��� ��� 
				for(int update_neubard : tree[lev]){//����� �� ������ �� ������
					if(deg[update_neubard] > 0){
						deg[update_neubard] --;
						if(deg[update_neubard] == 1){//�� ���� ������� ������ ����� ��� ��� ����� ���� ���������� ����
							temp_leaves.add(update_neubard);
						}
					}
				}
				n--;//����� �� �� �� �� �� ��� ������ ����� �� ����� ��� 	
			}
			radius++;//������ ���� �� ����� ��� ����� �����
			leaves = temp_leaves; //������ ���� ����� 
		}
		System.out.println((leaves));
		//��� ���������
		if(n==2){ //�� �� 2 ������
			System.out.println("c1 is: " + leaves.get(0));
			System.out.println("c2 is: " + leaves.get(1));
			System.out.println("radius is: " + radius);
			System.out.println("diameter: " + (2*radius -1));
		}
		else{ //���� ���� ���
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
		ArrayBlockingQueue<Integer> q1 ;//����� �� ��������� �������� �� ���
		ArrayBlockingQueue<Integer> q2 ;//����� �� ���������  ������ ������ ������� �� �� ��� ����

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
			//FixArr_sort();//�� �� ����� �� ������
			codes  = new String [n];
			mat = new int [2*n-1][4]; //0=sum L+R,newV,times ; 1=Left ; 2 = Right ; 3=pre
			q1 = new ArrayBlockingQueue<Integer>(n);
			q2 = new ArrayBlockingQueue<Integer>(n);
			AlgoBuildTree();
			MakeCode();
		}
		//�� ����� �� ������ ����� ����� �� �� ����� �������
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
				mat[i][0]=times[i];//������ ����� ������ �� �������
				q1.add(i);//����� ��������� ����
				mat[i][1]=-1;
				mat[i][2]=-1;
			}

			//����� ������ 
			int k=n;//��� ���
			int left = q1.poll();//������ ����� ������� �� ��������� �� ������� ������ 
			int right = q1.poll();
			mat[k][0]=mat[left][0]+mat[right][0];//������� �� ������� ���� �� ������ �������� ��� ���� ������� ���� 
			mat[k][1]=left;//������ ����=������� ���� �����= �� ����� ���
			mat[k][2]=right;
			mat[left][3]=k;//������ ����� �� ���� ���� ����
			mat[right][3]=k;
			q2.add(k);//�� ������� ���� ���� ������� ���� ����� ���� ��� ��������� ������=����� ��������� ������
			k++;//����� ������ ���� ���� ����

			//��� ������ �� ����� =���� ������ ��������� ������ ���
			while(q1.size()+q2.size()>1){//������� ����� ������� �� ������ ��� �� �� �� ��
				left = getMin();//������ ����� ������� �� ��������� �� ������� ������ 
				right = getMin();
				mat[k][0]=mat[left][0]+mat[right][0];//������� �� ������� ���� �� ������ �������� ��� ���� ������� ���� 
				mat[k][1]=left;//������ ����=������� ���� �����= �� ����� ���
				mat[k][2]=right;
				mat[left][3]=k;//������ ����� �� ���� ���� ����
				mat[right][3]=k;
				q2.add(k);//�� ������� ���� ���� ������� ���� ����� ���� ��� ��������� ������=����� ��������� ������
				k++;//
			}
			mat[2*n-1-1][3]=-1;//��� ��� �����
		}

		private int getMin() {//������ �2 ������ ������� �� �2 ���������=��� ��������� ��������� ���� �����
			if(q1.size()==0) return q2.poll(); //�� ������ ��� �� ���� ����� ����=����� ����
			if(q2.size()==0) return q1.poll();
			if(mat[q1.peek()][0]<mat[q2.peek()][0])//������ �� ������ �������� �� ������ ��������
				return q1.poll();
			else 
				return q2.poll();
		}
		private void MakeCode() {
			for(int i=0;i<n;i++){//��� ��� ������ �� ���� �������
				String ans = "";
				int v=i;//������� ������� �� ���� ������� ���� �� ���= ����� ����� ����� �� ����� ������ ������ ���� �����
				int p;
				while(v!=2*n-1-1 ){//�� ����� ����� ������ ��� �� ���� ��� ��� ������� ��� �� ��� ���� ���
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
	////////////////////////////////////////���� ���� ����� ���� ����� �� �� �� �� ��� ����
	public static ArrayList<Integer>[] GetTree(int [] deg2){
		if(!IsTree(deg2)) return null; //������ �������� ����� ��
		//������
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
		//������ ������� �� ����� ��������� ������ �1 ����� ��� ������ ����� ����
		int stop=0;
		sort(deg);
		stop=0;
		for(int i=0;i<n;i++){
			if(deg[i]==1){
				stop=i;
				break;
			}
		}
		//����� ���������
		int v=0,nextv=1;
		while(v<stop){//�� ��� �� ������ �� ����� ��������� ������ �1
			for(int i=0;i<deg[v];i++){//����� ����� �����	
				tree[v].add(nextv);//����� ���
				tree[nextv].add(v);
				nextv++;//���� �� ���� ���
			}
			deg[v++]=0;//������ ����� ����� �� ���� �� �� �� ������ �� ���� �� ������ ���
			sort(deg);//����� ��� ������ ����
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
			if(deg[i]>=n)return false;//�� ���� ���� ������ ����� ��������� �� ���� ��
		}
		if(sum%2!=0) return false; //����� ������ �� �����= ���� �� ������ ��� ����
		if(sum !=2*(n-1))return false;//��� ���� ������ ���� ������� ���� ������=����� ������ ���� ��� ���� ���
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
			for(int i=0;i<size;i++){//�� ������ ��� ���� �� ���� ������ ����
				pernt[i]=i;
				rank[i]=0;
			}

		}
		public int find(int v){
			int p = pernt[v];
			if(p==v){//�� ���� ���� ���� �� ����� ����� 
				return v;
			}
			else{
				return pernt[v]=find(p);//���� �� ����� ����� �� ���� �� ��� �� ��
			}
		}
		public boolean union(int x,int y){
			int rootx = find(x);//������ �� �� ��� ����� ����� �����
			int rooty= find(y);

			if(rootx==rooty)//�� ���� ���� ������� ����� ���� ��� ���� ���� �� �� ������� ���
				return false;

			//���� ����� �� ������ ��� ����� ����� ����,��� ���� ���� ���
			if(rank[rootx]>rank[rooty]){
				pernt[rooty]=rootx;// ���� �� ������� �� ����� ������ ���� ����� ������� �� ����� ������ ����
			}
			else if(rank[rootx]<rank[rooty]){
				pernt[rootx]=rooty;
			}
			else{//�� ������ ���� ���� ����� ��� ���� ���� ����� �� �� ����� �� ������ ���� ��� �� ��
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
			v++;//�� ������� ���� �� ���� ��� ���������
			ds = new Disjoint_Set(v);
			tree = new Edge[v-1];
			Algo();
		}

		private void Algo() {
			Arrays.sort(g);
			int c = 0,i=0;//�� ���� �� ��� ��� ���� �� ����� ��� ���� ���� ������ ���� ��� ����� ��  
			while(c<v-1){//�� ���� �� ������� �� �� ���� ���� ���� ����� ������ ��� �����
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
		if(G1.length!=G2.length)return false;//���� �������� ����
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
		if(sum1!=sum2)return false;//���� ������ ���� �� ���� ������ ����
		Arrays.sort(arr1);
		Arrays.sort(arr2);//������ ����� ����� ����
		for(int i=0;i<n;i++){
			if(arr1[i]!=arr2[i])
				return false;
		}
		//int [] c1 = fireAlgo(G1);//������ ������ �����
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
	//������ ��� ���� ���� ���� ��� ���� ���
	//�� �� ��� ��� ���=��� ��� ��� �� ������� ����� ����=�� ���� ����� �� ���� ����
	//���� ����� ���� �� ������ ������
	//��� ����� ������ ���� �� ���� ����� ��������� ���� ����
	//���� ������ ���� ��� ���� ������
}
