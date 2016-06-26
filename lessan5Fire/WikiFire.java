package lessan5Fire;

import java.util.Vector;

public class WikiFire 
{
	// data items	
	int numOfCenters, radius, diameter;
	int [] centers;
	int numOfVertexes;
	Vector<Integer> leaves;
	int[] degrees;
	Vector<Integer>[] tree;//graph (tree)
 
	// constructor
	public WikiFire()
	{
		initTree();
		numOfVertexes = tree.length;
		radius = 1;
		diameter = 0;
		numOfCenters = 0;
		// array of leaves and array of degrees initialization
		// assumption number of leaves > 2
		leaves = new Vector<Integer>(numOfVertexes);
		degrees = new int[numOfVertexes];
		for (int i = 0; i<numOfVertexes; i++)
		{
			degrees[i] = tree[i].size();
			if (degrees[i] == 1)
			{
				leaves.add(i);
			}
		}
	}
 
	public int getRadius()
	{
		if (numOfCenters == 2)
		{
			radius = (diameter+1)/2;
		}
		else
		{
		radius = diameter/2;
		}
		return radius;
	}
 
	public int getNumOfCentres()
	{
		return numOfCenters;
	}
 
	public int getDiameter()
	{
		if (numOfCenters == 2)
		{
			diameter = radius*2 - 1;
		}
		else
		{
			diameter = radius*2;
		}
		return diameter;
	}
 
	public void printNumOfCentres()
	{
		System.out.print("Centers: ");
		for (int i=0; i<leaves.size(); i++)
		{
			System.out.print(leaves.get(i)+", ");
		}
		System.out.println();
	}
 
	public void findCsRD()
	{
		int number = numOfVertexes;
		int index = 0;
		while (number > 2)
		{
			Integer leaf = leaves.get(index);
			int vertex = tree[leaf].get(0);
			tree[vertex].remove(leaf);
			degrees[vertex]--;
			if (degrees[vertex] == 1)
			{
				leaves.set(index, vertex); 
				index++;
				if (index == leaves.size())
				{
					index = 0;
					radius++;
				}
				//diameter++;
			}
			else
			{
				leaves.remove(index);
			}
			number--;
			if (number==2 && index==1){
				leaf = leaves.get(index);
				vertex = tree[leaf].get(0);
				leaves.remove(index);
				tree[vertex].remove(leaf);
				degrees[vertex]--;
			}
		}
		numOfCenters = leaves.size();
	}
 
	///// main
	public static void main(String[] args) 
	{
		WikiFire cr = new WikiFire();
		cr.findCsRD();
		System.out.println("diameter: "+cr.getDiameter());
		System.out.println("radius: "+cr.getRadius());
		System.out.println("number of centres: "+cr.getNumOfCentres());
		cr.printNumOfCentres();
		System.out.println();
	}
 
	public void initTree()
	{
		numOfVertexes = 7;
		tree = new Vector[numOfVertexes];
		for (int i=0; i<numOfVertexes; i++)
		{
			tree[i] = new Vector<Integer>();
		}
		tree[0].add(1);//1->2
		////////////
		tree[1].add(0);//2->1
		tree[1].add(2);//2->3
		tree[1].add(4);//2->5
		///////////
		tree[2].add(1);//3->2
		tree[2].add(3);//3->4
		/////////////
		tree[3].add(2);//4->3
		////////////
		tree[4].add(1);//5->2
		tree[4].add(5);//5->6
		////////////
		tree[5].add(4);//6->5
		tree[5].add(6);//6->7
		////////////
		tree[6].add(5);//7->6
	}
 
}