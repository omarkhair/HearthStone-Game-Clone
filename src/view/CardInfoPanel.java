package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import model.cards.*;


 
public class CardInfoPanel extends JPanel {
	private JLabel cardImage;
	private JLabel cardName;
	private JLabel cardInfo;
	private JButton attack;
	private JButton play;
	private Card card;
	private CardButton cardButton;
	private int width;
	private int height;
	
	private JLabel backGround;
	


	public CardInfoPanel(int twidth,int theight) {
		super();
		this.width=twidth/5;
		this.height=theight;
		setVisible(false);
		setPreferredSize(new Dimension(width, height));
		//setBackground(Color.BLACK);
		setLayout(null);
		backGround=new JLabel();
		backGround.setSize(new Dimension(width,height));
		backGround.setLayout(new GridBagLayout());
		backGround.setLocation(0, 0);
		add(backGround);
		GridBagConstraints c = new GridBagConstraints();
		
		cardName = new JLabel();
		cardName.setPreferredSize(new Dimension(width*4/5,height*5/66));
		cardName.setFont(new Font("Traditional serif", Font.BOLD, 35));
		cardName.setHorizontalAlignment(JLabel.CENTER);
		cardImage = new JLabel();
		cardImage.setPreferredSize(new Dimension(width*9/10, height*4/10));
		//cardInfo = new JTextArea(5,15);
		//JScrollPane scrollPane = new JScrollPane(cardInfo);
		//scrollPane.getVerticalScrollBar().setValue(0);
		//scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		cardInfo=new JLabel();
		cardInfo.setFont(new Font("Traditional serif", Font.BOLD, 20));
		cardInfo.setForeground(Color.black);
		//cardInfo.setEditable(false);
		//cardInfo.setPreferredSize(new Dimension(250,170));
		attack=new JButton();
		//attack.setFont(new Font("Traditional serif", Font.BOLD, 50) );
		attack.setActionCommand("Attack");
		ImageIcon i = new ImageIcon(new ImageIcon("images/design/Attack.png").getImage().getScaledInstance(width*4/5,height*5/66, Image.SCALE_DEFAULT));
		attack.setIcon(i);
		attack.setContentAreaFilled(false);
		attack.setPreferredSize(new Dimension(width*4/5,height*5/66));
		play=new JButton();
		play.setActionCommand("Play");
		i = new ImageIcon(new ImageIcon("images/design/playCard.png").getImage().getScaledInstance(width*4/5,height*5/66, Image.SCALE_DEFAULT));
		play.setIcon(i);
		play.setContentAreaFilled(false);
		
		//play.setBorder(new LineBorder(Color.green,12));
		
		//play.setFont(new Font("Traditional serif", Font.BOLD, 50) );
		play.setPreferredSize(new Dimension(width*4/5,height*5/66));
		c.gridx=0;
		c.gridy=0;
		backGround.add(cardImage,c);
		c.gridy=1;
		backGround.add(cardName,c);
		c.gridy=2;
		
		//add(cardInfo,c);
		backGround.add(cardInfo,c);
		c.gridy=3;
		backGround.add(attack,c);
		c.gridy=4;
		backGround.add(play,c);
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


	public JLabel getCardInfo() {
		return cardInfo;
	}


	public void setCardInfo(JLabel cardInfo) {
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
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public CardButton getCardButton() {
		return cardButton;
	}
	public void setCardButton(CardButton cardButton) {
		this.cardButton = cardButton;
	}
	public JLabel getBackGround() {
		return backGround;
	}
	public void setBackGround(JLabel backGround) {
		this.backGround = backGround;
	}
	public void setBackGroundImage(String s) {
		ImageIcon i = new ImageIcon(new ImageIcon(s).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		backGround.setIcon(i);
	}
	
}
