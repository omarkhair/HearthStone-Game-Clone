package view;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.PopupMenu;
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
import javax.swing.*;
import javax.swing.border.Border;

import model.heroes.*;
public class HeroDeathView extends JFrame implements ActionListener {
	private int width;
	private int height;
	private JButton PlayAgain;
	private JButton Quit;
	private JPanel upperPanel ; 
	private JPanel lowerPanel ; 
	
	public HeroDeathView(Hero lower ,Hero upper , Boolean up) {
		getContentPane().setBackground(Color.red);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		this.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) (dim.getWidth());
		height = (int) (dim.getHeight());
//		getContentPane().setSize(dim);
		upperPanel = new JPanel(); 
		lowerPanel = new JPanel();
		upperPanel.setBackground(Color.white);
		lowerPanel.setBackground(Color.green);
		setLayout(new BorderLayout());
		
		upperPanel.setPreferredSize(new Dimension(width,height/2));
		lowerPanel.setPreferredSize(new Dimension(width,height/2));
		
		add(upperPanel, BorderLayout.NORTH); 
		add(lowerPanel, BorderLayout.SOUTH);
		String upper1=  upperPath(lower, upper, up) ; 
		String lower1 = lowerpath(lower, upper, up); 
		int w=width/2;
		int h=height/2;
		JLabel upper2 = new JLabel(new ImageIcon(new ImageIcon(upper1).getImage()
				.getScaledInstance(w,h, Image.SCALE_DEFAULT)));
		JLabel lower2 = new JLabel(new ImageIcon(new ImageIcon(lower1).getImage()
				.getScaledInstance(w,h, Image.SCALE_DEFAULT)));

		upper2.setSize(w,h);
		upper2.setLocation(width/2-w/2, height/4-h/2);
		lower2.setSize(w,h);
		lower2.setLocation(width/2-w/2, height/4-h/2);
				

		JLabel background1 = new JLabel(new ImageIcon(new ImageIcon("images/design/UpperHalfBlur.png").getImage()
		.getScaledInstance(width, height/2, Image.SCALE_DEFAULT)));
		
		JLabel background2 = new JLabel(new ImageIcon(new ImageIcon("images/design/LowerHalfBlur.png").getImage()
			.getScaledInstance(width, height/2, Image.SCALE_DEFAULT)));

		upperPanel.setLayout(new BorderLayout());
		lowerPanel.setLayout(new BorderLayout());
	
		
		background1.setBounds(0, 0, upperPanel.getWidth(), upperPanel.getHeight());
		background2.setBounds(0, 0, lowerPanel.getWidth(), lowerPanel.getHeight());
		upperPanel.add(background1);
		lowerPanel.add(background2);
	
		//System.out.println("done");

		PlayAgain = new JButton(new ImageIcon(new ImageIcon("images/design/Play Again.png").getImage()
				.getScaledInstance(width / 7, height / 14, Image.SCALE_DEFAULT)));
		PlayAgain.setBorderPainted(false);
		PlayAgain.setContentAreaFilled(false);
		PlayAgain.setFocusPainted(false);
		PlayAgain.addActionListener(this);
		PlayAgain.setActionCommand("PlayAgain");
		
//		PlayAgain.setBounds(width / 2 - width / 12, height * 8 / 10, width / 6, height / 10);

		Quit = new JButton(new ImageIcon(
				new ImageIcon("images/design/EXIT.png").getImage().getScaledInstance(width/7, height/14, Image.SCALE_DEFAULT)));
		Quit.setBorderPainted(false);
		Quit.setContentAreaFilled(false);
		Quit.setFocusPainted(false);
		Quit.addActionListener(this);
		Quit.setActionCommand("Quit");
	
	   PlayAgain.setBounds(width*1/40,height*8/20, width/7, height/14);
		Quit.setBounds(width*33/40,height*8/20, width/7, height/14);

		background2.add(Quit);
		background2.add(PlayAgain);
		background1.add(upper2);
		background2.add(lower2); 

		playSound("sounds/Victory.wav");

		// Quit.setBounds((int) (0.833 * (dim.width)), (int) (0.787 * (dim.height)), 400, 250);
		this.revalidate();
		this.repaint();
	}

	public static String upperPath (Hero lower , Hero upper,Boolean up) {
	String s = ""; 
	if (up) {
		if (upper instanceof Mage) 
			s = "images/Heros/Mage-Win.png" ; 
		else if (upper instanceof Warlock)
			s = "images/Heros/Warlock-Win.png" ; 

			else if (upper instanceof Priest)
				s = "images/Heros/Priest-Win.png" ; 

				else if (upper instanceof Hunter)
					s = "images/Heros/Hunter-Win.png" ; 

					else if (upper instanceof Paladin)
						s = "images/Heros/Paladin-Win.png" ; 
		}
	else {
		if (upper instanceof Mage) 
			s = "images/Heros/Mage-Defeat.png" ; 
		else if (upper instanceof Warlock)
			s = "images/Heros/Warlock-Defeat.png" ; 

			else if (upper instanceof Priest)
				s = "images/Heros/Priest-Defeat.png" ; 

				else if (upper instanceof Hunter)
					s = "images/Heros/Hunter-Defeat.png" ; 

					else if (upper instanceof Paladin)
						s = "images/Heros/Paladin-Defeat.png" ; 
	}
	return s ; 
		
}

public static String lowerpath (Hero lower , Hero upper,Boolean up) {
	String s = ""; 
	if (!up) {
		if (lower instanceof Mage) 
			s = "images/Heros/Mage-Win.png" ; 
		else if (lower instanceof Warlock)
			s = "images/Heros/Warlock-Win.png" ; 

			else if (lower instanceof Priest)
				s = "images/Heros/Priest-Win.png" ; 

				else if (lower instanceof Hunter)
					s = "images/Heros/Hunter-Win.png" ; 

					else if (lower instanceof Paladin)
						s = "images/Heros/Paladin-Win.png" ; 
		}
	else {
		if (lower instanceof Mage) 
			s = "images/Heros/Mage-Defeat.png" ; 
		else if (lower instanceof Warlock)
			s = "images/Heros/Warlock-Defeat.png" ; 

			else if (lower instanceof Priest)
				s = "images/Heros/Priest-Defeat.png" ; 

				else if (lower instanceof Hunter)
					s = "images/Heros/Hunter-Defeat.png" ; 

					else if (lower instanceof Paladin)
						s = "images/Heros/Paladin-Defeat.png" ; 
	}
	return s ; 
		
}
 
			
	
	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		
		new HeroDeathView( new Paladin(),new Mage() , true  ); 
	}
	private Clip playSound(String s) {
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(s).getAbsoluteFile());
		
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
		return clip;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.out.println("Error");
		}
		return null;
}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "PlayAgain") {
			new HeroSelectionView();
			this.setVisible(false);
		} else if (e.getActionCommand() == "Quit") {
			int a = JOptionPane.showConfirmDialog(this, "Do you want to exit ", "Exit Bar", JOptionPane.YES_NO_OPTION);
			if (a == JOptionPane.YES_OPTION) {
				System.exit(0);
			}

		}

	}
}