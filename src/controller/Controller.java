package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import engine.*;
import exceptions.*;
import model.cards.*;
import model.cards.minions.Minion;
import model.cards.spells.*;
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

	private String defaultMessage;
	private boolean onAttackMode;
	private boolean spellOnMinion;
	private boolean spellOnHero;
	private Card card;

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
		gameView.getEndTurn().addActionListener(this);

		modifyHeroPanel(lower);
		modifyHeroPanel(upper);
		hand1 = new ArrayList<CardButton>();
		hand2 = new ArrayList<CardButton>();
		field1 = new ArrayList<CardButton>();
		field2 = new ArrayList<CardButton>();

		buildHand(lower);
		buildHand(upper);
		gameView.revalidate();
		gameView.repaint();
		onAttackMode = false;
		spellOnMinion=false;
		spellOnHero=false;
		heroImageAction(lower);
		heroImageAction(upper);
		setDefaultMessage();
	}

	private void heroImageAction(Hero hero) {
		HeroPanel panel;
		if (hero == lower)
			panel = gameView.getLowerHero();
		else
			panel = gameView.getUpperHero();
		panel.getHeroImage().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (onAttackMode) {
					Minion attacker = (Minion) gameView.getCardView().getCard();

					try {
						game.getCurrentHero().attackWithMinion(attacker, hero);
						onAttackMode=false;
						gameView.getCardView().setVisible(false);
						rebuildAll();
					} catch (CannotAttackException | NotYourTurnException | TauntBypassException | NotSummonedException
							| InvalidTargetException e1) {
						setDialogueTextwithTimer(e1.getMessage());
					}

				}else if (spellOnHero) {
					HeroTargetSpell spell = (HeroTargetSpell) gameView.getCardView().getCard();
					try {
						game.getCurrentHero().castSpell(spell, hero);
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						setDialogueTextwithTimer(e1.getMessage());
					}

				}

			}
		});
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
		modifyHealth(hero);
		hpanel.getHeroPower().addActionListener(this);
		// System.out.println(hero.getClass().toString().substring(19));
	}

	private String pathOfImage(Hero hero) {
		String s = "images/";
		s += hero.getName();
		s += ".png";
		// System.out.println(s);
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

	private void modifyHealth(Hero hero) {
		int value = hero.getCurrentHP();
		JProgressBar bar;
		if (hero == lower) {
			bar = gameView.getLowerHero().getHealth();
		} else {
			bar = gameView.getUpperHero().getHealth();
		}
		bar.setValue((int) (value * 100.0 / 30.0));
		bar.setString(value + "/30");
	}

	private void buildHand(Hero h) {
		ArrayList<CardButton> heroHand;
		JPanel panel;
		if (h == lower) {
			heroHand = hand1;
			panel = gameView.getLowerHand();
		} else {
			heroHand = hand2;
			panel = gameView.getUpperHand();
		}
		panel.removeAll();
		heroHand.clear();
		for (Card c : h.getHand()) {
			CardButton b = new CardButton(c);
			b.setText(c.getName());
			b.addActionListener(this);
			heroHand.add(b);
			panel.add(b);
		}
	}

	private void buildField(Hero h) {
		ArrayList<CardButton> heroField;
		JPanel panel;
		if (h == lower) {
			heroField = field1;
			panel = gameView.getLowerField();
		} else {
			heroField = field2;
			panel = gameView.getUpperField();
		}
		panel.removeAll();
		heroField.clear();
		for (Card c : h.getField()) {
			CardButton b = new CardButton(c);
			b.setText(c.getName());
			b.addActionListener(this);
			heroField.add(b);
			panel.add(b);
		}
	}

	@Override
	public void onGameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Card inView = gameView.getCardView().getCard();
		JButton b = (JButton) (e.getSource());
		if (b instanceof CardButton) {
			CardButton cb = (CardButton) b;
			if (onAttackMode) {
				try {
					if (!(cb.getCard() instanceof Minion))
						throw new InvalidTargetException("You can only attack opponent field cards or hero");
					game.getCurrentHero().attackWithMinion((Minion) inView, (Minion) cb.getCard());
					rebuildAll();
					onAttackMode = false;
					gameView.getCardView().setVisible(false);
				} catch (CannotAttackException | NotYourTurnException | TauntBypassException | InvalidTargetException
						| NotSummonedException e1) {
					setDialogueTextwithTimer(e1.getMessage());

				}

			} else if (spellOnMinion) {
				try {
					if (!(cb.getCard() instanceof Minion))
						throw new InvalidTargetException("You can only cast this spell on opponent field cards");
					if (inView instanceof MinionTargetSpell) {
						game.getCurrentHero().castSpell((MinionTargetSpell) inView, (Minion) cb.getCard());
					}else if(inView instanceof LeechingSpell){
						game.getCurrentHero().castSpell((LeechingSpell) inView, (Minion) cb.getCard());
					}
				} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
					setDialogueTextwithTimer(e1.getMessage());
				}
				rebuildAll();
				spellOnMinion = false;
				gameView.getCardView().setVisible(false);

			} else {

				if (game.getCurrentHero().getHand().contains(cb.getCard())) {
					monitorCard(false, true, cb);
				} else if (game.getCurrentHero().getField().contains(cb.getCard())) {
					monitorCard(true, false, cb);
				} else if (game.getOpponent().getHand().contains(cb.getCard())) {
					gameView.getCardView().setVisible(false);
				} else if (game.getOpponent().getField().contains(cb.getCard())) {
					monitorCard(false, false, cb);
				} else {
					System.out.println("Error");
				}
			}
		} else if (b.getText().equals("Play")) {
			Card card = gameView.getCardView().getCard();
			CardButton cardButton = gameView.getCardView().getCardButton();
			playCard(card, cardButton);
			

		} else if (b.getText().equals("Attack")) {
			if (onAttackMode) {
				onAttackMode = false;
				setDefaultMessage();
			} else {
				Card card = gameView.getCardView().getCard();
				CardButton cardButton = gameView.getCardView().getCardButton();
				defaultMessage = "Choose a Minion or Hero to attack";
				setDialogueText(defaultMessage);
				onAttackMode = true;
			}
		} else if (b.getText().equals("End Turn")) {
			boolean burned = false;

			try {
				game.endTurn();

			} catch (FullHandException e1) {
				monitorBurnedCard(e1.getBurned());
				burned = true;
			} catch (CloneNotSupportedException e1) {
				System.out.println("ayhabal");
			}
			onAttackMode = false;
			setDefaultMessage();
			rebuildAll();
			if (burned)
				gameView.getCardView().setVisible(true);
			else
				gameView.getCardView().setVisible(false);
		} else if (b.getText().equals("Hero Power")) {
			gameView.getCardView().setVisible(false);
			onAttackMode = false;
			setDefaultMessage();
		} else {
			gameView.getCardView().setVisible(false);
			System.out.println("ERROR");
		}
	}

	public void setDefaultMessage() {
		String message = lower == game.getCurrentHero() ? "Player 1 turn" : "Player 2 turn";
		gameView.getDialogue().setText(message);
		defaultMessage = message;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDialogueTextwithTimer(String s) {
		gameView.getDialogue().setText(s);
		Timer t = new Timer(3000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameView.getDialogue().setText(defaultMessage);
			}
		});
		t.setRepeats(false);
		t.start();

	}

	public void setDialogueText(String s) {
		gameView.getDialogue().setText(s);
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	private void monitorBurnedCard(Card burned) {
		CardButton cb = new CardButton(burned);
		monitorCard(false, false, cb);
		gameView.getCardView().setBackground(Color.RED);
	}

	private void playCard(Card card, CardButton cardButton) {
		if (card instanceof Minion) {
			try {
				game.getCurrentHero().playMinion((Minion) card);
				gameView.getCardView().setVisible(false);
			} catch (NotYourTurnException | NotEnoughManaException | FullFieldException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage(), "InfoBox: " + "Error",
//					JOptionPane.INFORMATION_MESSAGE);
				setDialogueTextwithTimer(e.getMessage());

			}
		} else if (card instanceof Spell) {
			if (card instanceof FieldSpell) {
				// game.getCurrentHero().castSpell((FieldSpell)card);
			} else if (card instanceof MinionTargetSpell) {
				spellOnMinion = true;
			} else if (card instanceof LeechingSpell) {
				spellOnMinion = true;
			} else if (card instanceof HeroTargetSpell) {
				spellOnHero=true;
			} else if (card instanceof AOESpell) {
				//game.getCurrentHero().castSpell((AOESpell)card,game.getOpponent().getField());
			}
		} else {
			System.out.println("neither minion nor spell");
		}
		rebuildAll();

	}

	private void rebuildAll() {
		buildHand(lower);
		buildField(lower);
		buildHand(upper);
		buildField(upper);
		modifyHeroPanel(lower);
		modifyHeroPanel(upper);
		gameView.revalidate();
		gameView.repaint();
//		gameView.getCardView().setVisible(false);
		// gameView.setDialogueText("");
	}

	private void monitorCard(boolean attack, boolean play, CardButton cb) {
		gameView.getCardView().setVisible(true);
		gameView.getCardView().setBackground(Color.cyan);
		Card c = cb.getCard();
		gameView.getCardView().setCard(c);
		gameView.getCardView().setCardButton(cb);
		// TODO sora
		gameView.getCardView().getAttack().setVisible(attack);
		gameView.getCardView().getPlay().setVisible(play);
		gameView.getCardView().getCardName().setText("<html>" + c.getName() + "<html>");
		gameView.getCardView().getCardInfo().setText(c.toString());
		gameView.getCardView().getCardInfo().setCaretPosition(0);
		ImageIcon imageIcon = new ImageIcon(
				new ImageIcon(pathofcardImage(c)).getImage().getScaledInstance(250, 300, Image.SCALE_DEFAULT));
		gameView.getCardView().getCardImage().setIcon(imageIcon);
	}

	public String pathofcardImage(Card c) {
		String s = "";
		s += "images" + "/" + c.getName() + ".png";
		// System.out.println(s);
		return s;
	}

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		Controller c = new Controller(new Mage(), new Mage());
	}

}
