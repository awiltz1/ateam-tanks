import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import java.util.ArrayList;

public class Game extends JPanel {
	private ArrayList<Sprite> objectList;
	public Game() {
		this.objectList = new ArrayList<Sprite>();
	}
	public Game(ArrayList<Sprite> list) {
		this.objectList = list;
	}
	public void add(Sprite s) {
		this.objectList.add(s);
	}
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		g2.fillRect(0,0,900,700);
		for (Sprite sprite : objectList) {
			sprite.paint(g2);
		}
	}
}
