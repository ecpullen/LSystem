import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel{
	public JFrame frame;
	public Turtle t;
	ArrayList<LSystem> lss;
	
	public Window() throws InterruptedException {
		frame = new JFrame("Trippy Graphics");
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		frame.add(this);
		lss = new ArrayList<>();
		frame.setVisible(true);
		//randFracts();
		landscape();
		repaint();
		for(int i = 0; i < 100000; i ++) {
			Thread.sleep(2);
			repaint();
		}
	}
	
	public void randFracts() {
		for(int i = 0; i < 100; i ++) {
			LSystem temp = LSystem.getRandom();
			temp.setPos((int)(Math.random() * 1000),(int)(Math.random() * 1000));
			temp.makeDString((int)(Math.random()*4 + 3));
			lss.add(temp);
		}
	}
	
	public void landscape() {
		for(int i = 0; i < 5; i ++) {
			LSystem t = LSystem.fractaltree();
			t.setPos(200*i, 900);
			t.makeDString(6);
			lss.add(t);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		for(LSystem ls : lss) {
			ls.curl();
			ls.draw(g);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Window();
	}
}


