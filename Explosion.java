package mang;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Explosion {
	private Animation animation;
	private Image explosions;
	SpriteSheet spritesheet;
	
	private float x;
	private float y;
	private int spriteX;
	private int spriteY;
	
	public Explosion (float x, float y) throws SlickException {
		this.x = x;
		this.y = y;
		explosions = new Image("explosions.png");
		spriteX = explosions.getWidth() / 5;
		spriteY = explosions.getHeight() / 5;
		spritesheet = new SpriteSheet(explosions, spriteX, spriteY);
		animation = new Animation(spritesheet, 0, 0, 4, 4, true, 25, true);
		animation.setLooping(false);
	}
	
	public void draw() {
		animation.draw(x-20, y-30);
	}
	
	public Animation getAnimation() {
		return animation;
	}
}
