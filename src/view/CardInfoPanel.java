package view;

import java.awt.*;
import javax.swing.*;


 
public class CardInfoPanel extends JPanel {
	private JLabel cardImage;
	private JLabel cardName;
	private JTextArea cardInfo;
	private JButton attack;
	private JButton play;
	
	
	


	public CardInfoPanel() {
		super();
		setVisible(false);
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(300, 900));
		setBackground(Color.cyan);
		GridBagConstraints c = new GridBagConstraints();
		cardName = new JLabel();
		cardName.setPreferredSize(new Dimension(250,100));
		cardName.setFont(new Font("Traditional serif", Font.BOLD, 45));
		cardImage = new JLabel();
		cardImage.setPreferredSize(new Dimension(250, 360));
		cardInfo = new JTextArea();
		cardInfo.setEditable(false);
		cardInfo.setPreferredSize(new Dimension(250,170));
		attack=new JButton();
		//attack.setFont(new Font("Traditional serif", Font.BOLD, 50) );
		attack.setText("Attack");
		attack.setPreferredSize(new Dimension(250,100));
		play=new JButton("Play");
		//play.setFont(new Font("Traditional serif", Font.BOLD, 50) );
		play.setPreferredSize(new Dimension(250,100));
		c.gridx=0;
		c.gridy=0;
		add(cardImage,c);
		c.gridy=1;
		add(cardName,c);
		c.gridy=2;
		add(cardInfo,c);
		c.gridy=3;
		add(attack,c);
		c.gridy=4;
		add(play,c);
//		ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/Jaina Proudmoore.png").getImage().getScaledInstance(250, 360, Image.SCALE_DEFAULT));
//		cardImage.setIcon(imageIcon);
		//Font f = new Font("Traditional serif", Font.BOLD, 45);
	}
	public JLabel getCardImage() {
		return cardImage;
	}


	public void setCardImage(JLabel cardImage) {
		this.cardImage = cardImage;
	}


	public JLabel getCardName() {
		return cardName;
	}


	public void setCardName(JLabel cardName) {
		this.cardName = cardName;
	}


	public JTextArea getCardInfo() {
		return cardInfo;
	}


	public void setCardInfo(JTextArea cardInfo) {
		this.cardInfo = cardInfo;
	}


	public JButton getAttack() {
		return attack;
	}


	public void setAttack(JButton attack) {
		this.attack = attack;
	}


	public JButton getPlay() {
		return play;
	}


	public void setPlay(JButton play) {
		this.play = play;
	}
}
