package lessan7KasKal;

public class Disjoint_Set {

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
