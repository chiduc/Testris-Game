package Tetris;

import java.awt.Color;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class SingleGameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	protected Main main;
	protected GameView game;
	protected WatchView watch, xr;
	protected Timer downMoveTimer;
	private int speed;
	private boolean isPaused;
	Clip clip;
	Timer timestop;
	File f;
	URI uri;
	URL url;

	SingleGameFrame() throws Exception {
		getContentPane().setLayout(new FlowLayout());
		game = new GameView(15, 10);
		game.setBackground(new Color(180, 180, 180));
		watch = new WatchView();
		watch.setBackground(SystemColor.info);
		getContentPane().add(game);
		getContentPane().add(watch);
		this.pack(); // dieu chinh kich thuoc phu hop
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to exit ?",
							"Notification", dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION) {
						try {
							setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							setVisible(false);
							if(watch.clip != null)
								watch.clip.stop();
							downMoveTimer.stop();
							String a[] = null;
							Main.main(a);

						} catch (Exception e1) {
							e1.printStackTrace();
						}

					} else {
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		setResizable(false); // khong cho ng dung thay doi kich thuoc

		setLocationRelativeTo(null);
		downMoveTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!game.moveUnit(3)) {
					try {
						updateData();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		addListener();
		isPaused = false;
	}

	/** khởi động lại trò chơi mới */
	protected void resetGame() throws Exception {
		isPaused = false;
		game.resetGame();
		watch.resetData();
		updateData();
	}

	/**
	 * Được kích hoạt khi khối rơi xuống đáy, cập nhật nhiều dữ liệu khác nhau và
	 * tạo khối mới để rơi xuống
	 */
	protected int updateData() throws Exception {

		int c = game.removeRow();
		watch.updateDataWatch(c, game.creatNextUnit());
		speed = (int) (1000 * Math.pow(0.75, watch.getLevel() - 1));
		if (game.getNextUnit())
			downMoveTimer.setDelay(speed);
		else {
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("C:\\SoundGame\\hihi.wav")));
				clip.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if(watch.clip != null)
					watch.clip.stop();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			stopGame();
		}
		return c;
	}

	/** tạm dừng trò chơi */
	protected void pauseGame() {
		downMoveTimer.stop();
		isPaused = true;
	}

	protected void continueGame() throws Exception {
		downMoveTimer.start();
	}

	/** Tiếp tục trò chơi hoặc bắt đầu một trò chơi mới */
	protected void restartGame() throws Exception {
		if (!isPaused)
			resetGame();
		else
			isPaused = false;

		downMoveTimer.start();
	}

	/** trò chơi kết thúc */
	protected void stopGame() throws Exception {
		JOptionPane.showMessageDialog(null, "điểm số cuối cùng của bạn là" + watch.score, "Tetris",
				JOptionPane.ERROR_MESSAGE);
		String inputval = JOptionPane.showInputDialog("Vui lòng nhập nickname của bạn:");
		if (inputval == null) {
			this.setVisible(false);
			String a[] = null;
			Main.main(a);
		} else {
			SingleGameFrame.StoreData(inputval, watch.score);
			this.setVisible(false);
			String a[] = null;
			Main.main(a);
		}

	}

	// Cách kết nối với cơ sở dữ liệu
	public static Connection getCon() throws ClassNotFoundException, SQLException {
		// tạo một kết nối trống
		Connection con = null;
		String DB_URL = "jdbc:sqlserver://localhost:1433;encrypt=true;databaseName=rank;user=sa;password=Chiduc123;encrypt=true;trustServerCertificate=true";

		try {
			con = DriverManager.getConnection(DB_URL);
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	// lưu trữ dữ liệu
	protected static void StoreData(String inputval, int Score) throws SQLException, ClassNotFoundException {
		// Gọi getcon() để thiết lập kết nối với cơ sở dữ liệu
		Connection connection = getCon();
		// sơ chế
		String sql = "EXEC AddPlayer ? , ?";

		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, inputval);
		ps.setInt(2, Score);
		ps.executeUpdate();
	}

	private void addListener() {
		watch.addStartListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					restartGame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

		}, new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_LEFT)
					game.moveUnit(1);
				else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT)
					game.moveUnit(2);
				else if (arg0.getKeyCode() == KeyEvent.VK_UP)
					game.moveUnit(0);
				else if (arg0.getKeyCode() == KeyEvent.VK_DOWN)
					game.moveUnit(3);
				else if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD0)
					game.moveUnit(4);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		watch.addPauseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				pauseGame();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		watch.addSound(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if (arg0.getKeyCode() == KeyEvent.VK_LEFT)
						game.moveUnit(1);
					else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT)
						game.moveUnit(2);
					else if (arg0.getKeyCode() == KeyEvent.VK_UP)
						game.moveUnit(0);
					else if (arg0.getKeyCode() == KeyEvent.VK_DOWN)
						game.moveUnit(3);
					else if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD0)
						game.moveUnit(4);
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyTyped(KeyEvent arg0) {
					// TODO Auto-generated method stub

				}


		});

	}

}
