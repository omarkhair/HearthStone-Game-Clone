package view;

import java.awt.*;

import javax.swing.*;

import model.heroes.*;

public class HeroPanel extends JPanel{
	
	private JLabel heroImage;
	private JButton heroPower;
	private JLabel heroType;
	private JProgressBar manaBar;
	private JProgressBar health;
	public HeroPanel() {
		super();
		setPreferredSize(new Dimension(300,400));
		setLayout(new GridBagLayout());
		setBackground(Color.green);
		GridBagConstraints c = new GridBagConstraints();
		//ImageIcon i=new ImageIcon("images/Fire_Mage_Jaina.png");
		heroImage = new JLabel();
		
//		heroImage.setIcon(new ImageIcon(new javax.swing.ImageIcon(
//				getClass().getResource("images/Fire_Mage_Jaina.png"))
//				.getImage()
//				.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
		heroImage.setPreferredSize(new Dimension (200,200));
		heroType = new JLabel();
		Font f=new Font("Traditional serif", Font.BOLD, 45) ;
		heroType.setFont(f);
		heroType.setHorizontalAlignment(JLabel.CENTER);
		heroType.setPreferredSize(new Dimension (200,50));
		heroPower = new JButton("Hero Power");
		heroPower.setFont(new Font("Traditional serif", Font.BOLD, 20) );
		heroPower.setPreferredSize(new Dimension (200,50));
		manaBar=new JProgressBar();
		manaBar.setPreferredSize(new Dimension(200,50));
		manaBar.setValue(0);
		manaBar.setString("");
		manaBar.setFont(f);
		manaBar.setStringPainted(true);
		health=new JProgressBar();
		health.setPreferredSize(new Dimension(200,50));
		health.setValue(0);
		health.setString("");
		health.setFont(f);
		health.setStringPainted(true);
		c.gridx=0;
		c.gridy=0;
		add(heroImage,c);
		c.gridy=1;
		add(heroType,c);
		c.gridy=2;
		add(heroPower,c);
		c.gridy=3;
		add(health,c);
		c.gridy=4;
		add(manaBar,c);
		
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
		return heroType;
	}
	public void setHeroType(JLabel heroType) {
		this.heroType = heroType;
	}
	public JProgressBar getManaBar() {
		return manaBar;
	}
	public void setManaBar(JProgressBar mana) {
		this.manaBar = mana;
	}
	
	
}
