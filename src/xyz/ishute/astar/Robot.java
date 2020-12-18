package xyz.ishute.astar;

import java.util.Vector;

public class Robot implements Runnable
{
	private int x=0;
	private int y=0;
	private Vector<Node> openVector=null;
	private Vector<Node> closeVector=null;
	Vector<Node> allVector=null;
	Vector<Node> wallVector=null;
	private Node myNode=null;
	static Node finalNode=null;
	Node endNode=null;
	static Vector<Node> prooVector=null;
	
	public Robot(Node startNode,Node endNode,Vector<Node> allVector,Vector<Node> wallVector)
	{
		this.allVector=allVector;
		this.wallVector=wallVector;
		this.myNode=startNode;
		myNode.setH(Node.getDistance(myNode, endNode));
		myNode.setG(0);
		myNode.setF(myNode.getH()+myNode.getG());
		
		
		this.openVector=new Vector<Node>();
		this.closeVector=new Vector<Node>();
		this.endNode=endNode;
		Thread thread=new Thread(this);
		thread.start();
	}
	
	private Node findWay(Node endNode)
	{
		openVector.add(myNode);
		while(openVector.size()>0)
		{
			Node currentNode=findMinNode(openVector);
			openVector.remove(currentNode);
			closeVector.add(currentNode);
			
			Vector<Node> neighborsVector=findNeighbors(currentNode);
			if(neighborsVector==null) continue;
			for(Node node:neighborsVector)
			{
				if(!openVector.contains(node))
				{
					node.setParentNode(currentNode);
					openVector.add(node);
				}
			}
			if(openVector.contains(endNode))
			{
				return endNode;
			}
		}
		return null;
	}
	
	public Node findMinNode(Vector<Node> vector)
	{
		Node minNode=vector.get(0);
		for(int i=0;i<vector.size();i++)
		{
			Node node=vector.get(i);
			if(minNode.getF()>node.getF())
			{
				minNode=node;
			}
		}
		return minNode;
	}
	
	public Vector<Node> findNeighbors(Node currentNode)
	{
		Vector<Node> neighborsVector=new Vector<Node>();
		Node node1=Node.getNode(new Node(currentNode.getX()-Node.size, currentNode.getY()), allVector);
		Node node2=Node.getNode(new Node(currentNode.getX()+Node.size, currentNode.getY()), allVector);
		Node node3=Node.getNode(new Node(currentNode.getX(),currentNode.getY()+Node.size), allVector);
		Node node4=Node.getNode(new Node(currentNode.getX(),currentNode.getY()-Node.size), allVector);
		if(node1!=null&&allVector.contains(node1)&&!wallVector.contains(node1)&&!closeVector.contains(node1))
		{
			neighborsVector.add(node1);
		}
		if(node2!=null&&allVector.contains(node2)&&!wallVector.contains(node2)&&!closeVector.contains(node2))
		{
			neighborsVector.add(node2);
		}
		if(node3!=null&&allVector.contains(node3)&&!wallVector.contains(node3)&&!closeVector.contains(node3))
		{
			neighborsVector.add(node3);
		}
		if(node4!=null&&allVector.contains(node4)&&!wallVector.contains(node4)&&!closeVector.contains(node4))
		{
			neighborsVector.add(node4);
		}
		for(Node node:neighborsVector)
		{
			node.setH(Node.getDistance(node, endNode));
			node.setG(currentNode.getG()+1);
			node.setF(node.getH()+node.getG());
			//System.out.println("neighbor:("+node.getX()+","+node.getY()+")");
		}
		if(neighborsVector.size()==0) return null;
		return neighborsVector;
	}
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		finalNode=findWay(endNode);
		while(true)
		{
			try
			{
				
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
