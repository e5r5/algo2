/**
 * 
 * @author  Sefi Erlih
 *
 */
package lessan7KasKal;

public class Node implements Comparable{
int vertex;
int whigt;
public Node(int vertex, int whigt) {
	super();
	this.vertex = vertex;
	this.whigt = whigt;
}
public Node(Node node) {
	this.vertex=node.vertex;
	this.whigt=node.whigt;
}
public int getVertex() {
	return vertex;
}
public void setVertex(int vertex) {
	this.vertex = vertex;
}
public int getWhigt() {
	return whigt;
}
public void setWhigt(int whigt) {
	this.whigt = whigt;
}

public boolean equals(Object obj){
	return (this.vertex==((Node)obj).vertex);
}

@Override
public String toString() {
	return "Node [vertex=" + vertex + ", whigt=" + whigt + "]";
}
@Override
public int compareTo(Object o) {
	if (this.whigt>((Node)o).whigt)
		return 1;
	else
		return -1;
}



}
