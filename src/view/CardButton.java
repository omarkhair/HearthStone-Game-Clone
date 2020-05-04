package view;

import java.awt.Dimension;

import javax.swing.JButton;

import model.cards.Card;



public class CardButton extends JButton{
	private Card card;
	private int w;
	private int h;
	public CardButton(int tw,int th) {
		w=tw*3/55;
		h=th*3/22;
		setPreferredSize(new Dimension(w,h));
		setContentAreaFilled(false);
		setBorderPainted(false);
	}
	public CardButton(Card card,int tw, int th) {
		w=tw*3/55;
		h=th*3/22;
		setPreferredSize(new Dimension(w,h));
		this.card = card;
		setContentAreaFilled(false);
		//setBorderPainted(false);
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	public int getW() {
		return w;
	}
	public int getH() {
		return h;
	}
	
	
	
}
