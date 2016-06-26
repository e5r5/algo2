package lessan3Best;

import java.util.Arrays;

public class Best {

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

	///////////////////////////////////חיפוש שלם
	public static int Best_full_search(int [] arr){//O(n^3)
		//מקרה קצה שכולם שליליים
		if(Is_all_neg(arr))
			return Return_max(arr);
		//מקרה קצה שיש רק אחד חיובי
		if(Is_only_one_pos(arr))
			return Return_only_one_pos(arr);

		//שאר המקרים
		int sum = 0,x=0,y=0;
		int max =Integer.MIN_VALUE;
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr.length;j++){
				sum=0;
				for(int k=i;k<=j;k++){
					sum = sum + arr[k];
				}
				if(max<sum){
					max = sum;
					x=i;
					y=j-1;
				}
			}
		}
		System.out.println(x + "---" + y);
		return max;
	}

	//תכנות דינמי
	public static int Best_dinami(int [] arr){//O(n^2)
		int [][] mat = new int [arr.length][arr.length];
		mat[0][0] = arr[0];
		for(int i=1;i<arr.length;i++){
			mat[i][i] = arr[i];
			mat[0][i] = mat[0][i-1] + arr[i];
		}
		for(int i=1;i<arr.length;i++){
			for(int j=i;j<arr.length;j++){
				mat[i][j] =mat[i][j-1] + arr[j]; 
			}
		}

		int max = Integer.MIN_VALUE,x=0,y=0;
		for(int i=0;i<mat.length;i++){
			for(int j=i+1;j<mat[0].length;j++){
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
				tempstart=i;
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



	// int [] arr = {6,-1,-4,1,2,3,0,-7,6};
	public static int ShortestBest(int [] arr){
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
		int len=Integer.MAX_VALUE-2,templen=0;
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
			templen++;
			//אם הסכום שלילי נאפס את הסכום ואת נק ההתחלה
			if(sum<=0){
				sum=0;
				tempstart=i+1;
				templen=0;			
			}
			//אם הגדלנו את הסכום נעדכן את המקסימום
			else if(sum>=max ){
				max= sum;
				len++;
				if(templen<=len){
					start = tempstart;
					end = i;
					len = templen;
					templen=0;
				}


			}

		}
		System.out.println(start + "---" + end);
		return max;
	}
	public static int bestDPshortst(int [] arr){
		int [][] mat = new int [arr.length][arr.length];
		for(int i=0;i<arr.length;i++){
			mat[i][i] = arr[i];
		}
		for(int i=0;i<arr.length;i++){
			for(int j=i+1;j<arr.length;j++){
				mat[i][j]=mat[i][j-1] + arr[j];
			}
		}
		int max= Integer.MIN_VALUE, x=0, y=0,len=arr.length;
		for(int i=0;i<arr.length;i++){
			for(int j=i;j<arr.length;j++){
				if(max<=mat[i][j] ){
					max = mat[i][j];
					if(j-i + 1<=len){
					x=i;
					y=j;
					len =j-i+1;
					}
				}	
			}
		}
		printMatrix(mat);
		System.out.println(x + "---" + y);
		return max;
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
		int [] arr = {7,-1,-7,1,2,4,0,-7,1};
		//int [] arr = {3,0,2,-5,1,4,-10};
		//int [] arr = {3,-12,15,-10,1,4};
		//int [] arr = {-4,0,0,0,1,4,-5,0,3,2,-7,5,0,-7};
		System.out.println(" Best full search");
		System.out.println(Best_full_search(arr));
		System.out.println("DP:");
		System.out.println(Best_dinami(arr));
		System.out.println("best O(n): ");
		System.out.println(Best(arr));
		System.out.println("Best Zikli");
		System.out.println(BestZikli(arr));
		System.out.println("Shortest Best");
		System.out.println(ShortestBest(arr));
		System.out.println("Shortest Best in DP:");
		System.out.println(bestDPshortst(arr));
	}

}
