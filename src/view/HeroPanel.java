package view;

import java.awt.*;

import javax.swing.*;

import model.heroes.*;

public class HeroPanel extends JPanel{
	
	private JLabel heroImage;
	private JButton heroPower;
	private JLabel deckCards;
	private JProgressBar manaBar;
	private JProgressBar health;
	
	private int width;
	private int height;
	
	private JLabel backGround;
	
	public HeroPanel(int twidth,int theight) {
		super();
		this.width=twidth/5;
		this.height=theight*21/44;
		setPreferredSize(new Dimension(width,height));
		//setLayout(new GridBagLayout());
		setBackground(Color.black);
		
		
		setLayout(null);
		backGround=new JLabel();
		backGround.setSize(new Dimension(width,height));
		backGround.setLayout(new GridBagLayout());
		backGround.setLocation(0, 0);
		ImageIcon i = new ImageIcon(new ImageIcon("images/HearthStone Design/CardViewBack.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		backGround.setIcon(i);
		add(backGround);
		
		GridBagConstraints c = new GridBagConstraints();
		//ImageIcon i=new ImageIcon("images/Fire_Mage_Jaina.png");
		heroImage = new JLabel();
		
//		heroImage.setIcon(new ImageIcon(new javax.swing.ImageIcon(
//				getClass().getResource("images/Fire_Mage_Jaina.png"))
//				.getImage()
//				.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
		heroImage.setPreferredSize(new Dimension (width*4/5,height/2));
		deckCards = new JLabel();
		Font f=new Font("Traditional serif", Font.BOLD, 45) ;
		Font f2=new Font("Traditional serif", Font.BOLD, 30) ;
		deckCards.setFont(f2);
		deckCards.setHorizontalAlignment(JLabel.CENTER);
		deckCards.setPreferredSize(new Dimension (width*4/5,height/12));
		heroPower = new JButton();
		heroPower.setActionCommand("Hero Power");
		heroPower.setHorizontalAlignment(JButton.CENTER);
		heroPower.setFont(new Font("Traditional serif", Font.BOLD, 20) );
		heroPower.setPreferredSize(new Dimension (width*3/5,height/12));
		manaBar=new JProgressBar();
		manaBar.setPreferredSize(new Dimension(width/2,height/12));
		manaBar.setValue(0);
		manaBar.setString("");
		manaBar.setFont(f);
		manaBar.setStringPainted(true);
		health=new JProgressBar();
		health.setPreferredSize(new Dimension(width*4/5,height/12));
		health.setValue(0);
		health.setString("");
		health.setFont(f);
		health.setStringPainted(true);
		c.gridx=0;
		c.gridy=0;
		backGround.add(heroImage,c);
		c.gridy=1;
		backGround.add(deckCards,c);
		c.gridy=2;
		backGround.add(heroPower,c);
		c.gridy=3;
		backGround.add(health,c);
		c.gridy=4;
		backGround.add(manaBar,c);
	}
	
	
	
	

	public JProgressBar getHealth() {
		return health;
	}

	public void setHealth(JProgressBar health) {
		this.health = health;
	}


	public JLabel getHeroImage() {
		return heroImage;
	}
	public void setHeroImage(JLabel heroImage) {
		this.heroImage = heroImage;
	}
	public JButton getHeroPower() {
		return heroPower;
	}
	public void setHeroPower(JButton heroPower) {
		this.heroPower = heroPower;
	}
	public JLabel getHeroType() {
		return deckCards;
	}
	public void setDeckCards(JLabel dc) {
		this.deckCards = dc;
	}
	
	public JLabel getDeckCards() {
		return deckCards;
	}





	public JProgressBar getManaBar() {
		return manaBar;
	}
	public void setManaBar(JProgressBar mana) {
		this.manaBar = mana;
	}
	
	
}
