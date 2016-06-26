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
	ArrayBlockingQueue<Integer> q1 ;//����� �� ��������� �������� �� ���
	ArrayBlockingQueue<Integer> q2 ;//����� �� ���������  ������ ������ ������� �� �� ��� ����

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
		FixArr_sort();//�� �� ����� �� ������
		codes  = new String [n];
		mat = new int [2*n-1][4]; //0=sum L+R,newV,times ; 1=Left ; 2 = Right ; 3=pre
		q1 = new ArrayBlockingQueue<Integer>(n);
		q2 = new ArrayBlockingQueue<Integer>(n);
		AlgoBuildTree();
		MakeCode();
	}
	//�� ����� �� ������ ����� ����� �� �� ����� �������
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
