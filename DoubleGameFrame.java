package Tetris;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class DoubleGameFrame extends JFrame {

	private static final long serialVersionUID = 4L;

	protected GameView game, game1;
	protected WatchView watch, watch1, watch2;
	protected Timer downMoveTimer;
	private int speed, stop = 0;
	private String mid, play, hai;
	private boolean isPaused;
	private Clip clip;

	public DoubleGameFrame() throws Exception {
		getContentPane().setLayout(new FlowLayout());
		game = new GameView(15, 10);
		game1 = new GameView(15, 10);

		game.setBackground(new Color(180, 180, 180));
		game1.setBackground(new Color(180, 180, 180));

		watch = new WatchView(play, hai);
		watch1 = new WatchView(play, 1);
		watch2 = new WatchView(mid);

		watch.setBackground(SystemColor.info);
		watch1.setBackground(SystemColor.info);

		getContentPane().add(watch1);
		getContentPane().add(game1);

		getContentPane().add(watch2);

		getContentPane().add(game);
		getContentPane().add(watch);

		this.pack(); // dieu chinh kich thuoc phu hop
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to exit ?", "Notification",
							dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION) {
						try {
							setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							setVisible(false);
							if (watch2.clip != null)
								watch2.clip.stop();
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
				if (!game1.moveUnit(3)) {
					try {
						updateData1();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		addListener();
		isPaused = false;
	}

	/**
	 * Được kích hoạt khi khối rơi xuống đáy, cập nhật nhiều dữ liệu khác nhau và
	 * tạo khối mới để rơi xuống
	 */
	protected int updateData() throws Exception {
		int c = game.removeRow();
		watch.updateDataWatch(c, game.creatNextUnit());
		this.speed = (int) (1000 * Math.pow(0.75, watch.getLevel() - 1));
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
			stop();
		}
		return c;
	}

	protected int updateData1() throws Exception {
		int c1 = game1.removeRow();
		watch1.updateDataWatch(c1, game1.creatNextUnit());
		this.speed = (int) (1000 * Math.pow(0.75, watch.getLevel() - 1));

		if (game1.getNextUnit())
			downMoveTimer.setDelay(speed);
		else {
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("C:\\SoundGame\\hihi.wav")));
				clip.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			stop1();
		}
		return c1;
	}

	public void stop() {
		stop++;
		try {
			testStopGame();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void stop1() {
		stop++;
		try {
			testStopGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testStopGame() throws Exception {
		if (stop == 2) {
			if (watch2.clip != null)
				watch2.clip.stop();
			stopGame();
		}

		return;
	}

	protected void stopGame() throws Exception {
		if (watch.score > watch1.score) {
			JOptionPane.showMessageDialog(null, "Người Chiến Thắng Là Player2 với số điểm : " + watch.score, "Tetris",
					JOptionPane.ERROR_MESSAGE);
		} else if (watch.score == watch1.score) {
			JOptionPane.showMessageDialog(null, "Kết quả hòa nhau với số điểm : " + watch.score, "Tetris",
					JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Người Chiến Thắng Là Player1 với số điểm : " + watch1.score, "Tetris",
					JOptionPane.ERROR_MESSAGE);
		}
		this.setVisible(false);
		String a[] = null;
		Main.main(a);
	}

	/** tạm dừng trò chơi */
	protected void pauseGame() {
		downMoveTimer.stop();
		isPaused = true;
	}

	/** Tiếp tục trò chơi hoặc bắt đầu một trò chơi mới */
	protected void restartGame() throws Exception {
		if (!isPaused)
			resetGame();
		else
			isPaused = false;

		downMoveTimer.start();
	}

	/** khởi động lại trò chơi mới */
	protected void resetGame() throws Exception {
		isPaused = false;

		game.resetGame();
		game1.resetGame();

		watch.resetData();
		watch1.resetData();

		updateData();
		updateData1();
	}

	private void addListener() {
		watch2.addStartListener(new MouseListener() {
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
			public void keyPressed(KeyEvent arg) {
				if (arg.getKeyCode() == KeyEvent.VK_LEFT)
					game.moveUnit(1);
				else if (arg.getKeyCode() == KeyEvent.VK_RIGHT)
					game.moveUnit(2);
				else if (arg.getKeyCode() == KeyEvent.VK_UP)
					game.moveUnit(0);
				else if (arg.getKeyCode() == KeyEvent.VK_DOWN)
					game.moveUnit(3);
				else if (arg.getKeyCode() == KeyEvent.VK_NUMPAD0)
					game.moveUnit(4);

				else if (arg.getKeyCode() == KeyEvent.VK_A)
					game1.moveUnit(1);
				else if (arg.getKeyCode() == KeyEvent.VK_D)
					game1.moveUnit(2);
				else if (arg.getKeyCode() == KeyEvent.VK_W)
					game1.moveUnit(0);
				else if (arg.getKeyCode() == KeyEvent.VK_S)
					game1.moveUnit(3);
				else if (arg.getKeyCode() == KeyEvent.VK_F)
					game1.moveUnit(4);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

		});
		watch2.addPauseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				pauseGame();
			}

			@Override
			public void mouseEntered(MouseEvent e) {

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

		});
		watch2.addSound(new KeyListener() {
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

				else if (arg0.getKeyCode() == KeyEvent.VK_A)
					game1.moveUnit(1);
				else if (arg0.getKeyCode() == KeyEvent.VK_D)
					game1.moveUnit(2);
				else if (arg0.getKeyCode() == KeyEvent.VK_W)
					game1.moveUnit(0);
				else if (arg0.getKeyCode() == KeyEvent.VK_S)
					game1.moveUnit(3);
				else if (arg0.getKeyCode() == KeyEvent.VK_F)
					game1.moveUnit(4);
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
