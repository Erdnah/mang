package mang;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

public class Koll {

	private Image koll;  // siia laeme kolli pildi
	
	Circle circle; // kolli ümritsev ring

	float X = 0f;// X-positsioon kollil
	float Y = 0f;// Y-positsioon
	float width = 0f;//laius kollil
	float height = 0f;//kõrgus
	float speed = 1.0f;
	
	Random rand = new Random();
	
	public Koll(Image koll, float x, float y) {
		super();
		this.koll = koll;
		X = x;
		Y = y;
		
		height = koll.getHeight();
		width = koll.getWidth();
		
		circle = new Circle(X, Y, height/2);
	}
	
	public void update() {
		if (Y - koll.getHeight() > 260) {
			reset(false);
		} else {
			Y += speed;
		}
		circle.setX(X);
		circle.setY(Y);
	}
	
	public void reset(boolean kasLäbi) {
		if (!kasLäbi) {
			Y = -rand.nextInt(60) - 15;
		} else {
			Y = -rand.nextInt(260) - 15;
		}
		X = rand.nextInt(305);
	}
	
	public void draw() {
		koll.draw(X, Y);
	}
	
	public Circle getCircle() {
		return circle;
	}

	public Image getKoll() {
		return koll;
	}

	public float getX() {
		return X;
	}

	public void setX(float x) {
		X = x;
	}

	public float getY() {
		return Y;
	}

	public void setY(float y) {
		Y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setKoll(Image koll) {
		this.koll = koll;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
}
