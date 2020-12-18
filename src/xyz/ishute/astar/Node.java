package xyz.ishute.astar;

import java.util.Vector;

public class Node
{
	static int size=25;
	private Node parentNode=null;
	private int F=0;
	private int G=0;
	private int H=0;
	private int x=0;
	private int y=0;
	
	public Node(int x,int y)
	{
		// TODO Auto-generated constructor stub
		this.setX(x);
		this.setY(y);
	}
	
	public static int getDistance(Node preNode,Node nextNode)
	{
		int xLength=(preNode.getX()-nextNode.getX())/Node.size;
		int yLength=(preNode.getY()-nextNode.getY())/Node.size;
		if(xLength<0)
			xLength=-xLength;
		if(yLength<0)
			yLength=-yLength;
		return xLength+yLength;
	}
	
	public static Node getNode(Node node,Vector<Node> vector)
	{
		
		for(int i=0;i<vector.size();i++)
		{
			if(node.equals(vector.get(i)))
			{
				return vector.get(i);
			}
		}
		return null;
	}
	
	public static Node getNode(int x,int y,Vector<Node> vector)
	{
		Node node=new Node(x, y);
		return getNode(node,vector);
	}
	public boolean equals(Node node)
	{
		if(this.getX()>=node.getX()&&this.getX()<node.getX()+Node.size&&this.getY()>=node.getY()&&this.getY()<node.getY()+Node.size)
		{
			return true;
		}
		return false;
	}
	public Node getParentNode()
	{
		return parentNode;
	}

	public void setParentNode(Node parentNode)
	{
		this.parentNode = parentNode;
	}

	public int getF()
	{
		return F;
	}

	public void setF(int f)
	{
		F = f;
	}

	public int getG()
	{
		return G;
	}

	public void setG(int g)
	{
		G = g;
	}

	public int getH()
	{
		return H;
	}

	public void setH(int h)
	{
		H = h;
	}
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
}
