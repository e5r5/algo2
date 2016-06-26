package lessan7DFS;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class DFS {
	int n;
	final int white =0,black=2,gray=1;	
	ArrayList<Integer> [] G  ;
	int [] color;
	int [] first;
	int [] last;
	int [] pre;
	int time=0;
	boolean Circel = false;

	public DFS(ArrayList<Integer> [] G1 ){
		this.G = G1;
		this.n = G.length;
		this.color = new int [n];
		this.first = new int [n];
		this.last = new int [n];
		this.pre = new int [n];
		Arrays.fill(pre, -1);
		dfs();
	}

	private void dfs() {
		for(int i=0;i<n;i++){//��� ��������� ���� ����� �� ���������
			if(color[i]==white){
				recorsyaDFS(i);
			}
		}

	}
	//���� ���������
	private void recorsyaDFS(int v) {
		first[v] = ++time;//����� �� ������ �����
		color[v]=gray;//���� �� ���� �� ������� ������� ����
		for(int u : G[v]){//����� �� ������ �� ��� ��� ������� ��
			//�� ��� ���� �� ���� ������ ������ ���� ��� ���! ��� ����� ���� ����� ���� �� ��� �� �� �� ���� ���
			if(color[u]==gray && pre[v]!=u)
				Circel = true;

			if(color[u]==white){
				pre[u]=v;
				recorsyaDFS(u);
			}
		}
		last[v]=++time;//������ ����� �� ��� �� ����� �� ������ ������
		color[v]=black;//������ ����� �� ��� �� ���� �� �� ���� �����
	}

	public static String GetCircel(){
		
	}
	public boolean IsCircelInG(){
		return Circel;
	}
	public static void main(String[] args) {

	}

}
