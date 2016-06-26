package lessan1_2BatalPro;
public class BottleProblem {
 
	private final int _infinity = Integer.MAX_VALUE;
	private int _m,_n, _dim, _mat[][];
	private String _path[][];
	public BottleProblem(int m, int n){
		_m = m;  // big bottle
		_n = n;	 // small bottle
		_dim = (_m+1)*(_n+1); // matrix dimension
		_mat = new int[_dim][_dim];
		_path = new String[_dim][_dim];
		for (int i=0;i<_dim;i++){
			for (int j=0;j<_dim;j++){
				_mat[i][j]=_infinity;
				_path[i][j]="";
			}
		}	
	}	
	// index calculation; 
	// i liter in the big bottle, j liter in the small bottle
	private int index(int i,int j){	
		return (_n+1)*i + j;
	}
	// return liter in the big bottle
	private int iIndex(int index){	
		return index/(_n+1);
	}
	// return liter in the small bottle
	private int jIndex(int index){	
		return index%(_n+1);
	}
	// the matrix initialization
	private void initMatrBottle(){
		for (int i=0;i<=_m;i++)
			for (int j=0;j<=_n;j++){
				int indij = index(i,j);
				_mat[indij][index(0,j)]=1;
				_mat[indij][index(i,0)]=1;
				_mat[indij][index(i,_n)]=1;
				_mat[indij][index(_m,j)]=1;
				int indMinMax=index(Math.max(0,i+j-_n),Math.min(_n,i+j));
				_mat[indij][indMinMax]=1;
				indMinMax = index(Math.min(_m,i+j),Math.max(0,j+i-_m));
				_mat[indij][indMinMax]=1;
			}		
	}
	// the paths matrix initialization
	private void initMatrBottlePath(){
		for (int i=0;i<=_m;i++)
			for (int j=0;j<=_n;j++){
				int indij = index(i,j);
				_path[indij][index(0,j)]="("+i+","+j+")->"+"("+0+","+j+");";
				_path[indij][index(i,0)]="("+i+","+j+")->"+"("+i+","+0+");";
				_path[indij][index(i,_n)]="("+i+","+j+")->"+"("+i+","+_n+");";
				_path[indij][index(_m,j)]="("+i+","+j+")->"+"("+_m+","+j+");";
				int indMinMax=index(Math.max(0,i+j-_n),Math.min(_n,i+j));
				_path[indij][indMinMax]="("+i+","+j+")->"+"("+iIndex(indMinMax)+","+jIndex(indMinMax)+");";
				indMinMax = index(Math.min(_m,i+j),Math.max(0,j+i-_m));
				_path[indij][indMinMax]="("+i+","+j+")->"+"("+iIndex(indMinMax)+","+jIndex(indMinMax)+");";
			}
	}
	// initialization of two matrixes
	public void initTwoMatrix(){
		initMatrBottle();
		initMatrBottlePath();
	}
 
	// shortest path length calculation:
	public void shotrestDist(){
		// the shortest path length from vertex i to vertex j
		for (int k = 0; k<= _dim-1; k++){
			for (int i = 0; i<= _dim-1; i++){
				for (int j = 0; j<= _dim-1; j++){
					if (_mat[i][k]!=_infinity && _mat[k][j]!=_infinity ){
						if (_mat[i][k]+_mat[k][j] < _mat[i][j]){
							_mat[i][j] = _mat[i][k]+_mat[k][j];
							_path[i][j] = _path[i][k]+_path[k][j];
						}
					}
				}
			}
		}
	}
	// print the matrix of shortest distances
	public void printDistMatrix(String s){
		System.out.println("\n"+s);
		for (int i=0; i<_dim; i++){
			for (int j=0; j<_dim; j++){
				if (_mat[i][j] ==_infinity) System.out.print("* ");
				else System.out.print(_mat[i][j]+" ");
			}
			System.out.println();
		}		
	}
	public void printShortestPath(){
		System.out.println("\npath:");
		for (int i=0; i<_dim; i++){
			for (int j=0; j<_dim; j++){
				if(!_path[i][j].equals("")){
					String sFrom = "("+iIndex(i)+","+jIndex(i)+")";
					System.out.println(sFrom+" path: "+_path[i][j]+"; ");					
				}					
			}
		}		
	}
	public static void main(String[] args) {
		BottleProblem bp = new BottleProblem(2, 1);
		bp.initTwoMatrix();
		bp.printDistMatrix("init matrix");
		bp.shotrestDist();
		bp.printDistMatrix("shortest dist matrix");
	//	bp.printShortestPath();
	}
}