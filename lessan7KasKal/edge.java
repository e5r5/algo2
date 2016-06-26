
package lessan7KasKal;

public class edge {
int indexA;
int indexB;

int wight;
public edge(int indexA,int indexB, int wight) {
	super();
	this.indexA = indexA;
	this.indexB=indexB;
	this.wight = wight;
}
@Override
public String toString() {
	return "edge [indexA=" + indexA + ", indexB=" + indexB + ", wight=" + wight
			+ "]";
}





}
