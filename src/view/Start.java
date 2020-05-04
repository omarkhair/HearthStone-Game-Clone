package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Start extends JFrame implements ActionListener {
	private int width;
	private int height;
	JButton Start;
	JButton Quit;

	public Start() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) dim.getWidth();
		height = (int) dim.getHeight();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);

		setLayout(new BorderLayout());

		JLabel background = new JLabel(new ImageIcon(new ImageIcon("images/design/MOHAMED.png").getImage()
				.getScaledInstance(width, height, Image.SCALE_DEFAULT)));

		add(background);

		Start = new JButton(new ImageIcon(new ImageIcon("images/design/start.png").getImage()
				.getScaledInstance(width / 6, height / 10, Image.SCALE_DEFAULT)));
		Start.setBorderPainted(false);
		Start.setContentAreaFilled(false);
		Start.setFocusPainted(false);
		Start.addActionListener(this);
		Start.setActionCommand("START");
		Start.setBounds(width / 2 - width / 12, height * 8 / 10, width / 6, height / 10);

		Quit = new JButton(new ImageIcon(
				new ImageIcon("images/design/EXIT.png").getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT)));
		Quit.setBorderPainted(false);
		Quit.setContentAreaFilled(false);
		Quit.setFocusPainted(false);
		Quit.addActionListener(this);
		Quit.setActionCommand("Quit");
		Quit.setBounds((int) (0.833 * (dim.width)), (int) (0.787 * (dim.height)), 400, 250);

		background.add(Start);
		background.add(Quit);

		this.revalidate();
		this.repaint();
	}

	public static void main(String args[]) {
		new Start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "START") {
			
			new HeroSelectionView();
			this.setVisible(false);
		} else if (e.getActionCommand() == "Quit") {
			int a = JOptionPane.showConfirmDialog(this, "Do you want to exit ", "EXIT Bar", JOptionPane.YES_NO_OPTION);
			if (a == JOptionPane.YES_OPTION) {
				System.exit(0);
			}

		}

	}
}