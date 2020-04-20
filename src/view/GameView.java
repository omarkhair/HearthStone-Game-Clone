package view;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import model.cards.*;
@SuppressWarnings("serial")
public class GameView extends JFrame{
	private JPanel field;              //kol eli 3al yemeen
	private CardInfoPanel cardView;         //el 3al shemal
	
	
	
	private JPanel lowerPanel;         //elhero bel hand wel field
	private JPanel upperPanel;
	
	private HeroPanel lowerHero;
	private HeroPanel upperHero;
	private JPanel lCardArea;           //hand+field
	private JPanel uCardArea;
	
	
	
	
	private JPanel lowerHand;
	private JPanel upperHand;
	private JPanel lowerField;
	private JPanel upperField;
	
	
	 
//	private ArrayList<Card> hero1Hand;
//	private ArrayList<Card> hero2Hand;
//	private ArrayList<Card> hero1Hand;
//	private ArrayList<Card> hero1Hand;
	
	public GameView() {
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1600, 900);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		buildPanels();
		
		
		
		
		
		
		
		
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
		
	}
	
		

	private void buildPanels() {
		cardView=new CardInfoPanel();
		add(cardView,BorderLayout.WEST);
		
		field=new JPanel();
		field.setPreferredSize(new Dimension(1250,900));
		add(field,BorderLayout.EAST);
		
		field.setLayout(new BorderLayout());
		
		lowerPanel=new JPanel();
		lowerPanel.setPreferredSize(new Dimension(1100,400));
		field.add(lowerPanel,BorderLayout.SOUTH);
		lowerPanel.setBackground(Color.blue);
		
		upperPanel=new JPanel();
		upperPanel.setPreferredSize(new Dimension(1100,400));
		field.add(upperPanel,BorderLayout.NORTH);
		upperPanel.setBackground(Color.red);
		
		lowerPanel.setLayout(new BorderLayout());
		
		lowerHero=new HeroPanel();
		lowerPanel.add(lowerHero,BorderLayout.EAST);
		
		
		upperPanel.setLayout(new BorderLayout());
		
		upperHero=new HeroPanel();
		upperPanel.add(upperHero,BorderLayout.EAST);
		
		lCardArea=new JPanel();
		lCardArea.setPreferredSize(new Dimension(950,400));
		lowerPanel.add(lCardArea,BorderLayout.WEST);
		lCardArea.setBackground(Color.YELLOW);
		
		uCardArea=new JPanel();
		uCardArea.setPreferredSize(new Dimension(950,400));
		upperPanel.add(uCardArea,BorderLayout.WEST);
		uCardArea.setBackground(Color.YELLOW);
		
		lCardArea.setLayout(new BorderLayout());
		
		lowerHand=new JPanel();
		lowerHand.setPreferredSize(new Dimension(950,200));
		lCardArea.add(lowerHand,BorderLayout.SOUTH);
		lowerHand.setBackground(Color.MAGENTA);
		lowerHand.setAlignmentX(CENTER_ALIGNMENT);
		lowerHand.setAlignmentY(CENTER_ALIGNMENT);
		
		uCardArea.setLayout(new BorderLayout());
		
		upperHand=new JPanel();
		upperHand.setPreferredSize(new Dimension(950,200));
		uCardArea.add(upperHand,BorderLayout.NORTH);
		upperHand.setBackground(Color.MAGENTA);
		upperHand.setAlignmentX(CENTER_ALIGNMENT);
		upperHand.setAlignmentY(CENTER_ALIGNMENT);
		
		
		lowerField=new JPanel();
		lowerField.setPreferredSize(new Dimension(950,200));
		lCardArea.add(lowerField,BorderLayout.NORTH);
		lowerField.setBackground(Color.black);
		lowerField.setAlignmentX(CENTER_ALIGNMENT);
		lowerField.setAlignmentY(CENTER_ALIGNMENT);
		
		
			
		upperField=new JPanel();
		upperField.setPreferredSize(new Dimension(950,200));
		uCardArea.add(upperField,BorderLayout.SOUTH);
		upperField.setBackground(Color.black);
		upperField.setAlignmentX(CENTER_ALIGNMENT);
		upperField.setAlignmentY(CENTER_ALIGNMENT);
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
	public JPanel getLowerHand() {
		return lowerHand;
	}
	public void setLowerHand(JPanel lowerHand) {
		this.lowerHand = lowerHand;
	}
	public JPanel getUpperHand() {
		return upperHand;
	}
	public void setUpperHand(JPanel upperHand) {
		this.upperHand = upperHand;
	}
	public JPanel getLowerField() {
		return lowerField;
	}
	public void setLowerField(JPanel lowerField) {
		this.lowerField = lowerField;
	}
	public JPanel getUpperField() {
		return upperField;
	}
	public void setUpperField(JPanel upperField) {
		this.upperField = upperField;
	}
	public CardInfoPanel getCardView() {
		return cardView;
	}
	public void setCardView(CardInfoPanel cardView) {
		this.cardView = cardView;
	}
	public static void main(String[] args) {
		
		//@SuppressWarnings("notUsed")
		GameView g=new GameView();
	}
}
