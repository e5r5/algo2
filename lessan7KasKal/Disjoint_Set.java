package lessan7KasKal;

public class Disjoint_Set {

	int [] pernt;
	int [] rank;
	public Disjoint_Set(int size){
		pernt = new int[size];
		rank = new int [size];
		for(int i=0;i<size;i++){//כל קודקוד הוא האבא של עצמו כברירת מחדל
			pernt[i]=i;
			rank[i]=0;
		}
			
	}
	public int find(int v){
		int p = pernt[v];
		if(p==v){//אם האבא שווה לווי אז אנחנו בשורש 
			return v;
		}
		else{
			return pernt[v]=find(p);//אחרת לא הגענו לשורש אז האבא של ווי זה פי
		}
	}
	public boolean union(int x,int y){
		int rootx = find(x);//הופכים את תת העץ שיהיה מחובר לשורש
		int rooty= find(y);
		
		if(rootx==rooty)//אם התתי עצים מחוברים באותו שורש אין צורך לאחד כי הם מאוחדים כבר
			return false;
		
		//צריך לעדכן את הדרגות לפי הדרגה הקטנה יותר,שלא יהיה עומק בעץ
		if(rank[rootx]>rank[rooty]){
			pernt[rooty]=rootx;// האבא של הקודקוד עם הדרגה הנמוכה יותר מתחבר לקודקוד עם הדרגה הגבוהה יותר
		}
		else if(rank[rootx]<rank[rooty]){
			pernt[rootx]=rooty;
		}
		else{//אם הדרגות שוות נחבר מישהו שלא משנה לשני ונעלה לו את הדרגה כי חיברנו אליו עוד תת עץ
			pernt[rootx]=rooty;
			rank[rootx]++;
		}
		return true;
	}


}
