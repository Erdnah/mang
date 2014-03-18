package mang;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class Hull extends BasicGame {

	private Image koll1;
	private Image koll2;
	private Image koll3;
	private Image koll;
	private Image background;
	private Image alustusnupp;
	private Image alustusnupp2;
	private Image pealkiri;
	private Image tekst;
	private Image manglabi;
	private Image uuesti;
	private Image uuesti2;
	private Image signa;
	private Image aasta;
	private Image laeskoor;
	private Image laeskoor2;

	private int kolle = 60;
	private Circle lal;
	private Rectangle nupuBox;
	private Rectangle uuestiBox;
	private Rectangle laeBox;
	private static int aegMöödas;
	private int tase = 1;
	private int taseAeg;
	private int kuule = 3;
	private int telesid = 3;
	private Teleport teleport;
	private Explosion pauk;
	private float mouseX;
	private float mouseY;

	static Nimi nimi;

	private boolean paus = false;
	private boolean alustatud = false;
	private static boolean läbi = false;

	ArrayList<Koll> kollid = new ArrayList<Koll>();
	ArrayList<Kuul> kuulid = new ArrayList<Kuul>();

	Random rand = new Random();

	public Hull() {
		super("Hull Mäng!");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		koll1 = new Image("koll1.png");
		koll2 = new Image("koll2.png");
		koll3 = new Image("koll3.png");
		background = new Image("background.jpg");
		alustusnupp = new Image("alustanupp.png");
		alustusnupp2 = new Image("alustanupp2.png");
		manglabi = new Image("manglabi.png");
		tekst = new Image("tekst.png");
		pealkiri = new Image("pealkiri.png");
		uuesti = new Image("uuesti.png");
		uuesti2 = new Image("uuesti2.png");
		signa = new Image("signa.png");
		aasta = new Image("aasta.png");
		laeskoor = new Image("laeskoor.png");
		laeskoor2 = new Image("laeskoor2.png");

		nupuBox = new Rectangle(111, 200, alustusnupp.getWidth(),
				alustusnupp.getHeight());

		uuestiBox = new Rectangle(115, 190, uuesti.getWidth(),
				uuesti.getHeight());

		laeBox = new Rectangle(60, 125, laeskoor.getWidth(),
				laeskoor.getHeight());

		lal = new Circle(gc.getInput().getMouseX(), gc.getInput().getMouseY(),
				7);
		for (int i = 0; i < kolle; i++) {
			koll = koll1;
			if (i > 20) {
				koll = koll2;
				if (i > 45) {
					koll = koll3;
				}
			}
			kollid.add(new Koll(koll, rand.nextInt(305), -rand.nextInt(320)));
		}

		gc.setTargetFrameRate(60);
		gc.setShowFPS(false);

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		g.setColor(Color.white);
		g.drawImage(background, 0, 0);
		if (!alustatud) {
			if (!läbi) {
				if (nupuBox.contains(mouseX, mouseY)) {
					g.drawImage(alustusnupp2, 108, 197);
				} else {
					g.drawImage(alustusnupp, 111, 200);
				}
				g.drawImage(pealkiri, 60, 10);
				g.drawImage(tekst, 60, 75);
				g.drawImage(signa, 110, 55);
				g.drawImage(aasta, 240, 210);
			} else {
				g.drawImage(manglabi, 0, 10);
				g.drawString("Su aeg oli: " + aegMöödas / 1000 + " sekki", 70,
						100);
				if (laeBox.contains(gc.getInput().getAbsoluteMouseX(), gc
						.getInput().getAbsoluteMouseY())) {
					g.drawImage(laeskoor2, 57, 122);
				} else {
					g.drawImage(laeskoor, 60, 125);
				}
				if (uuestiBox.contains(gc.getInput().getAbsoluteMouseX(), gc
						.getInput().getAbsoluteMouseY())) {
					g.drawImage(uuesti2, 113, 187);
				} else {
					g.drawImage(uuesti, 115, 190);
				}
			}
		} else {
			for (int i = 0; i < kolle; i++) {
				kollid.get(i).draw();
			}
			for (int i = 0; i < kuulid.size(); i++) {
				kuulid.get(i).draw();
			}
			if (paus) {
				g.drawString("Vali uus asukoht!", 80, 200);
			}
			if (teleport != null) {
				teleport.draw();
			}
			if (pauk != null) {
				pauk.draw();
			}

			g.fill(lal);
			g.setColor(Color.gray);
			g.fillRect(0, 240, 320, 20);
			g.setColor(Color.white);
			g.drawString("Aeg:" + aegMöödas / 1000 + " |Kuule:" + kuule
					+ " |Telesid:" + telesid + " |Tase:" + tase, 5, 241);
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();
		if (alustatud) {
			if (!paus) {
				aegMöödas += delta;
				taseAeg += delta;
				if (taseAeg / 1000 == 30) {
					tase++;
					for (int i = 0; i < kollid.size(); i++) {
						kollid.get(i)
								.setSpeed(kollid.get(i).getSpeed() + 0.25f);
					}
					taseAeg = 0;
				}
				if (telesid > 0) {
					if (input.isMousePressed(0)) {
						paus = true;
						telesid--;
						teleport = new Teleport(input.getMouseX(),
								input.getMouseY(), false);
					}
				}
				for (int i = 0; i < kolle; i++) {
					kollid.get(i).update();
					if (kollid.get(i).getCircle().intersects(lal)) {
						gameOver();
					}
					for (int j = 0; j < kuulid.size(); j++) {
						if (kollid.get(i).getCircle()
								.intersects(kuulid.get(j).getRect())) {
							kuulid.remove(j);
							pauk = new Explosion(kollid.get(i).getX(), kollid
									.get(i).getY());
							kollid.get(i).reset(false);
						}
					}
				}
				if (kuule > 0) {
					if (input.isKeyPressed(Input.KEY_SPACE)) {
						kuulid.add(new Kuul(input.getMouseX(), input
								.getMouseY()));
						kuule--;
					}
				}
				for (int i = 0; i < kuulid.size(); i++) {
					kuulid.get(i).update();
					if (kuulid.get(i).getY() <= 0) {
						kuulid.remove(i);
					}
				}
			} else {
				if (input.isMousePressed(0)) {
					paus = false;
					teleport = new Teleport(input.getMouseX(),
							input.getMouseY(), true);
				}
			}
			if (teleport != null) {
				if (teleport.getAnimation().isStopped()) {
					teleport = null;
				}
			}
			if (pauk != null) {
				if (pauk.getAnimation().isStopped()) {
					pauk = null;
				}
			}
			lal.setCenterX(gc.getInput().getAbsoluteMouseX());
			lal.setCenterY(gc.getInput().getAbsoluteMouseY());
		} else {
			if (laeBox.contains(gc.getInput().getAbsoluteMouseX(), gc
					.getInput().getAbsoluteMouseY())) {
				if (input.isMousePressed(0)) {
					if (aegMöödas / 1000 > 1) {
						@SuppressWarnings("unused")
						Nimi nimi = new Nimi(aegMöödas / 1000);
					}
				}
			}
			if (nupuBox.contains(input.getAbsoluteMouseX(),
					input.getAbsoluteMouseY())) {
				if (input.isMousePressed(0)) {
					if (!läbi) {
						alustatud = true;
						aegMöödas = 0;
					} else {
						läbi = false;
					}
				}
			}
		}
	}

	public void gameOver() {
		alustatud = false;
		läbi = true;
		paus = false;
		kuule = 3;
		telesid = 3;
		taseAeg = 0;
		tase = 1;
		for (int i = 0; i < kollid.size(); i++) {
			kollid.get(i).reset(true);
			kollid.get(i).setSpeed(1.0f);
		}
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Hull(), 320, 260, false);
		app.start();

	}

	public static void setLäbi() {
		läbi = false;
		aegMöödas = 0;
		nimi = null;
	}

}
