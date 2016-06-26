package lessan6BFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;


public 	class bfsNEW{
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

	public bfsNEW(ArrayList<Integer> [] G ){
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
			coler[tempV] = black;//���� �� ���� ����� 
		}
		//System.out.println(Arrays.toString(coler));
		//System.out.println(Arrays.toString(pre));
		//System.out.println(Arrays.toString(dis));
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
		boolean  bipartite = true;
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
	
	public static ArrayList<Integer>[] initGilGraph(){
		int n = 4;
		ArrayList<Integer>[] ans = new ArrayList[n];
		for(int i=0;i<n;i++){
			ans[i] = new ArrayList<Integer>();
		}
		ans[0].add(1);
		ans[1].add(0);ans[1].add(2);
		ans[2].add(1);ans[2].add(3);
		ans[3].add(0);ans[3].add(2);
		return ans;
	}
	public static void main(String[] args) {

		bfsNEW b1 = new bfsNEW(initGilGraph());
		//System.out.println(b1.GetPath(0, 2));
		System.out.println("isBipartite: " + b1.isBipartite());
		System.out.println("is Connected : " + b1.isConnected());
		System.out.println("isTree "+ b1.isTree());
		System.out.println("has circel: "+ b1.IsCircel());
		System.out.println("nember Of Connected: " + b1.GetNemberOfConnected());
	//	b1.returnAllParts();
		System.out.println("GetDimater "+ b1.GetDimater());
//		System.out.println();
//		System.out.println("lavit:");
//		BFS b = new BFS(InitGraph.initGraph6());
//		System.out.println(b.getPath(0, 2));
//		System.out.println("is bipartite? " + b.isBipartite());
//		System.out.println("is connected? " + b.isConnected());

//		System.out.println(b.getAllComponents());
	}
}
