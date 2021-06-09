package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import controller.Controller;

public class HeroSelectionView extends JFrame implements ActionListener {
	private int width;
	private int height;
	private HeroSelectionPanel left;
	private HeroSelectionPanel right;
	private JButton startGame;
	private JButton Quit;
	private Clip cur;

	public HeroSelectionView() {

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) dim.getWidth();
		height = (int) dim.getHeight();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);

		setLayout(null);

		JLayeredPane pane = new JLayeredPane();
		pane.setLocation(0, 0);
		pane.setSize(width, height);

		add(pane);
		// pane.add(
		left = new HeroSelectionPanel(width, height);
		left.setLocation(0, 0);
		// left.setSize(width/2, height);
		pane.add(left, 1, 0);
		right = new HeroSelectionPanel(width, height);
		right.setLocation(width / 2, 0);
		// right.setSize(width/2, height);
		pane.add(right, 1, 0);

		startGame = new JButton();
		startGame.addActionListener(this);
		ImageIcon i = new ImageIcon(new ImageIcon("images/design/SelectionPlay.png").getImage()
				.getScaledInstance(width / 10, width / 10, Image.SCALE_DEFAULT));
		startGame.setIcon(i);
		startGame.setContentAreaFilled(false);
		startGame.setFocusPainted(false);
		startGame.setBorderPainted(false);
		startGame.setSize(new Dimension(width / 10, width / 10));
		startGame.setLocation(width / 2 - startGame.getWidth() / 2, height / 12);
		pane.add(startGame, 2, 0);

		JLabel vs = new JLabel();
		i = new ImageIcon(new ImageIcon("images/design/vs.png").getImage().getScaledInstance(width / 6, height * 3 / 5,
				Image.SCALE_DEFAULT));
		vs.setIcon(i);
		vs.setSize(new Dimension(width / 6, height * 3 / 5));
		vs.setLocation(width / 2 - vs.getWidth() / 2, height / 5);
		pane.add(vs, 2, 0);

		Quit = new JButton(new ImageIcon(new ImageIcon("images/design/EXIT.png").getImage().getScaledInstance(width / 8,
				height / 16, Image.SCALE_DEFAULT)));
		Quit.setBorderPainted(false);
		Quit.setContentAreaFilled(false);
		Quit.setFocusPainted(false);
		Quit.addActionListener(this);
		Quit.setActionCommand("Quit");
		Quit.setSize(new Dimension(width / 8, height / 16));
		Quit.setLocation(width / 2 - Quit.getWidth() / 2, height * 36 / 40);

		pane.add(Quit, 2, 0);
		sound();
		revalidate();
		repaint();

	}

	private void sound() {
		cur = playSound("sounds/SelectionAudio begin.wav");
		Timer timer = new Timer(26626, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (isVisible())
					repeatSound();
			}

		});
		timer.setRepeats(false);
		timer.start();

	}

	private void repeatSound() {
		cur = playSound("sounds/SelectionAudio.wav");
		cur.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void main(String[] args) {
		HeroSelectionView h = new HeroSelectionView();
	}

	private Clip playSound(String s) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(s).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			return clip;
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Quit") {
			int a = JOptionPane.showConfirmDialog(this, "Do you want to exit ", "EXIT Bar", JOptionPane.YES_NO_OPTION);
			if (a == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		} else {
			playSound("sounds/play.wav");

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Controller c = new Controller(left.getChosen(), right.getChosen());
			this.setVisible(false);
			cur.stop();
			cur = null;

		}
	}
}
