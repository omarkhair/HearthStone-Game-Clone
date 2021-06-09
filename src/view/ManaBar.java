package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ManaBar extends JLabel{
	private int width, height, cwidth, cheight;
	private JLabel[] crystals;
	private ImageIcon lightIcon, darkIcon, barIcon;
	private JLabel manaRatio;
	public ManaBar(int w, int h) {
		this.width=w;
		this.height=h;
		cwidth=width/14;
		cheight=(int)(0.8*height);
		barIcon=new ImageIcon(new ImageIcon("images/design/ManaBar.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		setSize(width, height);
		//setLayout(null);
		setIcon(barIcon);
		manaRatio=new JLabel();
		Font f=new Font("Traditional serif", Font.BOLD, height*2/5) ;
		manaRatio.setFont(f);
		manaRatio.setBackground(Color.black);
		manaRatio.setForeground(Color.white);
		//manaRatio.setHorizontalAlignment(JLabel.CENTER);
		manaRatio.setSize(width*3/14,height*8/10);
		manaRatio.setLocation(width/20, height/10);
		add(manaRatio);
		crystals= new JLabel[10];
		int xloc=(int)(width*3.5/14);
		
		for(int i=0;i<10;i++) {
			crystals[i]=new JLabel();
			crystals[i].setSize(cwidth, cheight);
			crystals[i].setLocation(xloc, (int)(0.1*height));
			xloc+=cwidth;
			this.add(crystals[i]);
		}
		lightIcon= new ImageIcon(new ImageIcon("images/design/manacrystal.png").getImage().getScaledInstance(
				cwidth, cheight, Image.SCALE_DEFAULT));
		darkIcon= new ImageIcon(new ImageIcon("images/design/manacrystal_dark.png").getImage().getScaledInstance(
				cwidth, cheight, Image.SCALE_DEFAULT));
	}
	public void setMana(int current, int total) {
		for(int i=0;i<total;i++) {
			if(i<current)
				crystals[i].setIcon(lightIcon);
			else
				crystals[i].setIcon(darkIcon);
		}
		manaRatio.setText(current+"/"+total);
	}
	public static void main(String[] args) {
		JFrame frame= new JFrame();
		frame.setLayout(null);
		frame.setSize(600, 600);
		frame.setVisible(true);
		ManaBar mb=new ManaBar(600, 80);
		mb.setLocation(50, 50);
		mb.setMana(10, 10);
		frame.add(mb);
		
	}
}
