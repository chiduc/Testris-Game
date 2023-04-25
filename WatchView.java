package Tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import java.awt.SystemColor;

public class WatchView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	private JButton start;
	private JButton pause;
	private JLabel[][] nextshape;
	private JPanel nextshapePanel;
	private JLabel s, l, tips; // Điểm số, cấp độ và hình dạng của khối tiếp theo
	private FlowLayout layout;
	private JToggleButton sound;
	public int level, score;
	public Clip clip;

	WatchView() {
		start = new JButton("bắt đầu");
		pause = new JButton("tạm ngừng");
		sound = new JToggleButton("🔊  ➖  🔇");
		s = new JLabel("Điểm : 0", JLabel.CENTER);
		l = new JLabel("mức độ :", JLabel.CENTER);
		final JTextArea instruct = new JTextArea("Hướng Dẫn Thao Tác \n" + "◀ : Sang Trái\n " + "▶ : Sang Phải\n"
				+ "🡇 : Đưa Xuống\n" + "🡅 : chuyển hướng\n" + "𝗡𝘂𝗺𝗯𝗲𝗿 𝟬 : Xuống Đáy");
		instruct.setBackground(SystemColor.info);
		instruct.setEditable(false);
		tips = new JLabel("Khối tiếp theo", JLabel.CENTER);
		tips.setPreferredSize(new Dimension(100, 50));
		s.setPreferredSize(new Dimension(100, 50));
		l.setPreferredSize(new Dimension(100, 50));
		resetData();
		nextshapePanel = new JPanel(new GridLayout(4, 4));
		nextshapePanel.setBackground(new Color(255, 248, 225));
		nextshape = new JLabel[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				nextshape[i][j] = new JLabel();
				nextshape[i][j].setPreferredSize(new Dimension(20, 20));
				nextshape[i][j].setBackground(Color.orange);
				nextshapePanel.add(nextshape[i][j]);
			}
		

		layout = new FlowLayout();
		layout.setVgap(20);
		this.add(instruct);
		this.setLayout(layout);
		this.add(tips);
		this.add(nextshapePanel);
		this.add(l);
		this.add(s);
		this.add(start);
		this.add(pause);
		this.add(sound);
		this.setPreferredSize(new Dimension(150, 607));
	}

	WatchView(String play, int x) {
		s = new JLabel("Điểm : 0", JLabel.CENTER);
		l = new JLabel("mức độ :", JLabel.CENTER);
		final JTextArea instruct = new JTextArea("Hướng Dẫn Thao Tác \n" + "𝗔  : Sang Trái\n" + "𝗗  : Sang Phải\n"
				+ "𝗦  : Đưa Xuống\n" + "𝘄 : chuyển hướng\n" + "𝗙  : Xuống Đáy", 1, 5);
		instruct.setBackground(SystemColor.info);
		instruct.setEditable(false);
		tips = new JLabel("Khối tiếp theo", JLabel.CENTER);
		tips.setPreferredSize(new Dimension(100, 50));
		s.setPreferredSize(new Dimension(100, 50));
		l.setPreferredSize(new Dimension(100, 50));
		resetData();
		nextshapePanel = new JPanel(new GridLayout(4, 4));
		nextshapePanel.setBackground(new Color(255, 248, 225));
		nextshape = new JLabel[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				nextshape[i][j] = new JLabel();
				nextshape[i][j].setPreferredSize(new Dimension(20, 20));
				nextshape[i][j].setBackground(Color.orange);
				nextshapePanel.add(nextshape[i][j]);
			}

		layout = new FlowLayout();
		layout.setVgap(30);
		this.add(instruct);
		this.setLayout(layout);
		this.add(tips);
		this.add(nextshapePanel);
		this.add(l);
		this.add(s);
		this.setPreferredSize(new Dimension(150, 586));
	}

	WatchView(String play, String nub) {
		s = new JLabel("Điểm : 0", JLabel.CENTER);
		l = new JLabel("mức độ :", JLabel.CENTER);
		final JTextArea instruct = new JTextArea("Hướng Dẫn Thao Tác \n" + "◀ : Sang Trái\n " + "▶ : Sang Phải\n"
				+ "🡇 : Đưa Xuống\n" + "🡅 : chuyển hướng\n" + "𝗡𝘂𝗺𝗯𝗲𝗿 𝟬 : Xuống Đáy", 1, 5);
		instruct.setBackground(SystemColor.info);
		instruct.setEditable(false);
		tips = new JLabel("Khối tiếp theo", JLabel.CENTER);
		tips.setPreferredSize(new Dimension(100, 50));
		s.setPreferredSize(new Dimension(100, 50));
		l.setPreferredSize(new Dimension(100, 50));
		resetData();
		nextshapePanel = new JPanel(new GridLayout(4, 4));
		nextshapePanel.setBackground(new Color(255, 248, 225));
		nextshape = new JLabel[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				nextshape[i][j] = new JLabel();
				nextshape[i][j].setPreferredSize(new Dimension(20, 20));
				nextshape[i][j].setBackground(Color.orange);
				nextshapePanel.add(nextshape[i][j]);
			}

		layout = new FlowLayout();
		layout.setVgap(30);
		this.add(instruct);
		this.setLayout(layout);
		this.add(tips);
		this.add(nextshapePanel);
		this.add(l);
		this.add(s);
		this.setPreferredSize(new Dimension(150, 586));
	}

	WatchView(String mid) {
		final JTextArea instruct = new JTextArea("_Chế Độ 2 Người Chơi_\n \n" + "___◀=__PLAYER 1___ " + "\n\t\n"
				+ "\n\t\n" + "___PLAYER 2__=▶___\n" + "\n\t\n" + "\n\t\n" + "\n\t\n" + "\n\t\n", 1, 10);
		instruct.setBackground(SystemColor.info);
		instruct.setEditable(false);
		instruct.setWrapStyleWord(true);
		final JTextArea instruct1 = new JTextArea("\n \t\n\t\n", 1, 3);
		instruct1.setBackground(SystemColor.info);
		instruct1.setEditable(false);
		instruct1.setWrapStyleWord(true);
		this.setBackground(SystemColor.info);
		start = new JButton("bắt đầu");
		pause = new JButton("tạm ngừng");
		sound = new JToggleButton("🔊  ➖  🔇");

		this.add(instruct);
		this.add(start);
		this.add(instruct1);
		this.add(pause);
		this.add(instruct1);
		this.add(sound);
		this.setPreferredSize(new Dimension(150, 586));
	}

	/** Khởi tạo thông tin cấp độ và điểm số */
	public void resetData() {
		score = 0;
		s.setText("Điểm : " + score);
		level = 1;
		l.setText("mức độ : " + level);
	}

	/** Cung cấp một phương thức để thêm một trình nghe bên ngoài vào nút */
	public void addStartListener(MouseListener m, KeyListener k) {
		start.addMouseListener(m);
		start.addKeyListener(k);
	}

	public void addPauseListener(MouseListener l) {
		pause.addMouseListener(l);
	}

	public void addSound(KeyListener k) {
		try {
			sound.addKeyListener(k);
			sound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sound.isSelected()) {
					try {
						clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(new File("C:\\SoundGame\\NhacNen.wav")));
						clip.start();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					clip.stop();
				}
			}
		});				
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	/** Cập nhật thông tin điểm số trên bảng điều khiển */
	public void updateDataWatch(int c, Shape p) {
		if (c == 1) {
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("C:\\SoundGame\\UWU.wav")));
				clip.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (c == 2) {
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("C:\\SoundGame\\CongDiem2.wav")));
				clip.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (c == 3) {
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("C:\\SoundGame\\congdiem.wav")));
				clip.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (c == 4) {
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(new File("C:\\SoundGame\\An.wav")));
				clip.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		score += c * 100;
		s.setText("Điểm : " + score);
		level = score / 500 + 1;
		l.setText("mức độ : " + level);
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				nextshape[i][j].setOpaque(false);

		for (Point u : p.getShape())
			nextshape[u.x][u.y].setOpaque(true);
		
		// Đặt lại thuộc tính giao diện người dùng với một giá trị từ giao diện hiện tại.
		nextshapePanel.updateUI();
		
	}
	public int getLevel() {
		return level;
	}
}


 
