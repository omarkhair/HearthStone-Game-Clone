package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import view.ClientHandler;
import controller.ControllerP1;
import view.ThreadListener;
import engine.Game;
import exceptions.FullHandException;
import model.heroes.Hero;

public class MultiplayerView extends JFrame implements ActionListener, ThreadListener {
	private int width;
	private int height;
	private JLabel connectBG, mainIcon, ip;
	private HeroSelectionPanel heroPanel;
	private JButton wait, exit;
	private JButton send;
	private String yourIP, opponentIP;
	private ClientHandler thread;
	private Socket socket;
	private Hero opponentHero;
	final private int port1 = 2001, port2 = 2002, port3 = 2003, port4 = 2004;
	private String yourName, opponentName;
	private boolean waiting;
	private ImageIcon waitIcon, linkIcon;
	private Clip cur;

	public MultiplayerView() {
		init();
		try {
			InetAddress inetAddress;
			inetAddress = InetAddress.getLocalHost();
			yourIP = inetAddress.getHostAddress();
			yourName = inetAddress.getHostName();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLayeredPane pane = new JLayeredPane();
		pane.setLocation(0, 0);
		pane.setSize(width, height);

		add(pane);

		ip = new JLabel(yourIP);
//		ip.setSize(width, height);
		ip.setBounds((int) (width * 12.4 / 20), (int) (height * 2 / 5), width / 8, height / 10);
		ip.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
		ip.setForeground(new Color(49, 37, 15));

		mainIcon = new JLabel();
		mainIcon.setBounds((int) (width * 16.5 / 20), (int) (height * 1.2 / 5), height / 5, height / 5);
		mainIcon.setIcon(linkIcon);

		connectBG = new JLabel(new ImageIcon(new ImageIcon("images/design/connectBG.jpg").getImage()
				.getScaledInstance(width / 2, height, Image.SCALE_DEFAULT)));
		connectBG.setLocation(width / 2, 0);
		connectBG.setSize(width / 2, height);

		heroPanel = new HeroSelectionPanel(width, height);
		heroPanel.setLocation(0, 0);

		send = new JButton(new ImageIcon(new ImageIcon("images/design/send.png").getImage()
				.getScaledInstance(width * 3 / 10, height / 12, Image.SCALE_DEFAULT)));
		send.setBorderPainted(false);
		send.setContentAreaFilled(false);
		send.setFocusPainted(false);
		send.setBounds(width * 6 / 10, height * 13 / 20, width * 3 / 10, height / 12);
		send.addActionListener(this);
		send.setActionCommand("send");
		send.setDisabledIcon(new ImageIcon(new ImageIcon("images/design/sendDisabeled.png").getImage()
				.getScaledInstance(width * 3 / 10, height / 12, Image.SCALE_DEFAULT)));

		wait = new JButton(new ImageIcon(new ImageIcon("images/design/wait.png").getImage()
				.getScaledInstance(width * 3 / 10, height / 12, Image.SCALE_DEFAULT)));
		wait.setBounds(width * 6 / 10, height * 8 / 10, width * 3 / 10, height / 12);
		wait.setBorderPainted(false);
		wait.setContentAreaFilled(false);
		wait.setFocusPainted(false);
		wait.addActionListener(this);
		wait.setActionCommand("wait");

		exit = new JButton(new ImageIcon(new ImageIcon("images/design/EXIT.png").getImage().getScaledInstance(width / 8,
				height / 16, Image.SCALE_DEFAULT)));
		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);
		exit.setFocusPainted(false);
		exit.addActionListener(this);
		exit.setActionCommand("Quit");
		exit.setSize(new Dimension(width / 8, height / 16));
		exit.setLocation(width / 2 - exit.getWidth() / 2, height * 36 / 40);

		pane.add(heroPanel, 1, 0);
		pane.add(connectBG, 1, 0);
		pane.add(exit, 2, 0);
		pane.add(wait, 2, 0);
		pane.add(send, 2, 0);
		pane.add(mainIcon, 2, 0);
		pane.add(ip, 2, 0);
		sound();
	}

	public void init() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) dim.getWidth();
		height = (int) dim.getHeight();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		waitIcon = new ImageIcon(new ImageIcon("images/design/waitIcon.gif").getImage().getScaledInstance(height / 5,
				height / 5, Image.SCALE_DEFAULT));
		linkIcon = new ImageIcon(new ImageIcon("images/design/linkIcon.png").getImage().getScaledInstance(height / 5,
				height / 5, Image.SCALE_DEFAULT));
		;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "Quit") {
			int a = JOptionPane.showConfirmDialog(this, "Do you want to exit ", "EXIT Bar", JOptionPane.YES_NO_OPTION);
			if (a == JOptionPane.YES_OPTION)
				System.exit(0);
		} else if (e.getActionCommand() == "send") {
			opponentIP = JOptionPane.showInputDialog(this, "Enter your friend's ip address:");
			if (opponentIP == null || opponentIP.equals(""))
				return;
			try {
				sendOutput(yourIP + "\n" + yourName, port3);
				sendOutput(heroPanel.getChosen(), port3);
				thread = new ClientHandler(this, port4);
				thread.start();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "Wrong IP address.", "Alert", JOptionPane.WARNING_MESSAGE);
			}

		} else if (e.getActionCommand() == "wait") {
			if (!waiting) {
				thread = new ClientHandler(this, port3);
				thread.start();
				waiting = true;
				send.setEnabled(false);
				mainIcon.setIcon(waitIcon);
			} else {
				thread.terminate();
				//System.out.println(thread.isAlive());
				waiting = false;
				send.setEnabled(true);
				mainIcon.setIcon(linkIcon);
			}
			// System.out.println("WAITING");

		}
	}

	private void sound() {
		cur = playSound("sounds/SelectionAudio begin.wav");
		Timer timer = new Timer(26626, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (isVisible())
					repeatSound();
			}

		});
		timer.setRepeats(false);
		timer.start();

	}

	private void repeatSound() {
		cur = playSound("sounds/SelectionAudio.wav");
		cur.loop(Clip.LOOP_CONTINUOUSLY);
	}

	private Clip playSound(String s) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(s).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			return clip;
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		new MultiplayerView();
		new MultiplayerView();
	}

	@Override
	public void onGettingInput(ObjectInputStream obj) {
		Object inp;
		try {
			inp = obj.readObject();
			if (inp instanceof String) {
				String[] a = ((String) inp).split("\n");
				opponentIP = a[0];
				opponentName = a[1];
			} else if (inp instanceof Hero) {
				opponentHero = (Hero) inp;

				int a = JOptionPane.showConfirmDialog(this, "Accept Invitation from: " + opponentName, "Notification",
						JOptionPane.YES_NO_OPTION);
				if (a == JOptionPane.YES_OPTION)
					initializeGame();
			} else if (inp instanceof Game) {
				Game g = (Game) inp;
				if (g.change) {
					g.change = false;
					new ControllerP1(g.getCurrentHero(), g.getOpponent(), g, port2, port1, opponentIP);
				} else
					new ControllerP1(g.getOpponent(), g.getCurrentHero(), g, port2, port1, opponentIP);

				setVisible(false);
				cur.close();
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

	private void initializeGame() {
		try {
			Game g = new Game(opponentHero, heroPanel.getChosen());

			if (g.getCurrentHero().getName().equals(heroPanel.getChosen().getName())) {
				new ControllerP1(g.getCurrentHero(), g.getOpponent(), g, port1, port2, opponentIP);
				g.change = false;
			} else {
				new ControllerP1(g.getOpponent(), g.getCurrentHero(), g, port1, port2, opponentIP);
				g.change = true;
			}
			sendOutput(g, port4);
			setVisible(false);
			cur.close();
		} catch (FullHandException | CloneNotSupportedException | IOException e) {
			JOptionPane.showMessageDialog(this, "Connection Timed Out", "Alert", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void sendOutput(Object o, int port) throws IOException {
		socket = new Socket(opponentIP, port);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(o);

	}
}
