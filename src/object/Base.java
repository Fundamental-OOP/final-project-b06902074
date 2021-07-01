package object;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import config.Config;
import images.ImageHandler;
import info.GameInfo;

public class Base extends Obstacle {
    private BufferedImage wallVertical = ImageHandler.getImage("wallVertical");

    public Base() {
        super(new double[] { Config.base, 0.0 });
    }

    public void perform(Ball ball) {
        double[] pos = ball.getPos();
        if (GameInfo.getStartPos() < 0) {
            GameInfo.setStartPos(pos[0]);
            System.out.printf("set start pos x: %f\n", pos[0]);
        }

        ball.updateDirection(new double[] { pos[0] < GameInfo.getStartPos() ? 1.0 : -1.0, 0.0 });
        ball.setTouchedBase(true);
    };

    public void collisionJudge(Ball ball) {
        if (ball.getPos()[1] - ball.getRadius() <= pos[0] && ball.getDirection()[1] < 0) {
            perform(ball);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(wallVertical, 0, (int) (Config.pageHeight - Config.base), (int) Config.pageWidth, (int) Config.base,
                null);
    };
}