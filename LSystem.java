import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class LSystem {
	
	private int x,y;
	private String base;
	private String[] rules;
	private String[] subs;
	private String drawString;
	private Turtle t;
	private int distance;
	private double angle;
	private double heading;

	public LSystem(int x, int y, String base, String[] rules, String[] subs, Turtle t) {
		this.x = x;
		this.y = y;
		this.base = base;
		this.rules = rules;
		this.t = t;
		drawString = "";
		distance = 10;
		angle = Math.PI/2;
		this.subs = subs;
	}
	
	public LSystem(int x, int y, String base, String[] rules, String[] subs, int distance, double angle, double heading) {
		this.x = x;
		this.y = y;
		this.base = base;
		this.rules = rules;
		this.subs = subs;
		this.heading = heading;
		this.t = new Turtle(x,y,heading);
		this.distance = distance;
		this.angle = angle;
	}
	
	/*public void update() {
		int c = (int)(3*Math.random());
		switch (c) {
		case 0:
			distance += distance * Math.random()/5 * (Math.random() > .5 ? 1 : -1);
			distance = Math.max(5,Math.min(20, distance));
			break;
		case 1:
			angle += (Math.random() - .5)/10;
			break;
		case 2:
			t.heading += (Math.random() - .5)/5;
			break;
		}
	}*/
	public void update() {
		angle += Math.random()/100;
		t.heading -= Math.random()/1000;
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void makeDString(int n) {
		drawString = base.substring(5);
		for(int i = 0; i < n; i ++) {
			String temp = "";
			String ds = drawString;
			drawString = "";
			for(char c : ds.toCharArray()) {
				String s = "" + c;
				for(String rule: rules) {
					char key = rule.charAt(5);
					String repl = rule.substring(7);
					temp = s.replaceAll("" + key, repl);
					if(!temp.equals(s)) {
						break;
					}
				}
				drawString += temp;
			}
		}
		for(String sub : subs) {
			char key = sub.charAt(5);
			String repl = sub.substring(7);
			drawString = drawString.replaceAll("" + key, repl);
		}
		System.out.println(drawString);
	}
	
	public void draw(Graphics g) {
		ArrayList<Point> positions = new ArrayList<>();
		ArrayList<Double> headings = new ArrayList<>();
		ArrayList<Color> colors = new ArrayList<>();
		for(char c : drawString.toCharArray()) {
			switch(c) {
			case 'F':
				t.forward(g, distance);
				break;
			case '-':
				t.right(angle);
				break;
			case '+':
				t.left(angle);
				break;
			case 'R':
				t.color = Color.RED;
				break;
			case 'G':
				t.color = Color.GREEN;
				break;
			case 'B':
				t.color = Color.BLUE;
				break;
			case 'I':
				t.penSize += 1;
				break;
			case 'D':
				t.penSize -= 1;
				break;
			case '[':
				positions.add(0,t.position());
				headings.add(0,t.heading);
				colors.add(0,t.color);
				break;
			case ']':
				t.goTo(positions.remove(0), headings.remove(0), colors.remove(0));
				break;
			}
		}
		t = new Turtle(x,y, heading);
	}
	
	public static LSystem readFromFile(String filename) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(new File(filename)));
			String base = r.readLine();
			ArrayList<String> lines = new ArrayList<>();
			String temp = r.readLine();
			while(temp != null && !temp.contains("subs")) {
				lines.add(temp);
				temp = r.readLine();
			}
			String[] rules = new String[lines.size()];
			for(int i = 0; i < rules.length; i ++) {
				rules[i] = lines.get(i);
			}
			lines.clear();
			while(temp != null) {
				lines.add(temp);
				temp = r.readLine();
			}
			String[] subs = new String[lines.size()];
			for(int i = 0; i < subs.length; i ++) {
				subs[i] = lines.get(i);
			}
			LSystem ls = new LSystem(100, 900, base, rules, subs, new Turtle(100,900, 0));
			r.close();
			return ls;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static LSystem arrowhead() {
		String[] rules = {"rule X RY-GX-BY","rule Y X+Y+X"};
		String[] subs = {"subs X F","subs Y F"};
		LSystem ls = new LSystem(100, 900, "base X", rules, subs, 4, Math.PI/3, 0);
		return ls;
	}
	
	public static LSystem dragon() {
		String[] rules = {"rule X X+RYF+","rule Y -GFX-BY"};
		String[] subs = {"subs X ","subs Y "};
		LSystem ls = new LSystem(500, 500, "base FX", rules, subs, 5, Math.PI/2, 0);
		return ls;
	}
	
	public static LSystem fractaltree() {
		String base = "base GX";
		String[] rules = {"rule X F+[[X]-X]-F[-BFX]+GX","rule F FRF"};
		String[] subs = {"subs X "};
		LSystem ls = new LSystem(500, 500, base, rules, subs, 2, Math.PI*25/180, -Math.PI/2);
		ls.t.heading = 0;
		return ls;
	}
	
	public static LSystem getRandom() {
		int c = (int)(Math.random()*3);
		switch(c) {
		case 0:
			return arrowhead();
		case 1:
			return dragon();
		default:
			return fractaltree();
		}
	}
	
	public void curl() {
		angle -= .001;
	}
	
	public void grow() {
		distance += distance*.01;
	}
	
	public void shrink() {
		distance -= distance*.01;
	}
	
	public void turn() {
		heading += .001;
	}
	
	@Override
	public String toString() {
		String ret = "";
		ret += base + "\n";
		for(String rule: rules)
			ret += rule + "\n";
		return ret;
	}
}
