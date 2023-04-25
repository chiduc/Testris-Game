package Tetris;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.*;

public class GameView extends JPanel {
	/**
	 * Bảng điều khiển chính để chơi trò chơi
	 */
	private static final long serialVersionUID = 2L;
	public Clip clip;
	
	private JLabel[][] box;
	private Shape movingShape;
	private LinkedList<Shape> nextshape;
	final Random rand = new Random();
	GameView(int x,int y)
	{
		 super();
		 this.setLayout(new GridLayout(x,y));
		 box = new JLabel[x][y];
		 for(int i=0;i<x;i++)
			 for(int j=0;j<y;j++)
			 {
				 box[i][j] = new JLabel();
				 box[i][j].setPreferredSize(new Dimension(40,40));
				 Color c = new Color(rand.nextInt(256));
				 box[i][j].setBackground(c);
				 this.add(box[i][j]);
			 }
		 nextshape = new LinkedList<Shape>();
	}

	/**trả về số dòng đã xóa*/
	public int removeRow()
	{
		movingShape = null;
		int count=0;
		for(int i = box.length-1 ; i >= 0 ; i--)
		{
			int j;
			//isOpaque return true, thành phần đảm bảo tô màu không mờ cho mọi pixel trong khu vực của nó.
			for(j = 0 ; j < box[0].length ; j++)			
			{
				if(!box[i][j].isOpaque())		
					break;	
			}
			if(j == box[0].length)
			{	
				count++;
				for(int k = i ; k > 0 ; k--)
				{
					for(int L = 0 ; L < box[0].length ; L++)						
					{
						//setOpaque = sơn màu 
						box[k][L].setOpaque(box[k-1][L].isOpaque());
					}
				} i++;
			 }		 	
		} return count;
	}
	

	public Shape creatNextUnit()
	{
		if(nextshape.isEmpty())
		{
			nextshape.add(new Shape(new Point(0,box[0].length/2-1)));
		}
		nextshape.add(new Shape(new Point(0,box[0].length/2-1)));
		return nextshape.getLast();
	}
	
	//** Lấy khối hộp tiếp theo */
	public boolean getNextUnit()
	{
		return paintUnit(nextshape.poll());
		//poll Truy xuất và xóa phần đầu (phần tử đầu tiên) của danh sách này
	}
	
	/**Cố gắng di chuyển hoặc biến dạng khối theo giá trị k đã cho, nếu khối rơi xuống dưới cùng, trả về false, nếu không thì trả về true*/
	synchronized boolean moveUnit(int k) // synchronized dong bo hoa
	{
		if(movingShape==null)
			return true;
		switch(k)
		{
		case 0:
			if(!paintUnit(movingShape.getChangedShap(true, 0, 0))) 
				if(!paintUnit(movingShape.getChangedShap(true, 0, -1))) 
					if(!paintUnit(movingShape.getChangedShap(true, 0, -2))) 
						if(!paintUnit(movingShape.getChangedShap(true, -2, -3)));
			
		break;
		case 1:
			paintUnit(movingShape.getChangedShap(false, 0, -1));
			break;
		case 2:
			paintUnit(movingShape.getChangedShap(false, 0, 1));
			break;
		case 3:
			return paintUnit(movingShape.getChangedShap(false, 1, 0));
		case 4:
			if(!paintUnit(movingShape.getChangedShap(false, 13, 0)))
				if(!paintUnit(movingShape.getChangedShap(false, 12, 0)))
					if(!paintUnit(movingShape.getChangedShap(false, 11, 0)))
						if(!paintUnit(movingShape.getChangedShap(false, 10, 0)))
							if(!paintUnit(movingShape.getChangedShap(false, 9, 0)))
								if(!paintUnit(movingShape.getChangedShap(false, 8, 0)))
									if(!paintUnit(movingShape.getChangedShap(false, 7, 0)))
										if(!paintUnit(movingShape.getChangedShap(false, 6, 0)))
											if(!paintUnit(movingShape.getChangedShap(false, 5, 0)))
												if(!paintUnit(movingShape.getChangedShap(false, 4, 0)))
													if(!paintUnit(movingShape.getChangedShap(false, 3, 0)))
														if(!paintUnit(movingShape.getChangedShap(false, 2, 0)))
															if(!paintUnit(movingShape.getChangedShap(false, 1, 0)));
		}
		return true;
	}	
	/**Cố gắng vẽ hộp với đơn vị đã cho, nếu nó chạm vào đường viền hoặc các hộp khác không thể vẽ được, hãy trả về false*/
	public boolean paintUnit(Shape shape)
	{
		List<Point> add = new ArrayList<Point>();
		for(Point n : shape.getPaintLocation()) 
		{
			add.add(n);
		}			
		if(movingShape!=null)
		{
			for(Point b : movingShape.getPaintLocation())
			{
				add.remove(b);
			}
		}				
		for(Point u : add) 
		{
			if(u.x < 0 || u.x >= box.length || u.y < 0 || u.y >= box[0].length || box[u.x][u.y].isOpaque())//isOpaque kiem tra dc to mau ko
				{
					return false;
				}
		}		
		//setOpaque = sơn màu 
		if(movingShape!=null)
		{
			for(Point u : movingShape.getPaintLocation())
			{
				box[u.x][u.y].setOpaque(false);
			}
		}				
		movingShape = shape;
		for(Point u : movingShape.getPaintLocation())
		{
			box[u.x][u.y].setOpaque(true);
		}
		this.updateUI();//Đặt lại thuộc tính giao diện người dùng với một giá trị từ giao diện hiện tại.
		return true;
	}
	
	/** thiết lập lại trò chơi mới*/
	public void resetGame()
	{
		for(int i=0;i<box.length;i++)
			 for(int j=0;j<box[0].length;j++)
					 box[i][j].setOpaque(false);
		movingShape = null;
		nextshape.clear();
	}
	
	//Tàn cuộc
	public void endGame()
	{
		movingShape = null;
	}

}
