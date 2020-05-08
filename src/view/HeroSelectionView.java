package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import controller.Controller;

public class HeroSelectionView extends JFrame implements ActionListener{
	private int width;
	private int height;
	private HeroSelectionPanel left;
	private HeroSelectionPanel right;
	private JButton startGame;

	public HeroSelectionView () {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width=(int) dim.getWidth();
		height=(int) dim.getHeight();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		setVisible(true);
		
		
		setLayout(null);
		
		JLayeredPane pane = new JLayeredPane();
		pane.setLocation(0, 0);
		pane.setSize(width, height);

		add(pane);
	//	pane.add(
		left=new HeroSelectionPanel(width, height);
		left.setLocation(0, 0);
		//left.setSize(width/2, height);
		pane.add(left,1,0);
		right=new HeroSelectionPanel(width, height);
		right.setLocation(width/2, 0);
		//right.setSize(width/2, height);
		pane.add(right,1,0);
		
		startGame=new JButton();
		startGame.addActionListener(this);
		ImageIcon i = new ImageIcon(new ImageIcon("images/design/SelectionPlay.png").getImage().getScaledInstance(width / 10, width/10,
				Image.SCALE_DEFAULT));
		startGame.setIcon(i);
		startGame.setContentAreaFilled(false);
		startGame.setFocusPainted(false);
		startGame.setBorderPainted(false);
		startGame.setSize(new Dimension(width/10,width/10));
		startGame.setLocation(width/2-startGame.getWidth()/2,height/12);
		pane.add(startGame,2,0);
		
		JLabel vs=new JLabel();
		i = new ImageIcon(new ImageIcon("images/design/vs.png").getImage().getScaledInstance(width/6,height*3/5,
				Image.SCALE_DEFAULT));
		vs.setIcon(i);
		vs.setSize(new Dimension(width/6,height*3/5));
		vs.setLocation(width/2-vs.getWidth()/2,height/5);
		pane.add(vs,2,0);
		
		revalidate();
		repaint();
		
		
		
//		
//		JPanel panel =new JPanel();
//		panel.setPreferredSize(new Dimension(width/2,height));
//		panel.setBackground(Color.black);
//		add(panel,BorderLayout.WEST);

		
		
		
		//		JButton t1 = new JButton();
//		t1.setSize(500, 100);
//		t1.setLocation(20,30);
//		JButton t2 = new JButton();
//		t2.setSize(500, 100);
//		t2.setLocation(100,30);
//		add(t1);add(t2);
//		JPanel panel2 =new JPanel();
//		panel2.setPreferredSize(new Dimension(width/2,height));
//		panel2.setBackground(Color.black);
//		add(panel2,BorderLayout.EAST);
		
	}
	public static void main(String[] args) {
		HeroSelectionView h = new HeroSelectionView();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Controller c=new Controller(left.getChosen(),right.getChosen());
		this.setVisible(false);
	}
	
}
