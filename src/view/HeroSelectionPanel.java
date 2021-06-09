package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;

public class HeroSelectionPanel extends JPanel implements ActionListener {

	private int width;
	private int height;
	private ArrayList<JButton> heros;
	private JLabel backGround;
	private Hero chosen;

	public HeroSelectionPanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		setSize(new Dimension(width / 2, height));
		setLayout(null);
		setBackground(Color.green);
		backGround = new JLabel();
		backGround.setLocation(0, 0);
		backGround.setSize(new Dimension(width / 2, height));
		ImageIcon i = new ImageIcon(new ImageIcon("images/Selection/Magebg.jpg").getImage().getScaledInstance(width / 2, height,
				Image.SCALE_DEFAULT));
		backGround.setIcon(i);
		add(backGround);
		heros = new ArrayList<JButton>();
		int w = width / 24;
		int h = (int) (height * 0.7);
		for (int k = 0; k < 5; k++) {
			JButton b = createButton(w, h);
			heros.add(b);
			b.addActionListener(this);
			w += width / 12;
		}

		createImages("images/Heros/Jaina Proudmoore.png", 0);
		createImages("images/Heros/Gul'dan.png", 1);
		createImages("images/Heros/Anduin Wrynn.png", 2);
		createImages("images/Heros/Rexxar.png", 3);
		createImages("images/Heros/Uther Lightbringer.png", 4);

		try {
			chosen = new Mage();
		} catch (IOException | CloneNotSupportedException e) {
			System.out.println("ERRORRR");
		}

		// //ImageIcon i=new ImageIcon("images/Fire_Mage_Jaina.png");
//		heroImage = new JLabel();
//		
////		heroImage.setIcon(new ImageIcon(new javax.swing.ImageIcon(
////				getClass().getResource("images/Fire_Mage_Jaina.png"))
////				.getImage()
////				.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
//		heroImage.setPreferredSize(new Dimension (200,200));
//		heroName = new JLabel();
//		Font f=new Font("Traditional serif", Font.BOLD, 45) ;
//		heroName.setFont(f);
//		heroName.setHorizontalAlignment(JLabel.CENTER);
//		heroName.setPreferredSize(new Dimension (200,50));

	}

	private void createImages(String name, int i) {
		ImageIcon image = new ImageIcon(
				new ImageIcon(name).getImage().getScaledInstance(width / 12, height / 6, Image.SCALE_DEFAULT));
		heros.get(i).setIcon(image);
		heros.get(i).setContentAreaFilled(false);
		heros.get(i).setFocusPainted(false);
		heros.get(i).setBorderPainted(false);

	}
	private Clip playSound(String s) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(s).getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
		return clip;
}


	private JButton createButton(int i, int j) {
		JButton b = new JButton();
		b.setSize(new Dimension(width / 12, height / 6));
		b.setLocation(i, j);
		backGround.add(b);
		return b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			playSound("sounds/HeroSelect.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
			System.out.println("Error");
		}
		JButton b = (JButton) e.getSource();
		int i = heros.indexOf(b);
		String imagePath = "";
		try {
			if (i == 0) {
				imagePath = "images/Selection/Magebg.jpg";
				chosen = new Mage();
			} else if (i == 1) {
				imagePath = "images/Selection/Warlockbg.jpg";
				chosen = new Warlock();
			} else if (i == 2) {
				imagePath = "images/Selection/Priestbg.jpg";
				chosen = new Priest();
			} else if (i == 3) {
				imagePath = "images/Selection/Hunterbg.jpg";
				chosen = new Hunter();
			} else if (i == 4) {
				imagePath = "images/Selection/Paladinbg.jpg";
				chosen = new Paladin();
			}
			ImageIcon image = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(width / 2, height,
					Image.SCALE_DEFAULT));
			backGround.setIcon(image);
		} catch (IOException | CloneNotSupportedException e1) {
			System.out.println("ERROOR");
		}

	}

	public Hero getChosen() {
		return chosen;
	}

	public void setChosen(Hero chosen) {
		this.chosen = chosen;
	}
	

}