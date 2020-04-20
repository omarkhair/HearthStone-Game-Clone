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
		bar.setString(value + "");
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
			CardButton b = new CardButton();
			b.setText(c.getName());
			b.addActionListener(this);
			heroHand.add(b);
			panel.add(b);
		}
	}

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		Controller c = new Controller(new Mage(), new Mage());
	}

	@Override
	public void onGameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) (e.getSource());
		if (b instanceof CardButton) {
			CardButton cb=(CardButton)b;
			ArrayList<CardButton> curHand;
			ArrayList<CardButton> curField;
			ArrayList<CardButton> oppHand;
			ArrayList<CardButton> oppField;
			
			
			if (game.getCurrentHero() == lower) {
				curHand = hand1;
				curField = field1;
				oppHand = hand2;
				oppField = field2;
			} else {
				curHand = hand2;
				curField = field2;
				oppHand = hand1;
				oppField = field1;
			}
			// ............
			if (curHand.contains(cb)) {
				
				monitorCard(false,true,curHand.indexOf(cb),game.getCurrentHero().getHand());
			} else if (curField.contains(cb)) {
				monitorCard(true,false,curField.indexOf(cb),game.getCurrentHero().getField());
			} else if(oppHand.contains(cb)){
				monitorCard(false,false,oppHand.indexOf(cb),game.getOpponent().getHand());
			} else if(oppField.contains(cb)) {
				monitorCard(false,false,oppField.indexOf(cb),game.getOpponent().getField());
			} else {
				System.out.println("Error");
			}
		}
		else {
			gameView.getCardView().setVisible(false);
		}
	}

	private void monitorCard(boolean attack, boolean play, int ind, ArrayList al) {
		gameView.getCardView().setVisible(true);
		Card c=(Card)al.get(ind);
		//TODO sora
		gameView.getCardView().getAttack().setVisible(attack);
		gameView.getCardView().getPlay().setVisible(play);
	}

	
}
