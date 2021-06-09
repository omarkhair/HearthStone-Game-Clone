package view;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Start extends JFrame implements ActionListener {	
	private int width;
	private int height;
	JButton Start, Multi;
	JButton Quit;
	private Clip bg;

	public Start() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) dim.getWidth();
		height = (int) dim.getHeight();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JLabel background = new JLabel(new ImageIcon(new ImageIcon("images/design/StartBG.jpg").getImage()
				.getScaledInstance(width, height, Image.SCALE_DEFAULT)));

		add(background);

		Start = new JButton(new ImageIcon(new ImageIcon("images/design/start.png").getImage()
				.getScaledInstance(width /7, height / 14, Image.SCALE_DEFAULT)));
		Start.setBorderPainted(false);
		Start.setContentAreaFilled(false);
		Start.setFocusPainted(false);
		Start.addActionListener(this);
		Start.setActionCommand("START");
		Start.setBounds(width / 2 - width / 14, height * 9 / 10, width /7, height / 14);
		
		Multi = new JButton(new ImageIcon(new ImageIcon("images/design/Lan.png").getImage()
				.getScaledInstance(width /7, height / 14, Image.SCALE_DEFAULT)));
		Multi.setBorderPainted(false);
		Multi.setContentAreaFilled(false);
		Multi.setFocusPainted(false);
		Multi.addActionListener(this);
		Multi.setActionCommand("Multiplayer");
		
		Multi.setBounds(width*1/25, height * 9 / 10, width /7, height / 14);

		Quit = new JButton(new ImageIcon(
				new ImageIcon("images/design/EXIT.png").getImage().getScaledInstance(width/7, height/14, Image.SCALE_DEFAULT)));
		Quit.setBorderPainted(false);
		Quit.setContentAreaFilled(false);
		Quit.setFocusPainted(false);
		Quit.addActionListener(this);
		Quit.setActionCommand("Quit");
		Quit.setBounds(width*32/40,height*18/20,  width /7, height / 14);

		background.add(Start);
		background.add(Quit);
		background.add(Multi);
		
		try {
			bg=playSound("sounds/Intro.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.out.println("Error");
		}
		
		this.revalidate();
		this.repaint();
	}
	private Clip playSound(String s) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(s).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			return clip;
	}

	public static void main(String args[]) {
		new Start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "START") {
			//bg.stop();
			finalizeSound();
			new HeroSelectionView();
			this.setVisible(false);
		} else if (e.getActionCommand() == "Quit") {
			int a = JOptionPane.showConfirmDialog(this, "Do you want to exit ", "EXIT Bar", JOptionPane.YES_NO_OPTION);
			if (a == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		else if (e.getActionCommand() == "Multiplayer") {	
			finalizeSound();
			new MultiplayerView();
			this.setVisible(false);
		}

	}
	private void finalizeSound() {
		try {
			Clip start=playSound("sounds/Start.wav");
			Timer timer = new Timer(1800, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent ae) {
	               bg.stop();
	                repaint();

	            }
	        });
			timer.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			System.out.println("Error");
		}
		
	}
}