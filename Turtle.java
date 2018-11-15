import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Turtle {
	
	double heading;
	double x,y;
	boolean up;
	Color color;
	double penSize = 1;
	
	public Turtle(double x, double y, double heading) {
		this.x = x;
		this.y = y;
		this.up = true;
		this.heading = heading;
		this.color = Color.BLACK;
	}
	
	public void forward(Graphics graphic, double distance) {
		double px = x;
		double py = y;
		this.x += distance*Math.cos(heading);
		this.y += distance*Math.sin(heading);
		if(up) {
			Graphics2D g = (Graphics2D)graphic;
			g.setColor(color);
			g.setStroke(new BasicStroke((float)penSize));
			g.drawLine((int)px, (int)py, (int)x, (int)y);
			g.setColor(Color.BLACK);
		}
	}
	
	public void goTo(Point p, double head, Color c) {
		this.x = p.x;
		this.y = p.y;
		this.heading = head;
		this.color = c;
	}
	
	public void left(double del) {
		heading -= del;
	}
	
	public void right(double del) {
		heading += del;
	}
	
	public void penup() {
		up();
	}
	
	public void  up() {
		up = true;
	}
	
	public void pendown() {
		down();
	}
	
	public void  down() {
		up = false;
	}
	
	public Point position() {
		return new Point((int)x,(int)y);
	}
	
}
