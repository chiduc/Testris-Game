package Tetris;

import java.awt.Point;
import java.util.Random;

public class CoordinatesOfShape {// Tọa Độ Của Hình Dạng
	private static final Point[][] a = new Point[][]
	{
		new Point[] {new Point(0,0),new Point(1,0),new Point(0,1),new Point(1,1)}
	};												 
	private static final Point[][] b = new Point[][] 
	{
		new Point[] {new Point(0,0),new Point(0,1),new Point(0,2),new Point(0,3)},
		new Point[] {new Point(0,0),new Point(1,0),new Point(2,0),new Point(3,0)}
	};
	private static final Point[][] c = new Point[][]
	{
		new Point[] {new Point(0,0),new Point(0,1),new Point(0,2),new Point(1,1)},
		new Point[] {new Point(0,1),new Point(1,1),new Point(2,1),new Point(1,0)},
		new Point[] {new Point(1,0),new Point(1,1),new Point(1,2),new Point(0,1)},
		new Point[] {new Point(0,0),new Point(1,0),new Point(2,0),new Point(1,1)}
	}; // _|_
	private static final Point[][] d = new Point[][]
	{
		new Point[] {new Point(0,0),new Point(1,0),new Point(1,1),new Point(2,1)},
		new Point[] {new Point(1,0),new Point(1,1),new Point(0,1),new Point(0,2)}
	};	
	private static final Point[][] e = new Point[][]
	{
		new Point[] {new Point(0,1),new Point(1,1),new Point(1,0),new Point(2,0)},
		new Point[] {new Point(0,0),new Point(0,1),new Point(1,1),new Point(1,2)}
	};
	private static final Point[][] f = new Point[][]
	{
		new Point[] {new Point(0,0),new Point(0,1),new Point(0,2),new Point(1,2)},
		new Point[] {new Point(0,1),new Point(1,1),new Point(2,1),new Point(2,0)},
		new Point[] {new Point(0,0),new Point(1,0),new Point(1,1),new Point(1,2)},
		new Point[] {new Point(0,1),new Point(0,0),new Point(1,0),new Point(2,0)}
	};
	private static final Point[][] g = new Point[][]
	{
		new Point[] {new Point(0,0),new Point(1,0),new Point(2,0),new Point(2,1)},
		new Point[] {new Point(0,2),new Point(0,1),new Point(0,0),new Point(1,0)},
		new Point[] {new Point(0,0),new Point(0,1),new Point(1,1),new Point(2,1)},
		new Point[] {new Point(0,2),new Point(1,2),new Point(1,1),new Point(1,0)}
	};
	
	/**danh sách tất cả các point*/
	private static final Point[][][] list = new Point[][][]{a,b,c,d,e,f,g};
	
	/**Lấy hình dạng của hộp thông qua giá trị khóa và sử dụng mảng Point để mô tả nó*/
	public static Point[] getShapeByKey(int[] key)
	{
		return list[key[0]][key[1]];
	}
	
	/**Tạo một giá trị khóa ngẫu nhiên*/
	public static int[] getRandomKey()
	{
		Random r = new Random();
		int i = r.nextInt(list.length);
		int j = r.nextInt(list[i].length);
		return new int[]{i,j};
	}
	
	/**Lấy giá trị khóa của hộp đã chuyển đổiֵ*/
	public static int[] getNextKey(int[] key)
	{
		return new int[]{key[0],(key[1]+1)%list[key[0]].length};
	}
}
