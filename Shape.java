 package Tetris;

import java.awt.Point;

	public class Shape {

	/**Điểm*/
	 private Point location;
	 private int[] key;
	 Shape(Point l)
	 {
		 location = l;
		 key = CoordinatesOfShape.getRandomKey();
	 }
	 Shape(Point l,int[] k)
	 {
		 location = l;
		 key = k;
	 }
	 
	 /**Lấy hình dạng của hình vuông hiện tại*/
	 public Point[] getShape()
	 {
		 return CoordinatesOfShape.getShapeByKey(key);
	 }
	 
	 /**thay đổi vị trí của hình dạng*/
	 public Shape getChangedShap(boolean isShapeChanged,int x,int y)
	 {
		 Point l = new Point(this.location.x + x,this.location.y + y);
		 int[] k;	
		 if(isShapeChanged) {
			 k = CoordinatesOfShape.getNextKey(this.key);
		 }			 
		 else {
			 k = new int[]{this.key[0],this.key[1]};
		 }
			 
		 return new Shape(l,k);
	 }
	 
	 /** Gán tọa độ shape[0] vào hôp game view => Return shape*/
	 public Point[] getPaintLocation()
	 {
		 Point[] s = CoordinatesOfShape.getShapeByKey(key);
		 Point[] p = new Point[s.length];
		 for(int i=0;i<s.length;i++)
			 p[i] = new Point(s[i].x+location.x,s[i].y+location.y);
		 return p;
	 }
}
