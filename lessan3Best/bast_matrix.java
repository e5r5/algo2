package lessan3Best;

import java.awt.Point;

public class bast_matrix {
	int [][] mat;
	Point p1, p2;
	int n,m;
	public bast_matrix(int [][] mat1){
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
	public static void bast_matrix_beterWithBlackPoint(int [][] matrix, Point p1Black,Point p2Black){//O(n^4)
		
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
	public static int HhlaAndHdaha(int[][] h, int i, int j, int p, int q) {
		if(i==0 && j==0)
			return h[p][q];
		else if(i==0 && j>0)
			return h[p][q] - h[p][j-1];
		else if(i>0 && j==0)
			return h[p][q] - h[i-1][q];
		else 
			return h[p][q] -  h[i-1][q]- h[p][j-1] + h[i-1][j-1];
	}

	
	public static void printMatrix(int [][] mat){
		System.out.println();
		for(int i=0;i<mat.length;i++){
			System.out.println();
			for(int j=0;j<mat[0].length;j++){
				System.out.print(mat[i][j] + "  " );
			}
		}
		System.out.println();
	}
	public static void main(String[] args) {
		int mat[][] = { {2,10,8,3},
					   {-8,14,-1,4},
				   	   {-6,-1,8,-2},
				    	{1,  8,7, 3},
					{8,2,-10,-8}};
		bast_matrix a = new bast_matrix(mat);
		bast_matrix b = new bast_matrix(mat);
		bast_matrix c = new bast_matrix(mat);
		bast_matrix d = new bast_matrix(mat);
		a.bast_matrix_full();
		b.bast_matrix_beter();
	//	c.bast_of_the_best_matrix();
		System.out.println();
		System.out.println("bast_matrix_beterWithBlackPoint:");	
		bast_matrix_beterWithBlackPoint(mat,new Point(0, 1),new Point(4, 1));

	}

}
