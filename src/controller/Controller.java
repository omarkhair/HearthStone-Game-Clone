package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import engine.*;
import exceptions.*;
import model.cards.*;
import model.cards.minions.Minion;
import model.cards.spells.Spell;
import model.heroes.*;
import view.*;

public class Controller implements GameListener, ActionListener {
	private Game game;
	private GameView gameView;
	private ArrayList<CardButton> hand1;
	private ArrayList<CardButton> hand2;
	private ArrayList<CardButton> field1;
	private ArrayList<CardButton> field2;
	private Hero lower;
	private Hero upper;

	public Controller(Hero h1, Hero h2) {
		try {
			game = new Game(h1, h2);
		} catch (FullHandException | CloneNotSupportedException e) {
			// for us
			System.out.println("inappropriatly catched error");
		}
		lower = game.getCurrentHero();
		upper = game.getOpponent();

		gameView = new GameView();
		gameView.getCardView().getPlay().addActionListener(this);
		gameView.getCardView().getAttack().addActionListener(this);

		modifyHeroPanel(lower);
		modifyHeroPanel(upper);
		hand1 = new ArrayList<CardButton>();
		hand2 = new ArrayList<CardButton>();
		field1 = new ArrayList<CardButton>();
		field2 = new ArrayList<CardButton>();

		generateHand(lower);
		generateHand(upper);
		gameView.revalidate();
		gameView.repaint();

	}

	private void modifyHeroPanel(Hero hero) {
		HeroPanel hpanel;
		if (hero == lower) {
			hpanel = gameView.getLowerHero();
		} else {
			hpanel = gameView.getUpperHero();
		}
		ImageIcon imageIcon = new ImageIcon(
				new ImageIcon(pathOfImage(hero)).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
		hpanel.getHeroImage().setIcon(imageIcon);
		hpanel.getHeroType().setText(hero.getClass().toString().substring(19));
		modifyMana(hero);
		hpanel.getHeroPower().addActionListener(this);
		// System.out.println(hero.getClass().toString().substring(19));
	}

	private String pathOfImage(Hero hero) {
		String s = "images/";
		s += hero.getName();
		s += ".png";
		System.out.println(s);
		return s;
	}

	private void modifyMana(Hero hero) {
		int value = hero.getCurrentManaCrystals();
		JProgressBar bar;
		if (hero == lower) {
			bar = gameView.getLowerHero().getManaBar();
		} else {
			bar = gameView.getUpperHero().getManaBar();
		}
		bar.setValue(value * 10);
		bar.setString(value + "/" + hero.getTotalManaCrystals());
	}

	private void generateHand(Hero h) {
		ArrayList<CardButton> heroHand;
		JPanel panel;
		if (h == lower) {
			heroHand = hand1;
			panel = gameView.getLowerHand();
		} else {
			heroHand = hand2;
			panel = gameView.getUpperHand();
		}
		for (Card c : h.getHand()) {
			CardButton b = new CardButton(c);
			b.setText(c.getName());
			b.addActionListener(this);
			heroHand.add(b);
			panel.add(b);
		}
	}

	@Override
	public void onGameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) (e.getSource());
		if (b instanceof CardButton) {
			CardButton cb = (CardButton) b;
			if (game.getCurrentHero().getHand().contains(cb.getCard())) {
				monitorCard(false, true, cb);
			} else if (game.getCurrentHero().getField().contains(cb.getCard())) {
				monitorCard(true, false, cb);
			} else if (game.getOpponent().getHand().contains(cb.getCard())) {
				// monitorCard(false,false,oppHand.indexOf(cb),game.getOpponent().getHand());
			} else if (game.getOpponent().getField().contains(cb.getCard())) {
				monitorCard(false, false, cb);
			} else {
				System.out.println("Error");
			}
		} else if (b.getText().equals("Play")) {
			Card card = gameView.getCardView().getCard();
			CardButton cardButton = gameView.getCardView().getCardButton();
			playCard(card, cardButton);
		} else {
			gameView.getCardView().setVisible(false);

		}
	}

	private void playCard(Card card, CardButton cardButton) {
		System.out.println("in");
		System.out.println(card);
		if (card instanceof Minion) {
			System.out.println("inin");

		try {
			game.getCurrentHero().playMinion((Minion) card);
		} catch (NotYourTurnException | NotEnoughManaException | FullFieldException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "InfoBox: " + "ERRRRROOOOORR",
					JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		}
		else {
			game.getCurrentHero().castSpell((Spell) card);
		}

	}

	private void monitorCard(boolean attack, boolean play, CardButton cb) {
		gameView.getCardView().setVisible(true);
		Card c = cb.getCard();
		gameView.getCardView().setCard(c);
		gameView.getCardView().setCardButton(cb);
		// TODO sora
		gameView.getCardView().getAttack().setVisible(attack);
		gameView.getCardView().getPlay().setVisible(play);
		gameView.getCardView().getCardName().setText("<html>" + c.getName() + "<html>");
		gameView.getCardView().getCardInfo().setText(c.toString());
		ImageIcon imageIcon = new ImageIcon(
				new ImageIcon(pathofcardImage(c)).getImage().getScaledInstance(250, 300, Image.SCALE_DEFAULT));
		gameView.getCardView().getCardImage().setIcon(imageIcon);
	}

	public String pathofcardImage(Card c) {
		String s = "";
		s += "images" + "/" + c.getName() + ".png";
		System.out.println(s);
		return s;
	}

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		Controller c = new Controller(new Mage(), new Mage());
	}

}
