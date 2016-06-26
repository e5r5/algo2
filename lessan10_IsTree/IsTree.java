package lessan10_IsTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class IsTree {
	//���� ���� ����� ���� ����� �� �� �� �� ��� ����

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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [] arr = {1, 9, 2, 1, 4, 1, 1, 3 };
		ArrayList<Integer> [] tree = GetTree(arr);
//		for(int i=0;i<tree.length;i++){
//			System.out.print(i + " :");
//			System.out.print(tree[i].toString());
//			System.out.println();
//		}
System.out.println(isGraph(arr));	
	}

}
