package mang;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;


public class Kuul {
	Image kuul;
	float X;
	float Y;
	
	Rectangle rect;

	public Kuul(float x, float y) throws SlickException {
		super();
		X = x;
		Y = y;
		kuul = new Image("kull.png");
		rect = new Rectangle(X, Y, kuul.getWidth() * 1.5f, kuul.getHeight() * 1.5f);
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public void draw() {
		kuul.draw(X, Y, 1.5f);		
	}
	
	public void update() {
		Y -= 5;
		rect.setY(Y);
	}

	public float getY() {
		return Y;
	}
	
}
