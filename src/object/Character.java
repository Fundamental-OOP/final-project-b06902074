package object;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

import config.Config;
import images.ImageHandler;
import info.GameInfo;

public class Character {
	private BufferedImage img;
	private double[] pos;
	private double[] size;
	private double speed;
	private boolean face = true; // false: left, true: right

	public Character() {
		this.img = ImageHandler.getImage(Config.characterImage);
		this.size = Config.characterSize;
		this.pos = new double[] { GameInfo.getStartPos() - this.size[0] / 2, Config.base + this.size[1] / 2 };
		this.speed = Config.speed;
	}

	public void draw(Graphics g) {
		if (this.face) {
			g.drawImage(this.img, (int) (this.pos[0] - this.size[0] / 2),
					(int) (Config.pageHeight - (this.pos[1] + this.size[1] / 2)), (int) (this.size[0]),
					(int) (this.size[1]), null);
		} else {
			g.drawImage(this.img, (int) (this.pos[0] + this.size[0] / 2),
					(int) (Config.pageHeight - (this.pos[1] + this.size[1] / 2)), (int) -(this.size[0]),
					(int) (this.size[1]), null);
		}
	}

	public void updatePosition() {
		double startPos = GameInfo.getStartPos();
		if (startPos != -1 && !this.isAtCorrectPos()) {
			this.face = startPos >= this.pos[0];
			if (this.pos[0] < startPos + (this.face ? -1 : 1) * this.size[0] / 2) {
				this.pos[0] = Math.min(this.pos[0] + speed, startPos + (this.face ? -1 : 1) * this.size[0] / 2);
			} else {
				this.pos[0] = Math.max(this.pos[0] - speed, startPos + (this.face ? -1 : 1) * this.size[0] / 2);
			}
		}
	}

	public boolean isAtCorrectPos() {
		return Math.round(this.pos[0] * 10)
				/ 10 == Math.round((GameInfo.getStartPos() + (this.face ? -1 : 1) * this.size[0] / 2) * 10) / 10;
	}
}
