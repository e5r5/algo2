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
				//איזה צעדים אפשריים ממצב מסויים של i,j?
				h[index][index(i,0)] = true;//למלא קצת את השמאלי ולרוקן את הימיני
				h[index][index(0,j)] = true;//מלא קצת את הימיני ולרוקן את השמאלי
				h[index][index(n,j)] = true;//למלא לגמרי את השמאלי ויש משהו בימיני
				h[index][index(i,m)] = true;//למלא לגמרי  את הימיני ויש משהו בשמאלי
				h[index][index(Math.max(0,i+j-m),Math.min(m, i+j))] = true;//אסור שיהיה מינוס מים ואסור שיהיה יותר מהקיבולת
				h[index][index(Math.min(i+j, n),Math.max(0,i+j-n))] = true;//סמטרי לשפוך מאם ןלאן 
				path[i][j] = "";
			}
		}
	}
	private void buildPath() {
		for(int i=0;i<n+1;i++){
			for(int j=0;j<m+1;j++){
				int index = index(i,j);
				//איזה צעדים אפשריים ממצב מסויים של i,j?
				path[index][index(i,0)] = "("+i+","+j+")->"+"("+i+","+0+");";//למלא את השמאלי ולרוקן את הימיני
				path[index][index(0,j)] ="("+i+","+j+")->"+"("+0+","+j+");";;//מלא את הימיני ולרוקן את השמאלי
				path[index][index(n,j)] = "("+i+","+j+")->"+"("+n+","+j+");";//למלא את השמאלי ויש משהו בימיני
				path[index][index(i,m)] = "("+i+","+j+")->"+"("+i+","+m+");";;//למלא את השמאלי ויש משהו בימיני
				int a =index(Math.max(0,i+j-m),Math.min(m, i+j));
				path[index][a] = "("+i+","+j+")->"+"("+iIndex(a)+","+jIndex(a)+");";//אסור שיהיה מינוס מים ואסור שיהיה יותר מהקיבולת
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

	//הופך מיקום במטריצה למיקום במערך חד מימדי שהמיקום במערך חד מימדי זה כל האפשרויות של מילוי הבקבוקים
	public int index(int i,int j){	
		return (m+1)*i+j; //מספר העמודות פלוס אחד כפול המיקום המיקום של השורות פלוס עמודות
		//דומה ל i*m+j
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
