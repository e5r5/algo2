package lessan2FW;

public class FW {
	//אלגוריתם פרוייד וורשל
	public static void fw(boolean [][] mat){//מטריצת שכנויות
		int n = mat.length;
		for(int k=0;k<n;k++){
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					mat[i][j] = (mat[i][j]) || (mat[i][k]&&mat[k][j]);
				}
			}
		}
	}
	//האם הגרף קשיר כלומר אפששר להגיע לכל נק מכל נק
	public static boolean isCashir(boolean [][] mat){
		for(int i=0;i<mat.length;i++){
			for(int j=0;j<mat[0].length;j++){
				if(!mat[i][j]) return false;
			}
		}
		return true;
	}
	//מחזיר את מספר רכיבי הקשירות בגרף
//	public static int numberParts(boolean [][] mat){
//		fw(mat);
//		if(isCashir(mat))return 1;
//		printMat(mat);
//		int nc =0;
//
//		int [] c = new int [mat.length];
//		for(int i=1;i<mat.length;i++){
//			if(c[i]==0)
//				c[i] = nc++;
//			for(int j=0;j<mat[0].length;j++){
//				if(c[i]==0&& mat[i][j])
//					c[j]=nc;
//			}
//		}
//		return nc;
//	}
	public static int parts_of_matrix(boolean [][] mat){
		fw(mat);
		if(isCashir(mat))return 1;
		int n = mat.length;
		int [] arr = new int [n];
		int count = 0;
		arr[0] =0;
		boolean flag = false;
		for (int i=1;i<n;i++){
			if(mat[0][i]){
				arr[i]=count;
			}
			else{
				flag = true;
				arr[i]=0;
			}
		}

		if(flag){
			for(int i=1;i<n;i++){
				if(arr[i]==0){
					count++;
				}
				for(int j=0;j<n;j++){
					if(arr[j]==0 && mat[i][j]){
						arr[j]=count;
					}
				}
			}
		}

		return count;

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
	//אחרי הפעלת האלגו אם יש מספר שלילי באלכסון יש מעגל שלילי בגרף
	public static boolean isNegCilcel(double [][] mat){
		fwPrice(mat);
		for(int i=0;i<mat.length;i++){
			if(mat[i][i] <0)
				return true;}
		return false;
	}
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

	public static void printMat(boolean[][] mat){
		System.out.println();
		for(int i=0;i<mat.length;i++){
			System.out.println();
			for(int j=0;j<mat[0].length;j++){
				System.out.print(mat[i][j] + "  ");
			}
		}
		System.out.println();
	}
	public static void printMat2(double[][] mat){
		System.out.println();
		for(int i=0;i<mat.length;i++){
			System.out.println();
			for(int j=0;j<mat[0].length;j++){
				System.out.print(mat[i][j] + "  ");
			}
		}
		System.out.println();
	}
	public static void main(String[] args) {
		boolean [][] G2  = { {true,true,true,false},
				{true,true,false,true},
				{true,false,true ,true},
				{false,true,true,true}

		};
		boolean [][] G  = { {true,true,true,false},
						{true,true,true,false},
						{true,true,true ,false},
						{false,false,false,true}

		};
		double [] price={1,2,3,4};
		double [][] mat = new double [G.length][G[0].length];
		printMat2(distFromVtoE(G,mat,price));

		
		System.out.println(parts_of_matrix(G));
	}

}
