package lessan4BestIn3D;

public class Best3D { //O(n^5) or full in O(n^9)
	int [][][] mat;
	int [][][] H;
	int k,n,m;
	int [] cor = new int [6];
	
	
	public Best3D(int [][][] mat){
		this.mat = mat;
		k = mat.length;
		n = mat[0].length;
		m = mat[0][0].length;
		H = new int [k][n][m];
		buildH();
	}
	private void buildH() {
		H[0][0][0] = mat[0][0][0];
		for(int i=1;i<n;i++)
			H[0][i][0] = H[0][i-1][0] + mat[0][i][0];
		for(int j=1;j<m;j++)
			H[0][0][j] = H[0][0][j-1] + mat[0][0][j];
		for(int kk=1;kk<k;kk++)
			H[kk][0][0] = H[kk-1][0][0] + mat[kk][0][0];
		//
		for(int kk=0;kk<k;kk++){
			for(int i=1;i<n;i++){
				for(int j=1;j<m;j++){
					H[kk][i][j] = H[kk][i-1][j] + H[kk][i][j-1] + mat[kk][i][j] - H[kk][i-1][j-1];
				}
			}
		}
	}
	public int max_beat(){
		int max=0;
		for(int is=0;is<n;is++){
			for(int js=0;js<m;js++){
				for(int ie=0;ie<n;ie++){
					for(int je=0;je<m;je++){
						int [] temp = new int [k];
						for(int kk=0;kk<temp.length;kk++){
							temp[kk] = sum(kk,is,js,ie,je);
						}
						int t = Best(temp);
						if(t>max){
							max = t;
//							cor[0] =is;
//							cor[1]=js;
//							cor[2]=ie;
//							cor[3]=je;
						}
					}
				}
			}
		}
//		System.out.println( "start:"+ cor[0]+ ", " + cor[1]);
//		System.out.println( "end:"+ cor[2]+ ", " + cor[3]);
		return max;
	}
	public int sum_full_s(int ks,int ke,int is,int js,int ie,int je){
		int sum=0;
		for(int k1=ks;k1<=ke;k1++){
			for(int i=is;i<=ie;i++){
				for(int j=js;j<=je;j++){
					sum = sum+mat[k1][i][j];
				}
			}
		}
		return sum;
	}
	public int full(){
		int max = Integer.MIN_VALUE;
		for(int ks=0;ks<k;ks++){
			for(int is=0;is<n;is++){
				for(int js=0;js<m;js++){
					for(int ke=ks;ke<k;ke++){
						for(int ie=is;ie<n;ie++){
							for(int je=js;je<m;je++){
								int t = sum_full_s(ks,ke,is,js,ie,je);
								if(max<t){
									max=t;
									cor[0] =is;
									cor[1]=js;
									cor[2]=ie;
									cor[3]=je;
									cor[4]=ks;
									cor[5]=ke;
								}
							}
						}
					}
				}
			}
		}
		System.out.println( "start:"+ cor[0]+ ", " + cor[1]);
		System.out.println( "end:"+ cor[2]+ ", " + cor[3]);
		System.out.println( "hight:"+ cor[4]+ ", " + cor[5]);
		return max;
	}
	public static int Best(int[] arr) {
		int max = Integer.MIN_VALUE;
		int start = -1;
		for(int i=0;i<arr.length;i++){
			if(arr[i]>0){
				start = i;
				break;
			}
		}
		if(start ==-1){
			for(int i=0;i<arr.length;i++){
				if(arr[i]>max)
					max=arr[i];
			}
			return max;
		}
		else{
			int sum=0;
			for(int i=start;i<arr.length;i++){
				sum = sum + arr[i];
				if(sum<0){
					sum=0;
					continue;
				}
				else if(max<sum){
					max=sum;
				}
			}
		}
		return max;
	}
	private int sum(int kk, int i, int j, int p, int q) {
		if(i==0 && j==0)
			return H[kk][p][q];
		else if(i==0 && j>0)
			return H[kk][p][q] - H[kk][p][j-1];
		else if(i>0 && j==0)
			return H[kk][p][q] - H[kk][i-1][q];
		else 
			return H[kk][p][q] -  H[kk][i-1][q]- H[kk][p][j-1] + H[kk][i-1][j-1];
	}
public void print_ne(){
	for(int k1=cor[4];k1<=cor[5];k1++){
		for(int i=cor[0];i<=cor[2];i++){
			for(int j=cor[1];j<=cor[3];j++){
				System.out.print(" " + mat[k1][i][j]);
			}
		}
	}
}
	public static void main(String[] args) {
		int [][] mat1 = {{1,-2,-3},
						{-4,2,1},
						{0,-4,3}
		};
		int [][] mat2 = {{-1,2,3},
						{-4,2,1},
						{0,-4,-3}
		};
		int [][] mat3 = {{-1,2,-3},
						{-4,2,1},
						{0,-4,3}
		};
		int [][][] mat = {mat1,mat2,mat3};
		Best3D b = new Best3D(mat);
		Best3D c = new Best3D(mat);
		System.out.println(c.max_beat());
		System.out.println("full " + b.full());
		b.print_ne();
	}

}
