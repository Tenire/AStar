package xyz.ishute.astar;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;

public class AStar extends JFrame
{
	
	static int width=700;
	static int heigth=350;
	MyPanel myPanel=null;
	
	public AStar()
	{
		myPanel=new MyPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(myPanel);
		this.setSize(width+25,heigth+50);
		this.setVisible(true);
	}
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		AStar myAStar=new AStar();
	}

	
	class MyPanel extends JPanel implements Runnable,MouseListener
	{
		Vector<Node> wallVector=null;
		Vector<Node> allVector=null;
		Node startNode=null;
		Node endNode=null;
		Robot robot=null;
		boolean isShowWay=false;
		Vector<Node> wayVector=null;
		
		public MyPanel()
		{
			this.setVisible(true);
			
			// TODO Auto-generated constructor stub
			allVector=new Vector<Node>();
			wallVector=new Vector<Node>();
			
			init();
			startNode=Node.getNode(25, 50, allVector);
			endNode=Node.getNode(100, 150, allVector);
			
			Thread thread=new Thread(this);
			thread.start();
			this.addMouseListener(this);
		}
		
		public void init()
		{
			for(int i=0;i<AStar.width;i+=Node.size)
			{
				for(int j=0;j<AStar.heigth;j+=Node.size)
				{
					Node node=new Node(i, j);
					allVector.add(node);
				}
			}
			Random random=new Random();
			for(int i=0;i<allVector.size()/4;i++)
			{
				Node node=Node.getNode(random.nextInt(width), random.nextInt(heigth),allVector);
				if(wallVector.contains(node))
				{
					i--;
				}else
				{
					wallVector.add(node);
				}
			}
		}
		
		@Override
		public void paint(Graphics g)
		{
			// TODO Auto-generated method stub
			super.paint(g);
			paintNode(g);
			paintGrid(g);
		}
		
		public void paintNode(Graphics graphics)
		{
			if(Robot.finalNode!=null&&Robot.finalNode.getParentNode()!=null&&isShowWay)
			{
				wayVector=new Vector<Node>();
				paintWay(graphics, Robot.finalNode.getParentNode());
			}
			graphics.setColor(Color.BLACK);
			for(int i=0;i<wallVector.size();i++)
			{
				Node node=wallVector.get(i);
				graphics.fill3DRect(node.getX(), node.getY(), node.size, node.size, true);
			}
			graphics.setColor(Color.GREEN);
			graphics.fill3DRect(startNode.getX(), startNode.getY(), Node.size, Node.size, true);
			graphics.setColor(Color.RED);
			graphics.fill3DRect(endNode.getX(), endNode.getY(), Node.size, Node.size, true);
		}
		
		public void paintWay(Graphics graphics,Node node)
		{
			//System.out.println("way:("+node.getX()+","+node.getY()+")");
			if(node.getParentNode()!=null&&!wayVector.contains(node.getParentNode()))
			{
				wayVector.add(node);
				paintWay(graphics, node.getParentNode());
			}else
			{
				return;
			}
			graphics.setColor(Color.CYAN);
			graphics.fill3DRect(node.getX(), node.getY(), Node.size, Node.size, true);
		}
		public void paintGrid(Graphics g)
		{
			g.setColor(Color.CYAN);
			for(int i=0;i<=AStar.width;i+=Node.size)
			{
				
				g.drawLine(i, 0, i, AStar.heigth);
			}
			for(int i=0;i<=AStar.heigth;i+=Node.size)
			{
				g.drawLine(0, i, AStar.width, i);
			}
		}
		
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			while(true)
			{
				try
				{
					Thread.sleep(50);
					
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				repaint();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			// TODO Auto-generated method stub
			if(e.getButton()==MouseEvent.BUTTON1)
			{
				Node node=new Node(e.getX(),e.getY());
				node=Node.getNode(node, allVector);
				if(node==null) return;
				if(wallVector.contains(node))
				{
					wallVector.remove(node);
				}else
				{
					if(!node.equals(startNode)&&!node.equals(endNode)&&allVector.contains(node))
					{
						wallVector.add(node);
					}
				}
				if(node.equals(startNode))
				{
					isShowWay=true;
					robot=new Robot(startNode, endNode, allVector, wallVector);
				}
				if(node.equals(endNode))
				{
					isShowWay=false;
				}
			}else if(e.getButton()==MouseEvent.BUTTON2)
			{
				Node node=new Node(e.getX(),e.getY());
				node=Node.getNode(node, allVector);
				if(!wallVector.contains(node)&&allVector.contains(node))
				{
					startNode=Node.getNode(node, allVector);
				}
			}else if(e.getButton()==MouseEvent.BUTTON3)
			{
				Node node=new Node(e.getX(),e.getY());
				node=Node.getNode(node, allVector);
				if(!wallVector.contains(node)&&allVector.contains(node))
				{
					endNode=Node.getNode(node, allVector);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
		}
	}
}
