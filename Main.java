
package Tetris;

import javax.swing.JOptionPane;


public class Main {

	public static void main(String[] args) {	
		Object[] options ={ "Đơn", "Đôi","Bảng xếp hạng"};
		int option = JOptionPane.showOptionDialog(null, "Hãy chọn chế độ trò chơi", "Tetris",JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,null);
		if(option == 0)
		{
			try {
				try {
					SingleGameFrame player = new SingleGameFrame();
					player.setVisible(true);
				}catch(Exception e ){
					String a[] = null;
					Main.main(a);
				}
			}catch (Exception e1){
				e1.printStackTrace();
			}
		}
		else if(option == 1){ 
			try {
				doubleGame();
			}catch (Exception e1){
				e1.printStackTrace();
			}
		}
		else if(option == 2){
			try {
				Ranking();
			}catch (Exception e1){
				e1.printStackTrace();
			}
        }else {
        	System.exit(0);
        }
	}

	private static void doubleGame()
	{ 
		try {
			DoubleGameFrame player1 = new DoubleGameFrame();
			player1.setVisible(true);
		}catch(Exception e){
			String a[] = null;
			Main.main(a);
		}
		
	}
	private static void Ranking()
	{
		try {
			RankingView rank = new RankingView();
			rank.setLocationRelativeTo(null);
		}catch(Exception e ){			
			String a[] = null;
			Main.main(a);
			
		}
	}
	
	
}

