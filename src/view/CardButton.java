package view;

import java.awt.Dimension;

import javax.swing.JButton;

import model.cards.Card;



public class CardButton extends JButton{
	private Card card;
	public CardButton() {
		setPreferredSize(new Dimension(90,150));
	}
	public CardButton(Card card) {
		super();
		setPreferredSize(new Dimension(90,150));
		this.card = card;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	
	
	
}
