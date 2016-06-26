package lessan1_2BatalPro;

public class Bottle {
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

	public static void main(String[] args) {
		Bottle b = new Bottle(2, 1);
		b.printMatrix();
		System.out.println(b.index(0, 4));
		System.out.println(b.get_on_this_pasation(0,2));

	}
}
