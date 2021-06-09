package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HealthBar extends JLabel{
	private int width, height, hheight;
	private ImageIcon health,barIcon;
	private JLabel healthBar;
	private JLabel healthRatio;
	private final int x=39;
	public HealthBar(int w, int h) {
		this.width=w;
		this.height=h;
		//hwidth=(int)(width/38.3);
		//System.out.println(hwidth);
		hheight=(int)(0.72*height);
		barIcon=new ImageIcon(new ImageIcon("images/design/HealthBG.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		setSize(width, height);
		//setLayout(null);
		setIcon(barIcon);
		healthRatio=new JLabel();
		Font f=new Font("Traditional serif", Font.BOLD, height*2/5) ;
		healthRatio.setFont(f);
		healthRatio.setForeground(Color.white);
		healthRatio.setSize(width*3/14,height*8/10);
		healthRatio.setLocation(width/20, height/10);
		add(healthRatio);
		
		healthBar=new JLabel();
		healthBar.setLocation(width*9/42,(int)(height/6.9));
		healthBar.setSize(width*30/x,hheight);
		add(healthBar);
		
		health= new ImageIcon(new ImageIcon("images/design/redRectangle.png").getImage().getScaledInstance(
				width*30/x,hheight, Image.SCALE_DEFAULT));
		healthBar.setIcon(health);
		
		
	}
	public void setHealth(int current) {
		healthBar.setLocation(width*9/42,(int)(height/6.9));
		healthBar.setSize(width*current/x,hheight);
		healthRatio.setText(current+"/30");
		health= new ImageIcon(new ImageIcon("images/design/redRectangle.png").getImage().getScaledInstance(
				((width*current)/x)+1,hheight, Image.SCALE_DEFAULT));
	}
//	public static void main(String[] args) {
//		JFrame frame= new JFrame();
//		frame.setLayout(null);
//		frame.setSize(900, 900);
//		frame.setVisible(true);
//		HealthBar mb=new HealthBar(600, 200);
//		mb.setLocation(50, 50);
//		mb.setHealth(30);
//		frame.add(mb);
//		frame.revalidate();
//		frame.repaint();
//		
//	}
}
