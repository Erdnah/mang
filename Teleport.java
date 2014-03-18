package mang;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Teleport {
	private Animation animation;
	private Image[] pildid;
	private Image[] pildidT;
	private int x;
	private int y;
	private float imageH;
	private float imageW;

	private boolean tagurpidi;

	public Teleport(int x, int y, boolean tagurpidi) throws SlickException {
		this.x = x;
		this.y = y;
		this.tagurpidi = tagurpidi;
		pildid = new Image[11];
		if (!tagurpidi) {
			for (int i = 0; i < pildid.length; i++) {
				pildid[i] = new Image("disintegrate0" + i + ".png");
			}
			animation = new Animation(pildid, 50);
		} else {
			pildidT = new Image[11];
			for (int i = pildidT.length - 1; i >= 0; i--) {
				pildidT[Math.abs(i-10)] = new Image("disintegrate0" + i + ".png");
			}
			animation = new Animation(pildidT, 50);
		}
		animation.setLooping(false);
		imageH = animation.getImage(0).getHeight();
		imageW = animation.getImage(0).getWidth();
	}

	public void draw() {
		animation.draw(x - imageW / 2f, y - imageH / 2f);
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public boolean isTagurpidi() {
		return tagurpidi;
	}
}
