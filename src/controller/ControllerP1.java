package controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
import view.CardButton;
import view.ClientHandler;
import view.GameView;
import view.HealthBar;
import view.HeroDeathView;
import view.HeroPanel;
import view.ManaBar;
import view.ThreadListener;

public class ControllerP1 implements GameListener, ActionListener, ThreadListener {
	private Game game;
	private GameView gameView;
	private ArrayList<CardButton> hand1;
	private ArrayList<CardButton> hand2;
	private ArrayList<CardButton> field1;
	private ArrayList<CardButton> field2;
	private Hero self;
	private Hero opposer;

	private String defaultMessage;
	private boolean onAttackMode;
	private boolean spellOnMinion;
	private boolean spellOnHero;
	private boolean onHeroPower;

	private int width;
	private int height;

	private Clip currentClip;

	private Socket s;
	private ClientHandler thread;
	private String opponentIP;
	int ps;

	public ControllerP1(Hero h1, Hero h2, Game game, int portSend, int portRecieve, String opponentIP) {
		ps = portSend;
		this.opponentIP = opponentIP;
		game.setListener(this);
		self = h1;
		opposer = h2;
		this.game = game;
		gameView = new GameView();
		gameView.getCardView().getPlay().addActionListener(this);
		gameView.getCardView().getAttack().addActionListener(this);
		gameView.getEndTurn().addActionListener(this);

		width = gameView.getWidth();
		height = gameView.getHeight();

		// System.out.println(gameView.getUpperHero().getHeroImage().getp);

		hand1 = new ArrayList<CardButton>();
		hand2 = new ArrayList<CardButton>();
		field1 = new ArrayList<CardButton>();
		field2 = new ArrayList<CardButton>();

		rebuildAll(false);

		onAttackMode = false;
		spellOnMinion = false;
		spellOnHero = false;
		onHeroPower = false;
		heroImageAction(self);
		heroImageAction(opposer);
		setDefaultMessage();
		gameView.getLowerHero().getHeroPower().addActionListener(this);
		// gameView.getUpperHero().getHeroPower().addActionListener(this);
		sound();

		thread = new ClientHandler(this, portRecieve);
		thread.start();
		// gameView.getLowerHero().getHeroPower().setToolTipText("kajdbfwbfiuwbefiuw");
	}

	private void heroImageAction(Hero hero) {
		HeroPanel panel;
		if (hero == self)
			panel = gameView.getLowerHero();
		else
			panel = gameView.getUpperHero();
		panel.getHeroImage().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (onAttackMode) {
					Minion attacker = (Minion) gameView.getCardView().getCard();

					try {
						game.getCurrentHero().attackWithMinion(attacker, panel.getHero());
						playSound("sounds/Attack.wav");
						onAttackMode = false;
						setDefaultMessage();
						gameView.getCardView().setVisible(false);
						rebuildAll(true);
					} catch (CannotAttackException | NotYourTurnException | TauntBypassException | NotSummonedException
							| InvalidTargetException e1) {
						setDialogueTextwithTimer(e1.getMessage());
					}

				} else if (spellOnHero) {
					HeroTargetSpell spell = (HeroTargetSpell) gameView.getCardView().getCard();
					try {
						game.getCurrentHero().castSpell(spell, panel.getHero());
						spellOnHero = false;
						spellOnMinion = false;
						setDefaultMessage();
						rebuildAll(true);
					} catch (NotYourTurnException | NotEnoughManaException e1) {
						setDialogueTextwithTimer(e1.getMessage());
					}

				} else if (onHeroPower) {
					Hero cur = game.getCurrentHero();
					try {
						if (cur instanceof Mage) {

							((Mage) cur).useHeroPower(panel.getHero());
							playSound("sounds/Attack.wav");
							onHeroPower = false;
							setDefaultMessage();
							rebuildAll(true);
						} else {
							((Priest) cur).useHeroPower(panel.getHero());
							onHeroPower = false;
							setDefaultMessage();
							rebuildAll(true);
						}
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						setDialogueTextwithTimer(e1.getMessage());
					}

				}

			}
		});
	}

	private void modifyHeroPanel(Hero hero) {
		HeroPanel hpanel;
		String s;
		if (hero == self) {
			hpanel = gameView.getLowerHero();
			s = "images/design/LowerHero.png";
		} else {
			hpanel = gameView.getUpperHero();
			s = "images/design/LowerHero.png";
		}
		hpanel.setHero(hero);
		// TODO
		int w = gameView.getUpperHero().getHeroImage().getPreferredSize().width;
		int h = gameView.getUpperHero().getHeroImage().getPreferredSize().height;
		int w2 = gameView.getUpperHero().getWidth();
		int h2 = gameView.getUpperHero().getHeight();
		ImageIcon i = new ImageIcon(new ImageIcon(s).getImage().getScaledInstance(w2, h2, Image.SCALE_DEFAULT));
		hpanel.getBackGround().setIcon(i);
		ImageIcon imageIcon = new ImageIcon(
				new ImageIcon(pathOfImage(hero)).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
		hpanel.getHeroImage().setIcon(imageIcon);
		hpanel.getHeroType().setText("Cards left:" + hero.getDeck().size());
		String icon = "images/HeroPower/";
		if (hero instanceof Mage) {
			icon += "Mage";
		} else if (hero instanceof Hunter) {
			icon += "Hunter";
		} else if (hero instanceof Paladin) {
			icon += "Paladin";
		} else if (hero instanceof Priest) {
			icon += "Priest";
		} else {
			icon += "Warlock";
		}
		i = new ImageIcon(
				new ImageIcon(icon + ".png").getImage().getScaledInstance(h2 / 4, h2 / 4, Image.SCALE_DEFAULT));
		hpanel.getHeroPower().setIcon(i);
		i = new ImageIcon(new ImageIcon(icon + " Disabled.png").getImage().getScaledInstance(h2 / 4, h2 / 4,
				Image.SCALE_DEFAULT));
		hpanel.getHeroPower().setDisabledIcon(i);
		hpanel.getHeroPower().setHorizontalAlignment(JButton.CENTER);
		modifyMana(hero);
		modifyHealth(hero);

		if (hero.equals(game.getCurrentHero()) && !hero.isHeroPowerUsed())
			hpanel.getHeroPower().setEnabled(true);
		else
			hpanel.getHeroPower().setEnabled(false);

		// System.out.println(hero.getClass().toString().substring(19));
	}

	private String pathOfImage(Hero hero) {
		String s = "images/Heros/";
		s += hero.getName();
		s += ".png";
		// System.out.println(s);
		return s;
	}

	private void modifyMana(Hero hero) {
		ManaBar bar;
		if (hero == self) {
			bar = gameView.getLowerHero().getManaBar();
		} else {
			bar = gameView.getUpperHero().getManaBar();
		}
		bar.setMana(hero.getCurrentManaCrystals(), hero.getTotalManaCrystals());
	}

	private void modifyHealth(Hero hero) {
		int value = hero.getCurrentHP();
		HealthBar bar;
		if (hero == self) {
			bar = gameView.getLowerHero().getHealth();
		} else {
			bar = gameView.getUpperHero().getHealth();
		}
		bar.setHealth(value);
	}

	private void buildHand(Hero h) {
		ArrayList<CardButton> heroHand;
		JLabel back;
		if (h == self) {
			heroHand = hand1;
			back = gameView.getLowerHand();
		} else {
			heroHand = hand2;
			back = gameView.getUpperHand();
		}
		back.removeAll();
		heroHand.clear();
		for (Card c : h.getHand()) {
			// TODO
			CardButton b = new CardButton(c, width, height);
			String s = "images/design/covered.png";
			if (h == self) {
				s = pathofcardImage(c);
				b.addActionListener(this);
			}
			ImageIcon i = new ImageIcon(
					new ImageIcon(s).getImage().getScaledInstance(b.getW(), b.getH(), Image.SCALE_DEFAULT));
			b.setIcon(i);
			heroHand.add(b);
			// b.setAlignmentX(Component.CENTER_ALIGNMENT);
			// b.setH
			back.add(b);
		}
	}

	private void buildField(Hero h) {
		ArrayList<CardButton> heroField;
		JLabel panel;
		if (h == self) {
			heroField = field1;
			panel = gameView.getLowerField();
		} else {
			heroField = field2;
			panel = gameView.getUpperField();
		}
		panel.removeAll();
		heroField.clear();
		for (Card c : h.getField()) {
			CardButton b = new CardButton(c, width, height);
			String s = pathofcardImage(c);
			ImageIcon i = new ImageIcon(
					new ImageIcon(s).getImage().getScaledInstance(b.getW(), b.getH(), Image.SCALE_DEFAULT));
			b.setIcon(i);
			// b.setText(c.getName());
			b.addActionListener(this);
			heroField.add(b);
			panel.add(b);
		}
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
					playSound("sounds/Attack.wav");
					rebuildAll(true);
					onAttackMode = false;
					setDefaultMessage();
					// System.out.println("WHITE");
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
						if (opposer.getField().contains((Minion) cb.getCard())
								|| self.getField().contains((Minion) cb.getCard()))
							game.getCurrentHero().castSpell((MinionTargetSpell) inView, (Minion) cb.getCard());
						else {
							throw new InvalidTargetException("You can only cast this spell on field minions");
						}
					} else if (inView instanceof LeechingSpell) {
						game.getCurrentHero().castSpell((LeechingSpell) inView, (Minion) cb.getCard());
					}
					rebuildAll(true);
					spellOnMinion = false;
					spellOnHero = false;
					setDefaultMessage();
					gameView.getCardView().setVisible(false);
				} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
					setDialogueTextwithTimer(e1.getMessage());
				}

			} else if (onHeroPower) {
				try {
					Hero cur = game.getCurrentHero();
					if (!(cb.getCard() instanceof Minion) || game.getCurrentHero().getHand().contains(cb.getCard())
							|| game.getOpponent().getHand().contains(cb.getCard()))
						throw new InvalidTargetException("You can only use this Hero power on Field Minions");
					if (cur instanceof Mage) {

						((Mage) cur).useHeroPower((Minion) cb.getCard());
						playSound("sounds/Attack.wav");
					} else {
						((Priest) cur).useHeroPower((Minion) cb.getCard());
					}
					rebuildAll(true);
					onHeroPower = false;
					setDefaultMessage();
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException
						| InvalidTargetException e1) {
					setDialogueTextwithTimer(e1.getMessage());
				}
			} else {

				if (self.getHand().contains(cb.getCard())) {
					if (self == game.getCurrentHero())
						monitorCard(false, true, cb);
					else
						monitorCard(false, false, cb);
				} else if (self.getField().contains(cb.getCard())) {
					if (self == game.getCurrentHero())
						monitorCard(true, false, cb);
					else
						monitorCard(false, false, cb);
				} else if (opposer.getHand().contains(cb.getCard())) {
					gameView.getCardView().setVisible(false);
				} else if (opposer.getField().contains(cb.getCard())) {
					monitorCard(false, false, cb);
				} else {
					System.out.println("Error");
				}
			}
		} else if (b.getActionCommand().equals("Play")) {
			if (spellOnMinion || spellOnHero) {
				spellOnMinion = false;
				spellOnHero = false;
				setDefaultMessage();
			} else {
				Card card = gameView.getCardView().getCard();
				CardButton cardButton = gameView.getCardView().getCardButton();
				playCard(card, cardButton);
			}

		} else if (b.getActionCommand().equals("Attack")) {
			if (onAttackMode) {
				onAttackMode = false;
				setDefaultMessage();
			} else {
				Card card = gameView.getCardView().getCard();
				CardButton cardButton = gameView.getCardView().getCardButton();
				if (((Minion) card).isAttacked())
					setDialogueTextwithTimer("This minion has attacked before!");
				else if (((Minion) card).isSleeping())
					setDialogueTextwithTimer("Give this minion a turn to get ready!");
				else {
					defaultMessage = "Choose a Minion or Hero to attack";
					setDialogueText(defaultMessage);
					onAttackMode = true;
				}
			}
		} else if (b.getActionCommand().equals("End Turn")) {
			boolean burned = false;
			Card burnedCard = null;
			try {
				game.endTurn();

			} catch (FullHandException e1) {
				burnedCard = e1.getBurned();
				monitorBurnedCard(burnedCard);
				burned = true;
			} catch (CloneNotSupportedException e1) {
				System.out.println("ayhabal");
			}
			onAttackMode = false;
			spellOnHero = false;
			spellOnMinion = false;
			onHeroPower = false;
			setDefaultMessage();

			rebuildAll(true);
			if (burned) {
				gameView.getCardView().setVisible(true);
				sendOutput(burnedCard);
			} else
				gameView.getCardView().setVisible(false);
		} else if (b.getActionCommand().equals("Hero Power")) {
			gameView.getCardView().setVisible(false);
			onAttackMode = false;
			spellOnHero = false;
			spellOnMinion = false;
			setDefaultMessage();
			Hero cur = game.getCurrentHero();
			try {
				if (cur instanceof Hunter || cur instanceof Warlock || cur instanceof Paladin) {

					cur.useHeroPower();

				} else {
					if (onHeroPower)
						onHeroPower = false;
					else {
						onHeroPower = true;
						defaultMessage = "Choose a Minion or Hero";
						setDialogueText(defaultMessage);
					}
				}
				// System.out.println("heropower used");
				rebuildAll(true);

			} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException | FullHandException
					| FullFieldException | CloneNotSupportedException e1) {

				setDialogueTextwithTimer(e1.getMessage());
			}

		} else {
			gameView.getCardView().setVisible(false);
			System.out.println("ERROR1");
		}
	}

	public void setDefaultMessage() {
		String message = self == game.getCurrentHero() ? "Your turn" : "Opponent turn";
		gameView.getDialogue().setText(message);
		defaultMessage = message;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDialogueTextwithTimer(String s) {
		gameView.getDialogue().setForeground(Color.red);
		gameView.getDialogue().setText(s);
		Timer t = new Timer(3000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameView.getDialogue().setForeground(Color.lightGray);
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
		gameView.getDialogue().setForeground(Color.lightGray);
		this.defaultMessage = defaultMessage;
	}

	private void playCard(Card card, CardButton cardButton) {
		if (card instanceof Minion) {
			try {
				game.getCurrentHero().playMinion((Minion) card);
				gameView.getCardView().setVisible(false);
				rebuildAll(true);
			} catch (NotYourTurnException | NotEnoughManaException | FullFieldException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage(), "InfoBox: " + "Error",
//					JOptionPane.INFORMATION_MESSAGE);
				setDialogueTextwithTimer(e.getMessage());

			}
		} else if (card instanceof Spell) {
			try {
				game.validateManaCost(card);
				if (card instanceof FieldSpell) {
					game.getCurrentHero().castSpell((FieldSpell) card);
					gameView.getCardView().setVisible(false);
				} else if (card instanceof MinionTargetSpell && card instanceof HeroTargetSpell) {
					spellOnMinion = true;
					spellOnHero = true;
					defaultMessage = "Choose a Minion or Hero to cast the spell";
					setDialogueText(defaultMessage);
				} else if (card instanceof MinionTargetSpell) {
					spellOnMinion = true;
					defaultMessage = "Choose a Minion to cast the spell";
					setDialogueText(defaultMessage);
				} else if (card instanceof LeechingSpell) {
					spellOnMinion = true;
					defaultMessage = "Choose a Minion to cast the spell";
					setDialogueText(defaultMessage);
				} else if (card instanceof HeroTargetSpell) {
					spellOnHero = true;
					defaultMessage = "Choose a Hero to cast the spell";
					setDialogueText(defaultMessage);
				} else if (card instanceof AOESpell) {
					game.getCurrentHero().castSpell((AOESpell) card, game.getOpponent().getField());
					gameView.getCardView().setVisible(false);
				}

				rebuildAll(true);
				if (card instanceof MinionTargetSpell || card instanceof HeroTargetSpell
						|| card instanceof LeechingSpell)
					gameView.getCardView().setVisible(true);
			} catch (NotYourTurnException | NotEnoughManaException e) {
				setDialogueTextwithTimer(e.getMessage());

			}

		} else {
			System.out.println("neither minion nor spell");
		}

	}

	private void rebuildAll(boolean send) {
		// System.out.println("in rebuild");
		buildHand(self);
		buildField(self);
		buildHand(opposer);
		buildField(opposer);
		modifyHeroPanel(self);
		modifyHeroPanel(opposer);
		gameView.revalidate();
		gameView.repaint();
		// System.out.println("im here "+game.getCurrentHero().getClass()+"
		// "+self.getClass());
		if (self.equals(game.getCurrentHero())) {
			gameView.getEndTurn().setEnabled(true);
			// System.out.println("why "+game.getCurrentHero().getClass());
		} else
			gameView.getEndTurn().setEnabled(false);
		gameView.getCardView().setVisible(false);
		if (send) {
			// System.out.println("rebuild");
			sendOutput(game);
		}
		// gameView.setDialogueText("");
	}

	private void monitorCard(boolean attack, boolean play, CardButton cb) {
		gameView.getCardView().getCardInfo().setForeground(Color.LIGHT_GRAY);
		gameView.getCardView().getCardName().setForeground(Color.lightGray);
		gameView.getCardView().setVisible(true);
		gameView.getCardView().setBackground(Color.black);
		gameView.getCardView().setBackGroundImage("images/design/CardViewBack.png");
		Card c = cb.getCard();
		gameView.getCardView().setCard(c);
		gameView.getCardView().setCardButton(cb);
		// TODO sora
		gameView.getCardView().getAttack().setVisible(attack);
		gameView.getCardView().getPlay().setVisible(play);
		gameView.getCardView().getCardName().setText("<html>" + c.getName() + "<html>");
		// gameView.getCardView().getCardInfo().setText(c.toString());
		gameView.getCardView().getCardInfo().setText("<html><body>" + c.toString() + "</body></html>");
		// gameView.getCardView().getCardInfo().setCaretPosition(0);
		int w = gameView.getCardView().getCardImage().getPreferredSize().width;
		int h = gameView.getCardView().getCardImage().getPreferredSize().height;
		ImageIcon imageIcon = new ImageIcon(
				new ImageIcon(pathofcardImage(c)).getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
		gameView.getCardView().getCardImage().setIcon(imageIcon);
	}

	private void monitorBurnedCard(Card burned) {
		CardButton cb = new CardButton(burned, width, height);
		monitorCard(false, false, cb);
		gameView.getCardView().setBackground(Color.RED);
		// gameView.getCardView().getCardInfo().setForeground(Color.white);
		// gameView.getCardView().getCardName().setForeground(Color.white);
		gameView.getCardView().setBackGroundImage("images/design/BurnedBack.jpg");
	}

	public String pathofcardImage(Card c) {
		String s = "";
		String name = c.getName();
		if (name.equals("Shadow Word: Death"))
			name = "Shadow Word Death";
		s += "images/Minions/" + name + ".png";
		// System.out.println(s);
		return s;
	}

	@Override
	public void onGameOver() {
		if (self.getCurrentHP() <= 0) {
			new HeroDeathView(self, opposer, true);
		} else if (opposer.getCurrentHP() <= 0) {
			new HeroDeathView(self, opposer, false);
		} else {
			System.out.println("ERROR2");
		}
		gameView.setVisible(false);
		currentClip.stop();
		thread.terminate();

	}

	private Clip playSound(String s) {
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(s).getAbsoluteFile());

			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			return clip;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.out.println("Error");
		}
		return null;
	}

	private void sound() {
		currentClip = playSound("sounds/gameAudioBegin.wav");
		Timer timer = new Timer(76110, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				repeatSound();
			}

		});
		timer.setRepeats(false);
		timer.start();

		// currentClip.addLineListener(listener);

	}

	private void repeatSound() {
		currentClip = playSound("sounds/gameAudio.wav");
		currentClip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		try {
			Game g = new Game(new Mage(), new Paladin());
			ControllerP1 c = new ControllerP1(g.getCurrentHero(), g.getOpponent(), g, 1080, 1081, "127.0.0.1");
			ControllerP1 c2 = new ControllerP1(g.getOpponent(), g.getCurrentHero(), g, 1081, 1080, "127.0.0.1");
		} catch (FullHandException | CloneNotSupportedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		 InetAddress inetAddress = InetAddress.getLocalHost();
//	        System.out.println("IP Address:- " + inetAddress.getHostAddress());
//	        System.out.println("Host Name:- " + inetAddress.getHostName());

		// ControllerP1 c2 = new ControllerP1( g.getOpponent(), g.getCurrentHero(),g);
	}

	@Override
	public void onGettingInput(ObjectInputStream ois) {
		// System.out.println("ALMOST "+ self.getClass()+" before
		// "+game.getCurrentHero().getClass());
		try {
			Object inp = ois.readObject();
			if (inp instanceof Game) {
				game = (Game) inp;
				game.setListener(this);

				if (game.change) {
					self = game.getCurrentHero();
					opposer = game.getOpponent();
					game.change = false;
					setDefaultMessage();
				} else {
					opposer = game.getCurrentHero();
					self = game.getOpponent();
				}
				checkGameOver();

				// System.out.println("new game "+game.getCurrentHero().getClass());
				rebuildAll(false);
			} else {
				Card burned = (Card) inp;
				monitorBurnedCard(burned);
				gameView.getCardView().setVisible(true);
			}
//			if(game.getCurrentHero().equals(self)||game.getCurrentHero().equals(opposer))
//				System.out.println("true");
//			else
			// System.out.println(false+" "+self.getClass()+" "+
			// game.getCurrentHero().getClass());
//				try {
//					thread.wait();
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void checkGameOver() {
		if (game.getCurrentHero().getCurrentHP() == 0 || game.getOpponent().getCurrentHP() == 0) {
			onGameOver();
		}

	}

	public void sendOutput(Object o) {
		try {
			s = new Socket(opponentIP, ps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(o);
			// System.out.println("sending op "+self.getClass()+ "
			// "+game.getCurrentHero().getClass());
			// thread.notify();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
