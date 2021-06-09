package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.Timer;

//import controller.Controller;

@SuppressWarnings("serial")
public class GameView extends JFrame{
	private int width;
	private int height;
	
	private JPanel field;              //kol eli 3al yemeen
	private CardInfoPanel cardView;         //el 3al shemal
	
	
	
	private JPanel lowerPanel;         //elhero bel hand wel field
	private JPanel upperPanel;
	
	private HeroPanel lowerHero;
	private HeroPanel upperHero;
	private JPanel lCardArea;           //hand+field
	private JPanel uCardArea;
	
	
	
	
	private JLabel lowerHand;
	private JLabel upperHand;
	private JLabel lowerField;
	private JLabel upperField;
	
	private JLabel middlePanel;
	private JButton endTurn;
	private JLabel dialogue;

	
//	private JPanel lowerFieldPanel;
//	private JPanel upperFieldPanel;
//	private JLabel lowerHandBack;
//	private JLabel upperHandBack;
//	private JLabel lowerHeroBack;
//	private JLabel upperHeroBack;
	
	
	 
//	private ArrayList<Card> hero1Hand;
//	private ArrayList<Card> hero2Hand;
//	private ArrayList<Card> hero1Hand;
//	private ArrayList<Card> hero1Hand;
	
	public GameView() {
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width=(int) dim.getWidth();
		height=(int) dim.getHeight();
		
		
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		
		endTurn=new JButton();
		endTurn.setActionCommand("End Turn");
		endTurn.setPreferredSize(new Dimension(width/5,height/11));
		
		buildPanels();
		
		
		
		ImageIcon i=new ImageIcon(new ImageIcon("images/design/end turn.png").getImage().getScaledInstance(250, 50, Image.SCALE_DEFAULT));
		endTurn.setIcon(i);
		endTurn.setContentAreaFilled(false);
		endTurn.setFocusPainted(false);
		endTurn.setBorderPainted(false);
		i=new ImageIcon(new ImageIcon("images/design/End_Turn_Disabled.png").getImage().getScaledInstance(250, 50, Image.SCALE_DEFAULT));
        endTurn.setDisabledIcon(i);		
		
		
	//	lowerHeroType.setPreferredSize(new Dimension);
		//b.setPreferredSize(100,100);
		
		
		
//		for (int i = 0; i < 10; i++) {
//			JButton b=new JButton();
//			b.setPreferredSize(new Dimension(90, 150));
//			JButton b2=new JButton();
//			b2.setPreferredSize(new Dimension(90, 150));
//			heroHand2Panel.add(b2);
//			heroHand1Panel.add(b);
//		}
		
		//heroHand1.setLayout(new GridLayout(0,8));
	//	heroHand1.setLocationRelativeTo(null);
		
		
		
		
		//5aliha ba3den
		//this.setUndecorated(true);
		
//        JLabel background=new JLabel(new ImageIcon(
//                new ImageIcon("images/covered.png").getImage().getScaledInstance(1600, 900, Image.SCALE_DEFAULT)));
//      //  background.setPreferredSize(new Dimension(1600,900));
//        add(background);
//    
//        this.revalidate();
//        this.repaint();
	}
	
		
	
	private void buildPanels() {
		JLabel bg=new JLabel() ;
		bg.setSize(new Dimension(width,height));
		bg.setLocation(0, 0);
		ImageIcon i2=new ImageIcon(new ImageIcon("images/design/BackGround.jpg").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		bg.setIcon(i2);
		add(bg);
		
		cardView=new CardInfoPanel(width,height);
		add(cardView,BorderLayout.WEST);
		
		field=new JPanel();
		field.setPreferredSize(new Dimension(width*4/5,height));
		add(field,BorderLayout.EAST);
		
		field.setLayout(new BorderLayout());
		
		lowerPanel=new JPanel();
		lowerPanel.setPreferredSize(new Dimension(width*4/5,height*21/44));
		field.add(lowerPanel,BorderLayout.SOUTH);
		lowerPanel.setBackground(Color.black);
		
		upperPanel=new JPanel();
		upperPanel.setPreferredSize(new Dimension(width*4/5,height*21/44));
		field.add(upperPanel,BorderLayout.NORTH);
		upperPanel.setBackground(Color.black);
		
		lowerPanel.setLayout(new BorderLayout());
		
		lowerHero=new HeroPanel(width,height);
		lowerPanel.add(lowerHero,BorderLayout.EAST);
		
		
		upperPanel.setLayout(new BorderLayout());
		
		upperHero=new HeroPanel(width,height);
		upperPanel.add(upperHero,BorderLayout.EAST);
		
		lCardArea=new JPanel();
		lCardArea.setPreferredSize(new Dimension(width*3/5,height*21/44));
		lowerPanel.add(lCardArea,BorderLayout.WEST);
		lCardArea.setBackground(Color.BLACK);
		
		uCardArea=new JPanel();
		uCardArea.setPreferredSize(new Dimension(width*3/5,height*21/44));
		upperPanel.add(uCardArea,BorderLayout.WEST);
		uCardArea.setBackground(Color.black);
		
		lCardArea.setLayout(new BorderLayout());
		//TODO
		lowerHand=new JLabel();
		ImageIcon i = new ImageIcon(
				new ImageIcon("images/design/LowerHand.png").getImage().getScaledInstance(width*3/5,height*5/22, Image.SCALE_DEFAULT));
		lowerHand.setIcon(i);
		lowerHand.setLayout(new FlowLayout(FlowLayout.CENTER, 0, height/22));
		lowerHand.setPreferredSize(new Dimension(width*3/5,height*5/22));
		lCardArea.add(lowerHand,BorderLayout.SOUTH);
		lowerHand.setBackground(Color.black);
		lowerHand.setAlignmentX(CENTER_ALIGNMENT);
		lowerHand.setAlignmentY(CENTER_ALIGNMENT);
		
		uCardArea.setLayout(new BorderLayout());
		
		upperHand=new JLabel();
		 i = new ImageIcon(
				new ImageIcon("images/design/UpperHand.png").getImage().getScaledInstance(width*3/5,height*5/22, Image.SCALE_DEFAULT));
		upperHand.setIcon(i);
		upperHand.setLayout(new FlowLayout(FlowLayout.CENTER, 0, height/22));
		//upperHand.setLayout(new BoxLayout(upperHand, BoxLayout.X_AXIS));
		upperHand.setPreferredSize(new Dimension(width*3/5,height*5/22));
		uCardArea.add(upperHand,BorderLayout.NORTH);
		upperHand.setBackground(Color.black);
		upperHand.setAlignmentX(CENTER_ALIGNMENT);
		upperHand.setAlignmentY(CENTER_ALIGNMENT);
		
		
		
		lowerField=new JLabel();
		i = new ImageIcon(
				new ImageIcon("images/design/LowerField.png").getImage().getScaledInstance(width*3/5,height/4, Image.SCALE_DEFAULT));
		lowerField.setIcon(i);
		lowerField.setLayout(new FlowLayout(FlowLayout.CENTER, 0, height/8-height*3/44));
		lowerField.setPreferredSize(new Dimension(width*3/5,height*1/4));
		lCardArea.add(lowerField,BorderLayout.NORTH);
		lowerField.setBackground(Color.black);
		lowerField.setAlignmentX(CENTER_ALIGNMENT);
		lowerField.setAlignmentY(CENTER_ALIGNMENT);
		
		
			
		upperField=new JLabel();
		i = new ImageIcon(
				new ImageIcon("images/design/UpperField.png").getImage().getScaledInstance(width*3/5,height/4, Image.SCALE_DEFAULT));
		upperField.setIcon(i);
		upperField.setLayout(new FlowLayout(FlowLayout.CENTER, 0, height/8-height*3/44));
		upperField.setPreferredSize(new Dimension(width*3/5,height*1/4));
		uCardArea.add(upperField,BorderLayout.SOUTH);
		upperField.setBackground(Color.black);
		upperField.setAlignmentX(CENTER_ALIGNMENT);
		upperField.setAlignmentY(CENTER_ALIGNMENT);
		
		
		middlePanel= new JLabel();
		i = new ImageIcon(
				new ImageIcon("images/design/MessageBar.png").getImage().getScaledInstance(width*4/5,height/22+7, Image.SCALE_DEFAULT));
		middlePanel.setIcon(i);
		middlePanel.setBackground(Color.BLACK);
		field.add(middlePanel);
		middlePanel.setLayout(new BorderLayout());
		middlePanel.add(endTurn, BorderLayout.EAST);
		dialogue=new JLabel();
		dialogue.setFont(new Font("Times New Roman", Font.BOLD, 27));
		dialogue.setForeground(Color.lightGray);
		dialogue.setHorizontalAlignment(JLabel.CENTER);
		middlePanel.add(dialogue);
		
		
	}
	
	

	
	public JPanel getLowerPanel() {
		return lowerPanel;
	}
	public void setLowerPanel(JPanel lowerPanel) {
		this.lowerPanel = lowerPanel;
	}
	public JPanel getUpperPanel() {
		return upperPanel;
	}
	public void setUpperPanel(JPanel upperPanel) {
		this.upperPanel = upperPanel;
	}
	public HeroPanel getLowerHero() {
		return lowerHero;
	}
	public void setLowerHero(HeroPanel lowerHero) {
		this.lowerHero = lowerHero;
	}
	public HeroPanel getUpperHero() {
		return upperHero;
	}
	public void setUpperHero(HeroPanel upperHero) {
		this.upperHero = upperHero;
	}
	public JLabel getLowerHand() {
		return lowerHand;
	}
	public void setLowerHand(JLabel lowerHand) {
		this.lowerHand = lowerHand;
	}
	public JLabel getUpperHand() {
		return upperHand;
	}
	public void setUpperHand(JLabel upperHand) {
		this.upperHand = upperHand;
	}
	public JLabel getLowerField() {
		return lowerField;
	}
	public void setLowerField(JLabel lowerField) {
		this.lowerField = lowerField;
	}
	public JLabel getUpperField() {
		return upperField;
	}
	public void setUpperField(JLabel upperField) {
		this.upperField = upperField;
	}
	public CardInfoPanel getCardView() {
		return cardView;
	}
	public void setCardView(CardInfoPanel cardView) {
		this.cardView = cardView;
	}



	public JPanel getField() {
		return field;
	}



	public void setField(JPanel field) {
		this.field = field;
	}



	public JPanel getlCardArea() {
		return lCardArea;
	}



	public void setlCardArea(JPanel lCardArea) {
		this.lCardArea = lCardArea;
	}



	public JPanel getuCardArea() {
		return uCardArea;
	}



	public void setuCardArea(JPanel uCardArea) {
		this.uCardArea = uCardArea;
	}



	public JButton getEndTurn() {
		return endTurn;
	}



	public void setEndTurn(JButton endTurn) {
		this.endTurn = endTurn;
	}



	public JLabel getMiddlePanel() {
		return middlePanel;
	}



	public void setMiddlePanel(JLabel middlePanel) {
		this.middlePanel = middlePanel;
	}



	public JLabel getDialogue() {
		return dialogue;
	}



	public void setDialogue(JLabel dialogue) {
		this.dialogue = dialogue;
	}



	public int getWidth() {
		return width;
	}



	public int getHeight() {
		return height;
	}
//	public static void main(String[] args) {
//		GameView name = new GameView();
//	}

}
